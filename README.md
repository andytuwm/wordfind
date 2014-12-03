Wordfind by Andy Tu
========
A word solver for the mobile game [Wordbase](https://play.google.com/store/apps/details?id=com.wordbaseapp&hl=en).

Download: 
========
Download the zip file from the [release](https://github.com/andytuwm/wordfind/releases) page. Make sure both the folder and .exe file are unzipped together. If the .exe file does not work for whatever reason, try using `java -jar wordfind.jar` in the command prompt from the same directory.

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
