#include <stdio.h>
#include <stdlib.h>
#include <graph.h>
#include "collisions.h"
#include "serpent.h"
#include "pommes.h"
#include "time_score.h"
#include "menu.h"
#include "mur.h"

int main(){
    char temps[6];
    char score[4];
    int murx[25], mury[25];
	couleur black = CouleurParComposante(0,0,0);
	couleur green = CouleurParComposante(163, 183, 99);
	couleur yellow = CouleurParComposante(255,255,204);
    couleur red = CouleurParComposante(240,15,15);
    couleur blanc = CouleurParComposante(255,255,255);
    int segmentsX[SEGMENTSMAX];
    int segmentsY[SEGMENTSMAX];
    int x_souris = 0;
    int y_souris = 0;
    int NOMBRE_SEGMENTS;
    char direction = 'r';
    int verifcollision = 0;
    int pomme_x[6], pomme_y[6];
    int tvb = 3;
    int mort=0;
    int minute = 0;
    int seconde = 0; 
    int ancienne_seconde = 0;
    int seconde_actuel = 0;

InitialiserGraphique();
    CreerFenetre(100, 100, 1240, 900);
    ChoisirTitreFenetre("Snake");
    EffacerEcran(black);

    while (tvb == 3) {
        tvb = AfficherMenu(); 
        if (tvb == 0) {
            ChoisirCouleurDessin(green);
            RemplirRectangle(20, 20, 1200, 800);
            InitialiserTailleSerpent(segmentsX, segmentsY, &NOMBRE_SEGMENTS);
            Pomme(pomme_x, pomme_y, segmentsX, segmentsY, NOMBRE_SEGMENTS);
            mur(murx, mury, segmentsX, segmentsY, NOMBRE_SEGMENTS);
        } else {
            if (tvb == 1){
                FermerGraphique();
            }
        }
    }
    while(tvb == 0 && verifcollision != 2){
        verifcollision = Deplacement(segmentsX, segmentsY, NOMBRE_SEGMENTS, direction);
        Serpent(segmentsX,segmentsY,NOMBRE_SEGMENTS);
        Keys(&direction);
        Score(NOMBRE_SEGMENTS);
        Timer(temps,&minute, &seconde, &ancienne_seconde, &seconde_actuel);
        if (Collisions_Murs(murx, mury, segmentsX, segmentsY, NOMBRE_SEGMENTS) == 2){
            verifcollision = 2;
        }
        if (Collisions_Pommes(pomme_x, pomme_y, segmentsX, segmentsY) == 4 ){
            NOMBRE_SEGMENTS ++;
        }
    }

    if(verifcollision==2){
        mort=1;
        sprintf(score,"%d",((NOMBRE_SEGMENTS - 10) *5));
        EffacerEcran(black);
        EcrireTexte(570,300,"Perdu!",2);
        EcrireTexte(400,440,"Temps final :",2);
        EcrireTexte(400,470,temps,2);
        EcrireTexte(650,440,"Score final :",2);
        EcrireTexte(650,470,score,2);
        ChoisirCouleurDessin(blanc);
        RemplirRectangle(550, 700, 170, 25);
        ChoisirCouleurDessin(red);
        EcrireTexte(570,720,"Rejouer?",2);

        while(mort==1){
            if (SourisCliquee()) {
                x_souris = _X;
                y_souris = _Y;
                if ((x_souris>=550) && (x_souris<=720)){
                    if ((y_souris>=700) && (y_souris<=725)){
                        FermerGraphique();
                        main();
                    }
                }
            }    
        }
    }
    

    Touche();
    FermerGraphique();
    return EXIT_SUCCESS;
}