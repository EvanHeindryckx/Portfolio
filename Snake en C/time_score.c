#include <graph.h>
#include <stdio.h>
#include <stdlib.h>
#include <graph.h>
#include "time_score.h"

void Score(int NOMBRE_SEGMENTS){
    char score[4];
    int nombre = ((NOMBRE_SEGMENTS - 10) *50);
    snprintf(score,4,"%04d", nombre);
    ChoisirCouleurDessin(CouleurParComposante(0,0,0));
    RemplirRectangle(1100,830,1200,900);
    ChoisirCouleurDessin(CouleurParComposante(255,255,255));
    EcrireTexte(1100,850,"Score",2);
    EcrireTexte(1100,880,score,2);
}

void Timer(char temps[6], int *minute, int *seconde, int *ancienne_seconde, int *seconde_actuel){
    *seconde_actuel = Microsecondes()/1000000;
    if (*seconde_actuel != *ancienne_seconde){
        *seconde = *seconde + 1;
        *ancienne_seconde = *seconde_actuel;
    }
    if (*seconde == 60){
        *seconde = 0;
        *minute = *minute + 1;
    }
    snprintf(temps,6,"%02d:%02d", *minute, *seconde);
    ChoisirCouleurDessin(CouleurParComposante(0,0,0));
    RemplirRectangle(0,830,900,900);
    ChoisirCouleurDessin(CouleurParComposante(255,255,255));
    EcrireTexte(10,880,"Temps :",2);
    EcrireTexte(130,880,temps,2);
} 

