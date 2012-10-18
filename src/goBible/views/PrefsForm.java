package goBible.views;

//
//  PrefsForm.java
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

public class PrefsForm extends Form implements CommandListener {

    private GoBible goBible;
    private ChoiceGroup fontSizeChoice;
    private ChoiceGroup fontStyleChoice;
    private ChoiceGroup fullScreenChoice;
    private ChoiceGroup themeChoice;
    private ChoiceGroup redLetterChoice;
    private ChoiceGroup reverseColoursChoice;
    private ChoiceGroup reverseCharactersChoice;
    private String[] themeNames = new String[]{
        GoBible.getString("UI-Theme-Paper"),
        GoBible.getString("UI-Theme-Computer"),
        GoBible.getString("UI-Theme-Floral"),
        GoBible.getString("UI-Theme-Natural"),
        GoBible.getString("UI-Theme-Blue"),
        GoBible.getString("UI-Theme-Sunshine")
    };
    private Image[] themeImages;

    private Command saveCommand = new Command(GoBible.getString("UI-Save"), Command.OK, 0);
    private Command cancelCommand = new Command(GoBible.getString("UI-Cancel"), Command.CANCEL, 0);

    // Theme Form
    Form themeForm;

    private Image createThemeImage(int themeNumber) {

        Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        int width = 26;
        int height = 28;
        Image txtImg = Image.createImage(width, height);
        Graphics g = txtImg.getGraphics();
        // Fill the background
        g.setColor(GoBible.THEME_BACK_COLOUR[themeNumber]);
        g.fillRect(0, 0, width, height);
        // Draw the highlight bar
        g.setColor(GoBible.THEME_HIGHLIGHT_COLOUR[themeNumber]);
        g.fillRect(0, 0, width, 5);
        // Set the font
        g.setFont(font);
        g.setColor(GoBible.THEME_TEXT_COLOUR[themeNumber]);
        // Draw the string
        g.drawString("Aa", 2, height - font.getHeight() - 4, Graphics.LEFT | Graphics.TOP);

        return txtImg;
    }

    /**
     * Setup the Preferences form.
     */
    public PrefsForm(GoBible goBible) {
        super(GoBible.getString("UI-Preferences"));

        this.goBible = goBible;

        System.err.println("[PrefsForm.createThemeImage()] populating images");
        themeImages = new Image[]{
            createThemeImage(0), createThemeImage(1), createThemeImage(2),
            createThemeImage(3), createThemeImage(4), createThemeImage(5)
        };

        System.err.println("[PrefsForm const] before populating choiceGroup");
        themeChoice = new ChoiceGroup(GoBible.getString("UI-Theme") + ":", Choice.POPUP, themeNames, themeImages);

        themeChoice.setSelectedIndex(goBible.theme, true);

        append(themeChoice);

        fontSizeChoice = new ChoiceGroup(GoBible.getString("UI-Font-Size") + ":", Choice.EXCLUSIVE, new String[]{GoBible.getString("UI-Small"), GoBible.getString("UI-Medium"), GoBible.getString("UI-Large")}, null);
        fontSizeChoice.setFont(0, Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        fontSizeChoice.setFont(1, Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        fontSizeChoice.setFont(2, Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
        fontSizeChoice.setSelectedIndex(goBible.fontSize, true);
        append(fontSizeChoice);

        fontStyleChoice = new ChoiceGroup(GoBible.getString("UI-Font-Style") + ":", Choice.EXCLUSIVE, new String[]{GoBible.getString("UI-Plain"), GoBible.getString("UI-Bold")}, null);
        fontStyleChoice.setFont(0, Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        fontStyleChoice.setFont(1, Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        fontStyleChoice.setSelectedIndex(goBible.fontStyle, true);
        append(fontStyleChoice);

        if (GoBible.USE_MIDP20) {
            fullScreenChoice = new ChoiceGroup(GoBible.getString("UI-Full-Screen") + ":", Choice.EXCLUSIVE, new String[]{GoBible.getString("UI-On"), GoBible.getString("UI-Off")}, null);
            fullScreenChoice.setSelectedIndex(goBible.fullScreen ? 0 : 1, true);
            append(fullScreenChoice);
        }

        redLetterChoice = new ChoiceGroup(GoBible.getString("UI-Red-Letter") + ":", Choice.EXCLUSIVE, new String[]{GoBible.getString("UI-On"), GoBible.getString("UI-Off")}, null);
        redLetterChoice.setSelectedIndex(TextStyle.redLetter ? 0 : 1, true);
        append(redLetterChoice);

        reverseColoursChoice = new ChoiceGroup(GoBible.getString("UI-Reverse-Colours") + ":", Choice.EXCLUSIVE, new String[]{GoBible.getString("UI-On"), GoBible.getString("UI-Off")}, null);
        reverseColoursChoice.setSelectedIndex(goBible.reverseColours ? 0 : 1, true);
        append(reverseColoursChoice);

        reverseCharactersChoice = new ChoiceGroup(GoBible.getString("UI-Reverse-Characters") + ":", Choice.EXCLUSIVE, new String[]{GoBible.getString("UI-On"), GoBible.getString("UI-Off")}, null);
        reverseCharactersChoice.setSelectedIndex(TextStyle.reverseCharacters ? 0 : 1, true);
        append(reverseCharactersChoice);

        addCommand(saveCommand);
        addCommand(cancelCommand);
        setCommandListener(this);
    }

    /**
     * Part of CommandListener.
     */
    public void commandAction(Command command, Displayable display) {
        // If the prefs form is being displayed then process accordingly
        // otherwise the commands are coming from the themes form
        if (display == this) {
            switch (command.getCommandType()) {
                case Command.OK: {
                    // Save has been pressed so save the prefs
                    goBible.fontSize = fontSizeChoice.getSelectedIndex();
                    goBible.fontStyle = fontStyleChoice.getSelectedIndex();

                    if (GoBible.USE_MIDP20) {
                        goBible.fullScreen = (fullScreenChoice.getSelectedIndex() == 0);
                    }

//					goBible.theme = themeItem.id;
                    goBible.theme = themeChoice.getSelectedIndex();
                    
                    goBible.reverseColours = (reverseColoursChoice.getSelectedIndex() == 0);

                    TextStyle.reverseCharacters = (reverseCharactersChoice.getSelectedIndex() == 0);

                    TextStyle.redLetter = (redLetterChoice.getSelectedIndex() == 0);
                    
                    TextStyle.backColour = GoBible.THEME_BACK_COLOUR[goBible.theme];

                    // Go back to the main screen
                    goBible.showMainScreen();
                    break;
                }

                // Go back to the main screen without saving
                case Command.BACK:
                case Command.CANCEL: {
                    goBible.showMainScreen();
                }
            }
        } else if (display == themeForm) {
            // The command is coming from the themes form
            switch (command.getCommandType()) {
                // Go back to the prefs screen without changing the theme
                case Command.BACK:
                case Command.CANCEL: {
                    goBible.display.setCurrent(this);
                }
            }

        }
    }

 
}
