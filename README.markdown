Raamattu-viewer
===============
Raamattu-viewer is an open-source Bible reader application for J2ME (Java Micro Edition) enabled phones based
on [GNU GPLv2][1] -licenced GoBible 2.4 symscroll -branch from [Crosswire Bible Society][2].

Features added
--------------
*   Finnish UI-translation
*   Multi-translation support
*   .zip -file readonly support (via modified [JazzLib 0.07][3] and [Albite RandomReadingFile][4]
*   Bible data files zipped and moved to memory card
*   Updated theme selection form and added theme suitable for night-reading
*   Updated book/chapter/verse -forms more suitable for touch usage (similar to AndBible)
*   real fake *italic* text instead of different color+underlining
*   added exception handling and recovery from various error situations
*   keyboard shortcut to enter/exit fullscreen
*   new icon

ToolChain
---------
*    NetBeans IDE 7.1.1
*    Nokia SDK 1.0 for Java
*    Notepad++ 6.1.4
*    Git 1.7.8
*    [Sword utils][5]

Used libraries, API:s etc
-------------------------
### JazzLib
Pure *Java* implementation of `java.util.zip` classes ported to Java ME by me. This is done by using the [AlbiteRandomReadingFile API][4]. Reading .zip files relies on this.

### AlbiteRandomReadingFile
Implementation without writing capabilities of `RandomAccessFile` in Java Mobile (J2ME) using FileConnection.

Installation
------------
### From sources
1. Clone, compile, install
2. Continue from pre-compiled instructions step 2

### Pre-compiled binaries
1. Download viewer binaries from my public [Google Docs][gDocs] folder.
2. Extract reader JAD/JAR-files from Raamattu_viewer.zip and install to phone (via BlueTooth or data cable)
3. Download translations from [same place][gDocs]
4. Create folder `Raamattu/` to your phone memory card root and copy translation .zip files there as-is
5. Read Bible

All translations provided are Public Domain or licenced to allow free distribution.

Known issues and future development plans
-----------------------------------------
### Installation ease-up
*   Separate Bluetooth-app to send .jar/.jad files on Nokia phones (Nokia has disabled this feature)
*   Translation downloads from app
*   Custom translation folder instead of current hard-coded one
*   Download from app stores?

### Usage ease-up and improvements
*   Navigation clarification
*   possibility to show/hide footnotes, comments and cross-references
*   possibility to compare translations (show a single verse from many translations)
*   cross-references to direct links
*   adding Biblical maps
*   bugfixes

### Known bugs
*   changing translation with larger canon to one with fewer books
    - changing for example OT+NT translation to translation with only OT and current book is outside books contained in new translation causes ArrayOutOfBoundsException
*   exiting "select translation" screen without selecting translation causes exception (first time usage issue)

### Others
*   New name for app

[1]: http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt 
[2]: http://www.crosswire.org/gobible/newpage/index.jsp
[3]: http://jazzlib.sourceforge.net/
[4]: https://github.com/dumbledore/AlbiteRandomReadingFile
[5]: https://crosswire.org/svn/sword/trunk/
[gDocs]: https://docs.google.com/folder/d/0B82abpkursG5eEVFNjEzc1VpdVE/edit