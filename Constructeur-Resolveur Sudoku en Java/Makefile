JAVAC=javac
JAVA=java


all: MenuSudoku.class SudokuResolveur.class SudokuBuilder.class

MenuSudoku.class: MenuSudoku.java
	$(JAVAC)  MenuSudoku.java

SudokuResolveur.class: SudokuResolveur.java
	$(JAVAC) SudokuResolveur.java

SudokuBuilder.class: SudokuBuilder.java
	$(JAVAC) SudokuBuilder.java

run:
	$(JAVA)  MenuSudoku

clean:
	rm -f *.class
