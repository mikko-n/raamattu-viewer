/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goBible.common;

import goBible.base.GoBible;

/**
 * A PassageReference is a reference to a passage + line numbers.
 *
 * This is contrasted with GoBible class, which is now used purely
 * as an abstraction for loading data.
 * @author Daniel
 */
public class PassageReference {
    private int verseIndex;
    private int lineOffset;
    private int bookIndex;
    private int chapterIndex;
    private GoBible goBible;

    public PassageReference(GoBible goBible) {
        this.goBible = goBible;
    }

    /**
     * @return the bookIndex
     */
    public int getBookIndex() {
        return bookIndex;
    }

    /**
     * @return the chapterIndex
     */
    public int getChapterIndex() {
        return chapterIndex;
    }

    /**
     * @return the lineOffset
     */
    public int getLineOffset() {
        return lineOffset;
    }

    /**
     * @return the verseIndex
     */
    public int getVerseIndex() {
        return verseIndex;
    }

    /**
     * @param bookIndex the bookIndex to set
     */
    public void setBookIndex(int bookIndex) {
        this.bookIndex = bookIndex;
    }

    /**
     * @param chapterIndex the chapterIndex to set
     */
    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    /**
     * @param lineOffset the lineOffset to set
     */
    public void setLineOffset(int lineOffset) {
        this.lineOffset = lineOffset;
    }

    /**
     * @param verseIndex the verseIndex to set
     */
    public void setVerseIndex(int verseIndex) {
        this.verseIndex = verseIndex;
    }
    public synchronized PassageReference clone() {
        PassageReference ctx = new PassageReference(this.goBible);
        ctx.setVerseIndex(getVerseIndex());
        ctx.setLineOffset(getLineOffset());
        ctx.setBookIndex(getBookIndex());
        ctx.setChapterIndex(getChapterIndex());
        return ctx;
    }
    public boolean sameChapter(PassageReference c) {
        return getBookIndex() == c.getBookIndex() && getChapterIndex() == c.getChapterIndex();
    }
    public boolean sameBook(PassageReference c) {
        return getBookIndex() == c.getBookIndex();
    }
    public boolean sameVerse(PassageReference c) {
        return getBookIndex() == c.getBookIndex() && getChapterIndex() == c.getChapterIndex() && getVerseIndex() == c.getVerseIndex();
    }
    public synchronized void cloneInto(PassageReference ctx) {
        synchronized (ctx) {
            ctx.setVerseIndex(getVerseIndex());
            ctx.setLineOffset(getLineOffset());
            ctx.setBookIndex(getBookIndex());
            ctx.setChapterIndex(getChapterIndex());
        }
    }
    public String toString() {
        return "Bk " + getBookIndex() + " Ch " + getChapterIndex() + " Ve " + getVerseIndex() + " Ln " + getLineOffset();
    }

    /* Navigation functions */
    public void nextChapter() {
        setChapterIndex(getChapterIndex() + 1);
        if (getChapterIndex() >= goBible.bibleSource.getNumberOfChapters(getBookIndex()))
        {
                nextBook();
        }
        
        lineOffset = verseIndex = 0;
    }
    public void previousChapter() {
        setChapterIndex(getChapterIndex() - 1);
        if (getChapterIndex() < 0)
        {
            previousBook();
            
            // Go to the last chapter in the previous book
            setChapterIndex(goBible.bibleSource.getNumberOfChapters(getBookIndex()) - 1);
        }
        lineOffset = verseIndex = 0;
    }

    public void nextBook() {
        // Go to the next book
        setBookIndex(getBookIndex() + 1);

        // Wrap
        if (getBookIndex() >= goBible.bibleSource.getNumberOfBooks())
        {
            setBookIndex(0);
        }
        lineOffset = chapterIndex = verseIndex = 0;
    }
    public void previousBook() {
        // Go to the previous book
        setBookIndex(getBookIndex() - 1);

        // Wrap
        if (getBookIndex() < 0) {
            setBookIndex(goBible.bibleSource.getNumberOfBooks() - 1);
        }

        lineOffset = chapterIndex = verseIndex = 0;
    }

    public void nextVerse() {
        setVerseIndex(getVerseIndex() + 1);
        if (getVerseIndex() >= goBible.bibleSource.getNumberOfVerses(getBookIndex(), getChapterIndex()))
        {
                nextChapter();
        }
        setLineOffset(0);
    }
    public void previousVerse() {                        
        setVerseIndex(getVerseIndex() - 1);
        if (getVerseIndex() < 0)
        {
                previousChapter();
        }
        setLineOffset(0);   
    }
}
