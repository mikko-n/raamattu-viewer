/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//
//  GotoFormChapter.java
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
/**
 *
 * @author NIM
 */

import javax.microedition.lcdui.*;

public class GotoFormChapter extends Form implements ItemCommandListener, CommandListener
{
	private GoBible goBible;

        private int bookIndex;
                
	private Command cancelCommand = new Command(GoBible.getString("UI-Cancel"), Command.CANCEL, 0);
        private Command backCommand = new Command(GoBible.getString("UI-Back"), Command.OK, 0);
	
	/**
	 * Creates a search for with search criteria and from and to books to search in.
	 */
	public GotoFormChapter(GoBible goBible, int bookIndex)
	{
		super(GoBible.getString("UI-Select")+" "+GoBible.getString("UI-Chapter"));

		this.goBible = goBible;
                this.bookIndex = bookIndex;
                int chapterCount = goBible.bibleSource.getNumberOfChapters(bookIndex);
                Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
                
                int width;
                if (chapterCount <=9) {
                    width = f.stringWidth("0"+String.valueOf(chapterCount))+4;
                } else {
                    width = f.stringWidth(String.valueOf(chapterCount))+4;
                }
                
                int height = f.getHeight()+2;
                
                for (int i=0; i <= chapterCount-1; i++) {
                    String btntext = String.valueOf(i+1);
                    
//                    System.out.println("[GotoFormChapter.const] chapter button text: "+btntext);
                    
                    Image txtimg = Image.createImage(width, height);
                    txtimg.getGraphics().drawRoundRect(0, 0, width-1, height-1, TextStyle.fontHeight/3, TextStyle.fontHeight/3);
                    // text centering
                    int btntxtWidth = f.stringWidth(btntext);                                       
                    txtimg.getGraphics().drawString(btntext, width/2-btntxtWidth/2, 1, 0);                    
                                        
                    ImageItem im = new ImageItem(null, txtimg, Item.LAYOUT_SHRINK|Item.LAYOUT_LEFT, btntext, Item.HYPERLINK);
                    im.setDefaultCommand(new Command("Set", Command.ITEM, 1)); 
                    im.setPreferredSize(width, height);
                    
                    // icl is ItemCommandListener   
                    im.setItemCommandListener(this);
                    this.append(im);
                }
                
		addCommand(cancelCommand);
                addCommand(backCommand);		
		
		setCommandListener(this);
                
	}
        
        protected void gotoPassage(int bk, int ch, int ve) {
            bookIndex = bk;        
        }
	
        /**
         * Gui buttons
         * @param command
         * @param display 
         */
	public void commandAction(Command command, Displayable display)
	{
            switch (command.getCommandType())
            {			
                case Command.OK: 
                {
                    goBible.showGotoScreen();
                    break;
                }
                case Command.BACK:
                case Command.CANCEL: 
                {
                    goBible.showMainScreen();
                    break;
                }
            }
	}

        /**
         * Button event handler
         * @param c
         * @param item 
         */
    public void commandAction(Command c, Item item) 
    {
        switch (c.getCommandType()) {
            case Command.ITEM: {
                
                int bookindex = this.bookIndex;
                int ch = (int) Integer.parseInt(((ImageItem) item).getAltText())-1;
                int ve = 0;
                System.out.println("[GotoFormChapter.commandAction()] getting verse count for book: "+this.bookIndex+", chapter: "+ch);
                int verseCount = goBible.bibleSource.getNumberOfVerses(bookIndex, ch);                
                System.out.println("[GotoFormChapter.commandAction()] verse count = "+verseCount);
                
                // only 1 verse, skip verse selection and show main screen
                if (verseCount == 1) {
                    goBible.bibleCanvas.gotoFormRequest(bookindex, ch, ve);
                    goBible.showMainScreen();
                    break;
                }               
                goBible.showGotoVerseScreen(bookindex, ch);
                break;
            }
        }
    }   
}
