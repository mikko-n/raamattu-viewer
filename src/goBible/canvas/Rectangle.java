/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goBible.canvas;

/**
 *
 * @author NIM
 */
public class Rectangle {

    public int x, y, width, height;

    public Rectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public Rectangle() {
        this(0, 0, 0, 0);
    }

    public boolean contains(int x, int y) {
        if (this.x <= x && x <= this.x + this.width
                && this.y <= y && y <= this.y + this.height) {
            return true;
        }
        return false;
    }
        
    public String toString() {
        return "x"+x+" y"+y+" w"+width+" h"+height;
    }
}