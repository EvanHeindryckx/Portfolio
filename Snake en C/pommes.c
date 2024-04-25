#include "pommes.h"
#include <graph.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>


void Pomme(int *pomme_x,int *pomme_y,int *segmentsX,int *segmentsY,int NOMBRE_SEGMENTS){
	int i, p;
	srand((Microsecondes())/1000000);
	for( p = 0; p < 5; p++){
		pomme_x[p] = ((rand() % (58)+1)*20);
		pomme_y[p] = ((rand() % (38)+1)*20);
		
		for(i=1;i<NOMBRE_SEGMENTS;i++){
			if(&pomme_x[p]==&segmentsX[i] && pomme_y[p]==segmentsY[i]){
				pomme_x[p] = ((rand() % (58)+1)*20);
				pomme_y[p] = ((rand() % (38)+1)*20);
				i = -1;
			}
		}
		ChoisirCouleurDessin(CouleurParComposante(240,15,15));
		RemplirRectangle(pomme_x[p],pomme_y[p], 20 ,20);

	}
}

int Collisions_Pommes(int *pomme_x, int *pomme_y, int *segmentsX, int *segmentsY){
	int i=0;

	for(i=0; i<5; i++){
		if((pomme_x[i]==segmentsX[0]) && (pomme_y[i]==segmentsY[0])){
			pomme_x[i] = ((rand() % (58)+1)*20);
			pomme_y[i] = ((rand() % (38)+1)*20);
			ChoisirCouleurDessin(CouleurParComposante(240,15,15));
			RemplirRectangle(pomme_x[i],pomme_y[i], 20 ,20);
			return 4;

	}

	}
}