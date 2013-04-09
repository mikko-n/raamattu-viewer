/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goBible.canvas;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author NIM
 */
public class MapLink {
    
    private Hashtable sanaTaulu = null;
    public MapLink() {
        sanaTaulu = new Hashtable();
    }
    
    /**
     * Adds a word with boundaries to linking table
     * @param key word to add
     * @param boundaries rectangle surrounding the word
     */
    public void addWord(String key, Rectangle boundaries) {
        sanaTaulu.put(key, boundaries);        
        System.out.println("sanaTaulu.toString(): "+sanaTaulu.toString());
    }
    
    /**
     * Returns the string corresponding given coordinates, if any
     * @param x pointer x
     * @param y pointer y
     * @return string, or null if not found
     */
    public String contains(int x, int y) {
       
        String key;
        Rectangle value;
        for (Enumeration e = sanaTaulu.keys() ; e.hasMoreElements() ;) {
            key = (String)e.nextElement();
            value = (Rectangle)sanaTaulu.get(key);
            if (value != null) {
                System.out.println("[MapLink.contains] processing: "+key+", "+value);
                if (value.contains(x, y)) {
                    return key;
                }
            }            
        }
        return null;
    }
    
    public void clear() {
        sanaTaulu.clear();
    }
    
    public void removeWord(String key) {
        sanaTaulu.remove(key);        
    }
    
    public void update(int diffX, int diffY) {
        System.out.println("[MapLink.update("+diffX+", "+diffY+")]");
        
        String key;
        Rectangle value;
                
        Vector toBeRemoved = new Vector();
                
        for (Enumeration e = sanaTaulu.keys() ; e.hasMoreElements() ;) {
            key = (String)e.nextElement();
            value = (Rectangle)sanaTaulu.get(key);
            if (value != null) {
                System.out.println("[MapLink.update] updating: "+key+", "+value+" => ");
//                value.x += diffX;
                value.y += diffY;
                
                // if new value is beyond current screen, remove it
                if (value.y >= 320 || value.y <= 0) {
                    toBeRemoved.addElement(key);
                    System.out.println("[MapLink.update] "+key+", "+value+" marked for removal");
                } else {                
                    System.out.println("[MapLink.update] updated to: "+key+", "+value);
                }                
            }            
        }
        for (int i = 0; i<toBeRemoved.size();i++){
            System.out.println("[MapLink.update] "+(String)toBeRemoved.elementAt(i)+" removed");
            sanaTaulu.remove((String)toBeRemoved.elementAt(i));
        }
    }
}

