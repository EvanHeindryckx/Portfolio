#include "mur.h"
#include <graph.h>
#include <stdio.h>
#include <stdlib.h>

void mur(int *murx, int *mury, int *segmentsX, int *segmentsY, int NOMBRE_SEGMENTS){
    srand((Microsecondes())/1000000);
    int i;
    for(i = 0; i < 25; i++)
    {
        murx[i] = ((rand() % (58) + 1) * 20);
        mury[i] = ((rand() % (38) + 1) * 20);
        ChoisirCouleurDessin(CouleurParComposante(50,50,50));
        RemplirRectangle(murx[i], mury[i], 20, 20);
    }
    for(i=1;i<NOMBRE_SEGMENTS;i++){
			if(&murx[i]==&segmentsX[i] && mury[i]==segmentsY[i]){
			    murx[i] = ((rand() % (58)+1)*20);
				mury[i] = ((rand() % (38)+1)*20);
				i = -1;
			}
}
}

int Collisions_Murs(int *murx, int *mury, int *segmentsX, int *segmentsY, int NOMBRE_SEGMENTS){
    int i;
    for(i = 0; i < 25; i++){
        if((murx[i] == segmentsX[0]) && (mury[i] == segmentsY[0])){
            printf("Collision mur\n");
            return 2;

        }
    }
}