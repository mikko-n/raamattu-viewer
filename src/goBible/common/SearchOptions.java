package goBible.common;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daniel
 */

public class SearchOptions {
    private boolean exhausted = false;
    private int fromBook;
    private int toBook;
    private String searchString;
    private int currentBook;
    private int currentChapter;
    private int currentVerse;

    public SearchOptions(
            int fromBook,
            int toBook,
            String searchString,
            int currentBook,
            int currentChapter,
            int currentVerse) {
        this.fromBook = fromBook;
        this.toBook = toBook;
        this.searchString = searchString;
        this.currentBook = currentBook;
        this.currentChapter = currentChapter;
        this.currentVerse = currentVerse;
    }

    /**
     * @return the exhausted
     */
    public boolean isExhausted() {
        return exhausted;
    }

    /**
     * @param exhausted the exhausted to set
     */
    public void setExhausted(boolean exhausted) {
        this.exhausted = exhausted;
    }

    /**
     * @return the fromBook
     */
    public int getFromBook() {
        return fromBook;
    }

    /**
     * @return the toBook
     */
    public int getToBook() {
        return toBook;
    }

    /**
     * @return the currentBook
     */
    public int getCurrentBook() {
        return currentBook;
    }

    /**
     * @return the currentChapter
     */
    public int getCurrentChapter() {
        return currentChapter;
    }

    /**
     * @return the currentVerse
     */
    public int getCurrentVerse() {
        return currentVerse;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }
    
    public void dump() {
        System.err.println("Search Snapshot dump: ");
        System.err.println("\tfromBook: " + getFromBook());
        System.err.println("\ttoBook: " + getToBook());
        System.err.println("\tsearchString: " + getSearchString());
        System.err.println("\tcurrentBook: " + getCurrentBook());
        System.err.println("\tcurrentChapter: " + getCurrentChapter());
        System.err.println("\tcurrentVerse: " + getCurrentVerse());
        System.err.println();

    }
}