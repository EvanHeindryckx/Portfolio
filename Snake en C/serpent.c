#include "serpent.h"
#include <graph.h>
#include <stdio.h>
#include <stdlib.h>

void InitialiserTailleSerpent(int *segmentsX, int *segmentsY, int *NOMBRE_SEGMENTS){
	int i;
	for (i = 0; i < 10; i++){
		segmentsX[i] = 600 - i * 20;
		segmentsY[i] = 400;
	    *NOMBRE_SEGMENTS = 10;
	}
}


void Serpent(int *segmentsX, int *segmentsY, int NOMBRE_SEGMENTS){
	int i;
	couleur yellow = CouleurParComposante(255,255,204);
	couleur green = CouleurParComposante(163, 183, 99);
	ChoisirCouleurDessin(yellow);
	for (i = 0; i<NOMBRE_SEGMENTS; i++){
		RemplirRectangle(segmentsX[i],segmentsY[i],20,20);
	}
	ChoisirCouleurDessin(green);
	RemplirRectangle(segmentsX[NOMBRE_SEGMENTS - 1], segmentsY[NOMBRE_SEGMENTS - 1], 20 ,20);
}

void Attendre(int microsecondes){
	unsigned long int attente = Microsecondes() + microsecondes;
	while(Microsecondes()<attente){

	}
}


int Deplacement(int *segmentsX, int *segmentsY, int NOMBRE_SEGMENTS, char direction){
	int i;
	for (i = NOMBRE_SEGMENTS -1; i>0; i--){
		segmentsX[i] = segmentsX[i - 1];
		segmentsY[i] = segmentsY[i -1];
	}

	if (direction=='u'){
		segmentsY[0] -= 20;
	} else if (direction=='d'){
		segmentsY[0] += 20;
	} else if (direction=='r'){
		segmentsX[0] += 20;
	} else if (direction=='l'){
		segmentsX[0] -= 20;
	}  
	Attendre(100000);
	return Check_Collisions(segmentsX, segmentsY, NOMBRE_SEGMENTS);
}

int Check_Collisions(int *segmentsX, int *segmentsY, int NOMBRE_SEGMENTS){
	int i;
	if (segmentsX[0] < 40 || segmentsX[0] > 1180 || segmentsY[0] < 40 || segmentsY[0] > 780){
		return 2;
	}

	for (i = 1 ; i <NOMBRE_SEGMENTS ; i++) {
		if (segmentsX[0] == segmentsX[i] && segmentsY[0] == segmentsY[i]){
			return 2;
		}
	}
	return 0;
}

void Keys(char *direction){
	if(ToucheEnAttente()){
		int touche = Touche();
		switch(touche){	
			case XK_Left : 
				if(*direction!='r'){
					*direction='l';
				}
				break;
			case XK_Right:
				if(*direction!='l'){
					*direction='r';
				}
				break;
			case XK_Up:
				if(*direction!='d'){
					*direction='u';
				}
				break;
			case XK_Down:
				if(*direction!='u'){
					*direction='d';
				}
				break;
		}
	}
}