/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goBible.views;

import goBible.base.GoBible;
import java.util.Calendar;
import java.util.Date;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Ticker;

/**
 * Displays a form which allows user to add note to specific passage.
 * @author NIM
 */
public class NotesEditor extends TextBox implements CommandListener {

    private GoBible goBible;
    private Command okCommand = new Command(GoBible.getString("UI-OK"), Command.OK, 0);
    private Command backCommand = new Command(GoBible.getString("UI-Back"), Command.BACK, 0);
    private Command addDateCommand = new Command(GoBible.getString("UI-Add-date"), Command.SCREEN, 0);
    private Command addVerseCommand = new Command(GoBible.getString("UI-Add-verse"), Command.SCREEN, 0);
        
    public NotesEditor(GoBible goBible) {        
        super("",null,2000, TextField.ANY);
        this.goBible = goBible;
        String bookName= goBible.bibleSource.getBookName(goBible.bibleCanvas.currentBook());
        String referenceString = goBible.bibleSource.getReferenceString(goBible.bibleCanvas.currentBook(), goBible.bibleCanvas.currentChapter(), goBible.bibleCanvas.currentVerse());
       
        goBible.Log("Notes editor, current book: "+bookName+", chapter: "+goBible.bibleCanvas.currentChapter()+" verse: "+goBible.bibleCanvas.currentVerse());
        
        this.setTitle(bookName+" "+referenceString);

        this.setString(goBible.getNoteForVerse());

        addCommand(okCommand);
        addCommand(backCommand);
        addCommand(addDateCommand);
        addCommand(addVerseCommand);
        
        setCommandListener(this);
       
    }

    /**
     * Command handler for note editor
     * @param c
     * @param d 
     */
    public void commandAction(Command c, Displayable d) {
        if (c == okCommand) {                
            goBible.Log("[NotesEditor.commandAction.ok] adding note: "+getString());
            goBible.addToNotes(getString());
            goBible.showMainScreen();
        }
        if (c == backCommand) {
            goBible.showMainScreen();				
        }
        if (c == addDateCommand) {
            insert(getDateString(), getCaretPosition());
        }
        if (c == addVerseCommand) {
            String bookName= goBible.bibleSource.getBookName(goBible.bibleCanvas.currentBook());
            String referenceString = goBible.bibleSource.getReferenceString(goBible.bibleCanvas.currentBook(), goBible.bibleCanvas.currentChapter(), goBible.bibleCanvas.currentVerse());
            insert(bookName+" "+referenceString, getCaretPosition());
        }
    }
    
    /**
     * Returns date string DD.MM.YYYY
     * @return 
     */
    private String getDateString() {
        String str, day, year, month;
        Date now = new Date(System.currentTimeMillis());
        String aika = now.toString();
        str= aika.substring(4, 7);
        // Converting 3-char month name to number
        if (str.equals("Jan")) { month= "01"; }
        else if (str.equals("Feb")) { month= "02"; }
        else if (str.equals("Mar")) { month= "03"; }
        else if (str.equals("Apr")) { month= "04"; }
        else if (str.equals("May")) { month= "05"; }
        else if (str.equals("Jun")) { month= "06"; }
        else if (str.equals("Jul")) { month= "07"; }
        else if (str.equals("Aug")) { month= "08"; }
        else if (str.equals("Sep")) { month= "09"; }
        else if (str.equals("Oct")) { month= "10"; }
        else if (str.equals("Nov")) { month= "11"; }
        else if (str.equals("Dec")) { month= "12"; }
        else { month= "00"; }
        
        year= aika.substring(aika.length()-4, aika.length());
        
        day = aika.substring(8, 10);
        return day+"."+month+"."+year;
    }
}
