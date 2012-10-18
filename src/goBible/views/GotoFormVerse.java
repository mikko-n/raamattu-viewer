package goBible.views;

//
//  GotoFormVerse.java
//  GoBible
//
//	Go Bible is a Free Bible viewer application for Java mobile phones (J2ME MIDP 1.0 and MIDP 2.0).
//	Copyright © 2003-2008 Jolon Faichney.
//	Copyright © 2008-2009 CrossWire Bible Society.
//      Copyright © 2011-2012 Mikko Nieminen
//
//	This program is free software; you can redistribute it and/or
//	modify it under the terms of the GNU General Public License
//	as published by the Free Software Foundation; either version 2
//	of the License, or (at your option) any later version.
//
//	This program is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//	GNU General Public License for more details.
//
//	You should have received a copy of the GNU General Public License
//	along with this program; if not, write to the Free Software
//	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//

import javax.microedition.lcdui.*;
import goBible.base.*;
import goBible.common.TextStyle;

public class GotoFormVerse extends Form implements ItemCommandListener, CommandListener
{
    private GoBible goBible;
    private int bookIndex;
    private int chapterIndex;
    
    private Command cancelCommand = new Command(GoBible.getString("UI-Cancel"), Command.CANCEL, 0);
    private Command backCommand = new Command(GoBible.getString("UI-Back"), Command.OK, 0);

    /**
     * Creates a search for with search criteria and from and to books to search
     * in.
     */
    public GotoFormVerse(GoBible goBible, int book, int chapter) {
        super(GoBible.getString("UI-Select")+" "+GoBible.getString("UI-Verse"));

        this.goBible = goBible;
        this.bookIndex = book;
        this.chapterIndex = chapter;
        
        int verseCount = goBible.bibleSource.getNumberOfVerses(bookIndex, chapterIndex);
        Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
//        int width = f.stringWidth(String.valueOf(verseCount - 1)) + 4;
        int width;
        if (verseCount <= 9) {
            width = f.stringWidth("0" + String.valueOf(verseCount)) + 4;
        } else {
            width = f.stringWidth(String.valueOf(verseCount)) + 4;
        }
                
        int height = f.getHeight() + 2;
      
        for (int i = 0; i <= verseCount-1; i++) {
            String btntext = String.valueOf(i+1);
            System.out.println("[GotoFormVerse.const] verse button text: "+btntext);
                        
            Image txtimg = Image.createImage(width, height);
            txtimg.getGraphics().drawRoundRect(0, 0, width - 1, height - 1, TextStyle.fontHeight / 3, TextStyle.fontHeight / 3);
            // text centering
            int btntxtWidth = f.stringWidth(btntext);                                       
            txtimg.getGraphics().drawString(btntext, width/2-btntxtWidth/2, 1, 0);                    
                    
            ImageItem im = new ImageItem(null, txtimg, Item.LAYOUT_SHRINK | Item.LAYOUT_LEFT, String.valueOf(i), Item.HYPERLINK);
            im.setDefaultCommand(new Command("Set", Command.ITEM, 1));
            im.setPreferredSize(width, height);

            // im is ItemCommandListener   
            im.setItemCommandListener(this);
            this.append(im);

        }

        addCommand(backCommand);
        addCommand(cancelCommand);

        setCommandListener(this);

    }

    public void gotoPassage(int bk, int ch, int ve) {
        bookIndex = bk;
        chapterIndex = ch;    
    }
	
    /**
     * Gui buttons
     *
     * @param command
     * @param display
     */
    public void commandAction(Command command, Displayable display) {
        switch (command.getCommandType()) {
            case Command.OK:
            {
                goBible.showGotoChapterScreen(bookIndex);
                break;
            }
            case Command.BACK:
            case Command.CANCEL: {
                goBible.showMainScreen();
                break;
            }
        }
    }

    /**
     * Button event handler
     *
     * @param c
     * @param item
     */
    public void commandAction(Command c, Item item) {
        switch (c.getCommandType()) 
        {
            case Command.ITEM: 
            {
                int bookindex = this.bookIndex;
                int ch = this.chapterIndex;
                int ve = (int) Integer.parseInt(((ImageItem) item).getAltText());
                System.out.println("[GotoFormVerse.commandAction()] before sanity check - book: " + this.bookIndex + ", chapter: " + ch + ", verse: " + ve);
                try {
                    int versesInChapter = goBible.bibleSource.getNumberOfVerses(bookindex, ch);

                    // sanity check
                    if (ve > versesInChapter) {
                        ve = versesInChapter;
                    } else if (ve < 0) {
                        ve = 0;
                    }

                } catch (NumberFormatException nfe) {
                }

                System.out.println("[GotoFormVerse.commandAction()] gotoform request- book: " + this.bookIndex + ", chapter: " + ch + ", verse: " + ve);
                goBible.bibleCanvas.gotoFormRequest(bookindex, ch, ve);
                goBible.showMainScreen();
                break;
            }
        }
    } 
}
