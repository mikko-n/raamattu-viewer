/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.Enumeration;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.*;

/**
 *
 * @author NIM
 */
public class SelectTranslationList extends List implements CommandListener  {
    
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
        
        if (GoBible.USE_MIDP20)
        {
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
     * Searches memory card for available translations
     */
    private void getAvailableTranslations() throws IOException {
        FileConnection con = null;
        try {
            con = (FileConnection) Connector.open(CombinedChapterBibleSource.BIBLE_DATA_ROOT, Connector.READ);
        } catch (IOException ioEx) {            
            throw new IOException("Err opening conn: "+ioEx.getMessage()+", filepath: "+CombinedChapterBibleSource.BIBLE_DATA_ROOT);            
        }
        
        Enumeration enumer = con.list();
        while (enumer.hasMoreElements()) {                                   
            String s = (String)enumer.nextElement();
            
            // list only dir
            if (s.endsWith("/")) {
                append(s, null);
            }
        }
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
//                goBible.showMainScreen();
            }
            else {
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
