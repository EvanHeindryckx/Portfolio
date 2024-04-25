CC=gcc
CFLAGS= -ansi -pedantic -lgraph
LDFLAGS= -lgraph
EXEC=snake

all: $(EXEC)

snake: serpent.o collisions.o pommes.o time_score.o menu.o mur.o main.o 
	$(CC) -o snake collisions.o serpent.o pommes.o time_score.o mur.o menu.o main.o $(LDFLAGS)

collisions.o: collisions.c
	$(CC) -o collisions.o -c collisions.c $(CFLAGS)

serpent.o: serpent.c
	$(CC) -o serpent.o -c serpent.c $(CFLAGS)

pommes.o: pommes.c
	$(CC) -o pommes.o -c pommes.c $(CFLAGS)

time_score.o: time_score.c
	$(CC) -o time_score.o -c time_score.c $(CFLAGS)	

menu.o: menu.c
	$(CC) -o menu.o -c menu.c $(CFLAGS)

mur.o: mur.c
	$(CC) -o mur.o -c mur.c $(CFLAGS)

main.o: main.c serpent.h collisions.h pommes.h time_score.h menu.h mur.h
	$(CC) -o main.o -c main.c $(CFLAGS)


clean:
	rm -rf *.o

run : snake
	./snake

mrproper: clean
	rm -rf $(EXEC)
