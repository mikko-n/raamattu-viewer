package goBible.base;

/**
 * MultiTranslationBibleSource.java
 * @author NIM
 */
import goBible.common.TranslationNotFoundException;
import goBible.views.SelectTranslationList;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

/**
 * Class used to read multiple translations simultaneously. Combines verse data
 * from available translations and wraps them to format that BibleCanvas 
 * understands.
 * 
 * @author NIM
 */
public class MultiTranslationBibleSource extends BibleSource {
        
    private GoBible goBible;
    
    /**
     * Currently selected translation in normal reading mode
     */
    private String REFERENCE_BIBLE_DATA_FOLDER;
    
    /**
     * Available translations' translation root folders
     */
    private Vector AVAILABLE_TRANSLATIONS;    
    
    private BibleSource[] translations;
    
    private int currentBookIndex = -1;
    private int currentFileIndex = -1;
    private int currentChapterIndex = -1;
    
    // Bible index
    private int numberOfBooks;
    
    // Current "chapter" data == combined verse data from all translations
    private char[] verseData;
    private int verseDataSize;    
    
    
    public MultiTranslationBibleSource(GoBible goBible) throws IOException, TranslationNotFoundException {
        this(goBible, goBible.getTranslation());
        this.goBible = goBible;        
        
    }

    public MultiTranslationBibleSource(GoBible goBible, String translationRoot) throws IOException, TranslationNotFoundException {
        super(goBible);
        AVAILABLE_TRANSLATIONS = SelectTranslationList.listAvailableTranslations(goBible);
        System.err.println("available translations: "+AVAILABLE_TRANSLATIONS.toString());
        
        if (translationRoot == "" || translationRoot == null) {
            REFERENCE_BIBLE_DATA_FOLDER = GoBible.BIBLE_DATA_ROOT + "FinPR92";
        } else {
            REFERENCE_BIBLE_DATA_FOLDER = GoBible.BIBLE_DATA_ROOT + translationRoot;
        }
        translations = new BibleSource[AVAILABLE_TRANSLATIONS.size()];
        
        for (int i = 0; i< AVAILABLE_TRANSLATIONS.size(); i++) {
            System.out.println(AVAILABLE_TRANSLATIONS.elementAt(i).toString());
            
            translations[i] = new CombinedChapterBibleSource(goBible, AVAILABLE_TRANSLATIONS.elementAt(i).toString());
        }
        
        numberOfBooks = getNumberOfBooks();
        
    }
    
    public char[] getChapter(int bookIndex, int chapterIndex) throws IOException {
        loadChapter(bookIndex, chapterIndex);
        return verseData;
    }

    public int getVerseDataSize() {
        return verseDataSize;
    }

    public int[] getChapterIndex(int bookIndex, int chapterIndex) throws IOException {
        // Load the chapter if it isn't loaded
        loadChapter(bookIndex, chapterIndex);
        
        return translations[0].getChapterIndex(bookIndex, chapterIndex);        
    }

    public int getStartChapter(int bookIndex) {
        return translations[0].getStartChapter(bookIndex);
    }

    public String[] getBookNames() {
        return translations[0].getBookNames();
    }

    public String getBookName(int bookIndex) {
        return translations[0].getBookName(bookIndex);
    }

    public int getNumberOfBooks() {
        int max = translations[0].getNumberOfBooks();
        for (int i = 1; i<translations.length; i++) {
            int compare =translations[i].getNumberOfBooks();
            if (compare > max) {
                max = compare;
            }
        }
        return max;
    }

    public int getNumberOfChapters(int bookIndex) {
        return translations[0].getNumberOfChapters(bookIndex);
    }

    public int getNumberOfVerses(int bookIndex, int chapterIndex) {
        return translations[0].getNumberOfVerses(bookIndex, chapterIndex);
    }
    
    private void loadChapter(int bookIndex, int chapterIndex) throws IOException {
        
        if (currentBookIndex != bookIndex || currentChapterIndex != chapterIndex) {
            if (currentBookIndex != bookIndex) {
                currentBookIndex = bookIndex;
            }
            currentChapterIndex = chapterIndex;
        }
        // 1. for chapter in reference translation
        //  -get chapter data and verse count from current chapter
        //  -iterate through translations
        //      -get verse index from current verse
        //      -get verse end index (= current verse+1 index -1)
        //      -read verse and append to combined verse data
        
        StringBuffer buf = new StringBuffer();

        for (int verseNumber = 0; verseNumber < translations[0].getNumberOfVerses(bookIndex, chapterIndex); verseNumber++) {
            for (int i=0; i<translations.length; i++) {
                buf.append(((CombinedChapterBibleSource)translations[i]).getVerse(bookIndex, chapterIndex, verseNumber));    
            }
        }
        verseDataSize = buf.length();
        verseData = new char[verseDataSize];
        verseData = buf.toString().toCharArray();
    }
    
    public int getBookIdFromNumber(int bookNumber) {
        return translations[0].getBookIdFromNumber(bookNumber);
    }
    
    public int getBookNumberFromIndex(int bookIndex) {
        return translations[0].getBookNumberFromIndex(bookIndex);
    }
    public int getVerseIndexFromNumber(int bookIndex, int chapterIndex, int verseNumber) {
        return translations[0].getVerseIndexFromNumber(bookIndex, chapterIndex, verseNumber);
    }
    public int getVerseNumberFromIndex(int bookIndex, int chapterIndex, int verseIndex) {
        return translations[0].getVerseNumberFromIndex(bookIndex, chapterIndex, verseIndex);
    }
    
}
