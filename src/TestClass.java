/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

/**
 *
 * @author daniel
 */
public class TestClass extends MIDlet {

    public TestClass() {
        System.out.println("GoBibleCore test class");
    }
    
    public void destroyApp(boolean unconditional) {
    }
    public void pauseApp() {
    }
    public void startApp() {
        testSorter();

        this.notifyDestroyed();
    }

    public void testSorter() {
        int r[] = new int[15];
        java.util.Random rand = new java.util.Random();

        for (int i=0; i<r.length; i++) {
            r[i] = rand.nextInt() % 5;
        }

       // r = new int[] {86,  46,  68,  -85,  -41,  -72,  -33,  -1,  -25,  -41,  -75,  83,  64,  10,  34};
        GBCToolkit.quicksort(r,0,r.length);

        System.out.println("Test Sorting:\nr[0] = " + r[0]);
        for (int i=1; i<r.length; i++) {
            System.out.println("r[" + i + "] = " + r[i] + ". Correct? " + (r[i] >= r[i-1]));
        }
    }

}
