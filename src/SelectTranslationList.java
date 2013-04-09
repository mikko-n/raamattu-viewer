/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.*;

/**
 *
 * @author NIM
 */
public class SelectTranslationList extends List implements CommandListener {

    private GoBible goBible;
    private Command selectTranslationCommand = new Command(GoBible.getString("UI-Select"), Command.ITEM, 0);
    private Command backCommand = new Command(GoBible.getString("UI-Back"), Command.CANCEL, 0);

    public SelectTranslationList(GoBible midlet) {
        super(GoBible.getString("UI-Change-Translation"), Choice.IMPLICIT);

        try {
            getAvailableTranslations();
        } catch (IOException ioEx) {
            goBible.showMainScreen();
        }

        if (GoBible.USE_MIDP20) {
            setFitPolicy(Choice.TEXT_WRAP_ON);
        }

        this.goBible = midlet;

        addCommands();
    }

    private void addCommands() {
        if (this.size() > 0) {
            setSelectCommand(selectTranslationCommand);
        }
        addCommand(backCommand);
        setCommandListener(this);
    }

    /**
     * Constructs list of available translations
     */
    private void getAvailableTranslations() throws IOException {

        Vector translations = listAvailableTranslations(this.goBible);

        for (int i = 0; i < translations.size(); i++) {
            append((String) translations.elementAt(i), null);
        }
    }

    /**
     * Searches memory card for available translations
     *
     * @return available translations
     * @throws IOException
     */
    public static Vector listAvailableTranslations(GoBible gobible) throws IOException {

        Vector translations = new Vector();

        FileConnection con = null;
        try {
            con = (FileConnection) Connector.open(gobible.BIBLE_DATA_ROOT, Connector.READ);
        } catch (IOException ioEx) {
            throw new IOException("Err opening conn: " + ioEx.getMessage() + ", filepath: " + gobible.BIBLE_DATA_ROOT);
        }

        Enumeration enumer = con.list();
        DataInputStream inp = null;

        while (enumer.hasMoreElements()) {
            String s = (String) enumer.nextElement();

            if (gobible.ZIP_COMPLIANT) {
                // list only zip files
                if (s.endsWith(".zip")) {

                    char[] nameChars = s.toCharArray();
                    StringBuffer sb = new StringBuffer();

                    for (int i = 0; i < nameChars.length; i++) {
                        
                        // not in the beginning or not in the end
                        if (i != 0 && i < nameChars.length-1) 
                        {                            
                            
                            // if current character is not uppercase and                          
                            // if next character is digit, uppercase or bracket, append with char + space 
                            if (Character.isLowerCase(nameChars[i]) && 
                                    (Character.isDigit(nameChars[i+1]) ||
                                    Character.isUpperCase(nameChars[i+1]) ||
                                    nameChars[i+1] == '(' ||
                                    nameChars[i+1] == ')'))
                            {   
                                sb.append(nameChars[i]);
                                sb.append(" ");
                            }
                            else {                                
                                sb.append(nameChars[i]);
                                
                                // special case: if current char is closing bracket, append with space
                                if (nameChars[i] == ')') {
                                    sb.append(" ");
                                }
                            }                            
                        }                        
                        else 
                        {
                            sb.append(nameChars[i]);
                        }
                        
                        
                    }                    

                    System.out.println(sb.toString());
                    // without .zip extension
                    translations.addElement(sb.toString().substring(0, sb.toString().length() - 4));
                }
            } else {
                // list only directories
                if (s.endsWith("/")) {
                    // without trailing slash
                    System.out.println(s);
                    translations.addElement(s.substring(0, s.length() - 1));
                }
            }
        }
        return translations;
    }

    public void commandAction(Command c, Displayable d) {
        if (d == this) {
            if (c == selectTranslationCommand) {
                // select and change translation
                // get current book, chapter and verse
                int bookIndex = goBible.currentBookIndex;
                int chapterIndex = goBible.currentChapterIndex;
                int verseIndex = goBible.currentVerseIndex;

                goBible.setTranslation(this.getString(getSelectedIndex()));
                goBible.run();
                goBible.display.setCurrent(goBible.bibleCanvas);
            } else {
                switch (c.getCommandType()) {
                    case Command.OK:
                    case Command.CANCEL:
                        goBible.display.setCurrent(goBible.bibleCanvas);
                        goBible.showMainScreen();
                        break;
                }
            }
        }
    }
}
