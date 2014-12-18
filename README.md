Wordfind by Andy Tu
========
A word solver for the mobile game [Wordbase](https://play.google.com/store/apps/details?id=com.wordbaseapp&hl=en).

Download: 
========
Download the zip file from the [release page](https://github.com/andytuwm/wordfind/releases). Extract all contents into the same directory. Click "launch.bat" to start the program. Run "launch.bat" as admin if Windows prevents you from running the batch script. If problems with launching the program still persists, try installing the latest [Java runtime](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html).

Commands Available:
=========

help - displays this help menu.

solve - Show longest words you can play. Relative increase towards opponent base is shown.

next - display next 20 solutions.

back - display last 20 solutions.

analyzeBestReach - show best words that allow for maximum distance gain towards opponent's base.

analyzePosition - show words closest to opponent's base.

analyzeWin - show words that allow  you to win - processing may take time.

analyzeOpponent - show words the opponent can potentially win with and their respective rows.

analyzePoint - show all words you can play from a specific letter, determined by X-Y coordinates. Coordinates must be inputted in the following format: `a b` where a and b are positive integers separated by a space character. No parentheses should be inputted and the coordinate system is always the same regardless which side your base is on. The X-Y coordinates start from 0 to 9 for x-axis and 0 to 12 for y-axis. Examples: `0 0` is always bottom left, `9 12` is always top right, `2 5` is the letter N in the sample board below.
		  
setBoard - set board file to build from; txt file must be in the folder files.

setDictionary - select either English dictionary or French dictionary. Default is English.

quit - end program.

Note:
===
Rows are always counted from the base (Yours or the Opponent's). Base row = 1

Board Formatting
====
Boards must be formatted as such:

1) First line is <# of columns> <# of rows>, separated by spaces.

2) Starting from the top row, type out letters of the actual game board, with each row separated by a nextline.

3) Capitalize each letter you have access to. This means your base will be entirely capitalized.

Sample board:
````
10 13
hiotyerhoc
lemhviluru
irsguqagio
txilnrlizs
aeloesated
nstkdtiain
yneiasnevu
srntltIhrl
EcArocoasi
oTIleucfdk
uVsctpdmlc
DANeiseaiu
CUJGXUWBEP
````

In the above sample board, the user's base is at the bottom, and is player blue. Letters the user has access to, outside of the base, are D, A, N, V, T, I, E, A.

Capitalized letters must be updated with each round of moves.
