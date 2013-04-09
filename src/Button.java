/*
 * Copyright © 2011 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 * See LICENSE.TXT for license information.
 */

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Button {
    
    protected int width;
    protected int height;
    protected int x = 0;
    protected int y = 0;
    private boolean isSelected = false;
    private boolean isDisabled = false;
    private boolean isVisible = true;

    // Constructor for button. The unpressed image can also be a sprite.
    public Button(String text, Font font) {
        
        width = font.stringWidth(text);
        height = font.getHeight() + 2;
        
    }
    public Button(String text, Font font, boolean visible) {
        this(text,font);
        isVisible = visible;
    }

//    
//    public void render(Graphics g) {
//        if (isVisible) {
//            if(isDisabled) {
//                g.drawImage(disabled, x, y, g.TOP | g.LEFT);
//            } else if (animated != null) {
//                animated.setPosition(x, y);
//                animated.paint(g);
//            } else if(isSelected) {
//                g.drawImage(pressed, x, y, g.TOP | g.LEFT);
//            } else {
//                g.drawImage(unpressed, x, y, g.TOP | g.LEFT);
//            }
//        }
//    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getPositionX() {
        return this.x;
    }
    public int getPositionY() {
        return this.y;
    }

    public boolean pressed(int x, int y) {        
        if(isDisabled) return false;
        if(contains(x, y)) {
            select();
            return true;
        }
        return false;
    }

    public boolean unpressed(int x, int y) {
        if(isDisabled) return false;        
        if(isSelected && contains(x, y)) {
            deselect();
            return true;
        }
        deselect();
        return false;
    }

    public boolean contains(int x, int y) {        
        if (x >= this.x && x <= this.x + width &&
            y >= this.y && y <= this.y + height) {
                return true;
        }
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void disable() {
        isDisabled = true;
    }
    public void enable() {
        isDisabled = false;
    }
    public void select() {
        isSelected = true;
    }

    public void deselect() {
        isSelected = false;
    }
    
    public void visible(boolean visible) {
        isVisible = visible;
    }
}