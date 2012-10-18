package goBible.views;

//
//  GotoFormBook.java
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

import goBible.base.GoBible;
import goBible.common.TextStyle;
import javax.microedition.lcdui.*;

public class GotoFormBook extends Form implements ItemCommandListener, CommandListener
{
	private GoBible goBible;
        
	private Command cancelCommand = new Command(GoBible.getString("UI-Cancel"), Command.CANCEL, 0);
	
    /**
     * Creates a search for with search criteria and from and to books to search
     * in.
     */
    public GotoFormBook(GoBible goBible) {
        super(GoBible.getString("UI-Select") + " " + GoBible.getString("UI-Book"));

        this.goBible = goBible;

        String[] bookNames = goBible.bibleSource.getBookNames();

        Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);        
        int spaceWidth = f.stringWidth(" ");        
        
        int maxwidth = f.stringWidth(getBtnText(bookNames[0]));
        for (int i = 1; i <bookNames.length; i++) {
            String vertailu = getBtnText(bookNames[i]);
            if (f.stringWidth(vertailu) > maxwidth) {
                System.out.println("Longest index found: "+i+", "+bookNames[i]);
                maxwidth = f.stringWidth(vertailu);                        
            }
        }
                
        int width = maxwidth+4;
        int height = f.getHeight() + 2;
        
        for (int i = 0; i < bookNames.length; i++) {
            String btntext = getBtnText(bookNames[i]);
        
            Image txtimg = Image.createImage(width, height);
            txtimg.getGraphics().drawRoundRect(0, 0, width - 1, height - 1, TextStyle.fontHeight / 3, TextStyle.fontHeight / 3);
            // text centering
            int btntxtLength = btntext.length();
            if (btntxtLength == 1) {
                txtimg.getGraphics().drawString(btntext, width / 2 - spaceWidth / 2, 1, 0);
            } else {
                txtimg.getGraphics().drawString(btntext, 2, 1, 0);
            }
        
            ImageItem im = new ImageItem(null, txtimg, Item.LAYOUT_SHRINK | Item.LAYOUT_LEFT, String.valueOf(i), Item.HYPERLINK);
            im.setDefaultCommand(new Command("Set", Command.ITEM, 1));
            im.setPreferredSize(width, height);

            // icl is ItemCommandListener   
            im.setItemCommandListener(this);
            this.append(im);


        }

        addCommand(cancelCommand);

        setCommandListener(this);

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
     *
     * @param c
     * @param item
     */
    public void commandAction(Command c, Item item) {
        switch (c.getCommandType()) {
            case Command.ITEM: {
                int bookindex = (int) Integer.parseInt(((ImageItem) item).getAltText());
                int ch = 0;
                int ve = 0;
                int chaptersInBook = goBible.bibleSource.getNumberOfChapters(bookindex);


                // only 1 chapter, skip chapter selection and show verse selection instead
                if (chaptersInBook == 1) {
                    System.out.println("[GotoFormBook.commandAction()] only 1 chapter, skipping chapter selection (book "+bookindex+", chapter "+ch);
                    goBible.showGotoVerseScreen(bookindex, ch);
                    break;
                }

                goBible.showGotoChapterScreen(bookindex);

                break;
            }
        }
    }
    private String getBtnText(String input) {
        
        StringBuffer btntext = new StringBuffer();
                
        if (input.length() <= 5) {
            return input.substring(0);
        }
        btntext.append(input.substring(0,3));
        for (int i= 3; i<=5; i++) {
            if (isVowelorWS(input.charAt(i)))
            {                
                btntext.append(input.charAt(i));    
//                System.out.println("[getBtnText 1] "+btntext.toString());
            }
            else {
                btntext.append(input.substring(i,i+1));
//                System.out.println("[getBtnText 2] "+btntext.toString());
                break;
            }            
        }

//        System.out.println("[getBtnText 3] "+btntext.toString());
        return btntext.toString();              
    }
    
    private boolean isVowelorWS(char c) {
        return c=='a'||c=='e'||c=='i'||c=='o'||c=='u'||c=='ä'||c=='ö'||c==' ';
    }
}
