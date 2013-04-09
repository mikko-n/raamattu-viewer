/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public final class GBCToolkit {

   /**
     * Follows the Unicode standard for determining if a code point is
     * a Unicode space character
     */
    public static boolean isSpace(char ch)
    {
            boolean result = false;
            switch (ch)
            {
                    case 0x0020: // SPACE
                    case 0x1680: // OGHAM SPACE MARK
                    case 0x2000: // EN QUAD
                    case 0x2001: // EM QUAD
                    case 0x2002: // EN SPACE
                    case 0x2003: // EM SPACE
                    case 0x2004: // THREE-PER-EM SPACE
                    case 0x2005: // FOUR-PER-EM SPACE
                    case 0x2006: // SIX-PER-EM SPACE
                    case 0x2007: // FIGURE SPACE
                    case 0x2008: // PUNCTUATION SPACE
                    case 0x2009: // THIN SPACE
                    case 0x200A: // HAIR SPACE
                    case 0x200B: // ZERO WIDTH SPACE
                    case 0x205F: // MEDIUM MATHEMATICAL SPACE
/* Two versions of the Chinese CUV exists. One version uses two words
* <shang> <di> to mean "God". The other version uses <IDEOGRAPHIC SPACE> <shen>
* to mean "God". (A vestige from the days of moveable types - preserving the
* character count by creating a space was easier than deleting a word and
* messing up the layout.) However, in the latter case, it is obviously not
* meant to be wrapped at the <IDEOGRAPHIC SPACE>.
*
* For the benefit of the Chinese translations, we break unicode rules and
* treat the ideographic space as a normal character.
*/
//			case 0x3000: // IDEOGRAPHIC SPACE
                            result = true;
                            break;
                    default:
                            break;
            }
            return result;
    }

    /**
     * Follows the Unicode standard for determining if a code point is
     * a Unicode non-breaking space (NBSP)
     */
    public static boolean isNonBreakingSpace(char ch)
    {
            boolean result = false;
            switch ((int)ch)
            {
                    case 0x00A0: // NO-BREAK SPACE
                    case 0x202F: // NARROW NO-BREAK SPACE
                    case 0x2060: // WORD JOINER (a zero width non-breaking space)
                            result = true;
                            break;
                    default:
                            break;
            }
            return result;
    }

    /**
     * Is this a breaking character?
     * - See if it's a space, tab, etc...
     */
    public static boolean isBreakingCharacter(char ch)
    {
            boolean result = isSpace(ch);
            if (result)
            {
                    return result;
            }

            switch ((int)ch)
            {
                    case 0x0009: // TAB
                    case 0x00AD: // SOFT HYPHEN (SHY)
                            result = true;
                            break;
                    default:
                            break;
            }
            return result;
    }

    /**
     * Convert from a Surrogate Pair to the int Code Point
     *
     * @param sp1 Surrogate Pair - 1st UTF-16 char
     * @param sp2 Surrogate Pair - 2nd UTF-16 char
     * @return integer code point value
     */
    public static int SPtoCP(char sp1, char sp2)
    {
            int cp = ((sp1 - 0xd800) << 10) + (sp2 - 0xDC00) + 0x10000;
            return cp;
    }

    /**
     * Returns a surrogate pair number (1 or 2) OR zero if not a surrogate pair
     */
    public static int isSurrogatePair(char ch)
    {
            if (ch >= 0xD800 && ch <= 0xDBFF) 	// 0xD800-0xDBFF
            {
                    return 1;
            }

            if (ch >= 0xDC00 && ch <= 0xDFFF)	// 0xDC00-0xDFFF
            {
                    return 2;
            }

            return 0;
    }

    /**
     * Reverses RTL characters for devices that do not support them.
     *
     * Note that this will frequently reverse characters that should not
     * be reversed, e.g. numeric digits.
     *
     * Detection of these characters can be left to a later implementation.
     *
     * @return
     */
    public static String reverseCharacters(String s) {
        return new StringBuffer(s).reverse().toString();
    }
    public static void myAssert(boolean expression, String message) {
        if (!expression) {
            System.err.println("ASSERT FAILED");
            System.err.println(message);
            throw new RuntimeException(message);
        }
    }


    public static void quicksort(int a[], int start, int end) {

        if (end - start <= 1) { // 0- or 1-element lists needs no sorting
            //System.out.println("element = " + a[start]);
            return;
        }
        // this will always take the centre, or left-of-centre element
        // therefore, pivot element(s) should always go to the first partition
        int pivotIndex = start + (end - 1 - start) / 2;
        int pivot = a[pivotIndex];

        //System.out.println("pivot = " + pivot);
        for (int i=start; i<end; i++) {
            System.out.print(" " + a[i] + ", ");
        }
        System.out.println();

        int i = start, j = end - 1;

        // move everything larger than pivot to right of pivot
        while (j >= i) {
            while ( a[i] < pivot ){
                i ++;
            }
            // a[i] is an element >= pivot
            while ( a[j] > pivot ){
                j --;
            }
            // a[j] is an element <= pivot
            if (j >= i) { // the element <= pivot comes after element >= pivot
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                // now a[i] is an <= pivot
                // a[j] is an element >= pivot
                // and j > i
                j--;
                i++;
            }
        }
        // now, j < i
        // if j < i, i.e. a[j] <= pivot value, a[i] >= pivot value
        // if a[i] is equal to pivot element, take start - i (start, i+1)
        // if a[i] is more than pivot element -- is this possible?
        // yes

        for (int i2=start; i2<end; i2++) {
            System.out.print(" " + a[i2] + ", ");
        }
        //System.out.println();
        quicksort(a, start, i);
        quicksort(a, i, end);
    }
    public static void quicksort(short a[], int start, int end) {
        if (end - start <= 1) { // 0- or 1-element lists needs no sorting
            //System.out.println("element = " + a[start]);
            return;
        }
        // this will always take the centre, or left-of-centre element
        // therefore, pivot element(s) should always go to the first partition
        int pivotIndex = start + (end - 1 - start) / 2;
        short pivot = a[pivotIndex];

        int i = start, j = end - 1;

        // move everything larger than pivot to right of pivot
        while (j >= i) {
            while ( a[i] < pivot ){
                i ++;
            }
            // a[i] is an element >= pivot
            while ( a[j] > pivot ){
                j --;
            }
            // a[j] is an element <= pivot
            if (j >= i) { // the element <= pivot comes after element >= pivot
                short temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                // now a[i] is an <= pivot
                // a[j] is an element >= pivot
                // and j > i
//                for (int i2=start; i2<end; i2++) {
//                    System.out.print(" " + a[i2] + ", ");
//                }
//                System.out.println();
                j--;
                i++;
            }
        }
        // now, j < i
        // if j < i, i.e. a[j] <= pivot value, a[i] >= pivot value
        // if a[i] is equal to pivot element, take start - i (start, i+1)
        // if a[i] is more than pivot element -- is this possible?
        // yes

        for (int i2=start; i2<end; i2++) {
            System.out.print(" " + a[i2] + ", ");
        }
        //System.out.println();
        quicksort(a, start, i);
        quicksort(a, i, end);
    }


    /**
     * Replaces a substring (needle) in a string (haystack) with another string
     * (replacement)
     *
     * @param haystack
     * @param needle
     * @param replacement
     * @return The haystack with the substitutions made.
     */
    public static String replaceSubstring(String haystack, String needle, String replacement) {
        StringBuffer returnValue = new StringBuffer();
        int keywordIndex = 0, offset = 0;
        for (
                keywordIndex = haystack.indexOf(needle, offset);
                keywordIndex >= 0;
                offset = keywordIndex + needle.length(),
                keywordIndex = haystack.indexOf(needle, offset)
        ) {
            returnValue.append(haystack.substring(offset, keywordIndex));
            returnValue.append(replacement);
        }
        returnValue.append(haystack.substring(offset));
        return returnValue.toString();
    }
}
