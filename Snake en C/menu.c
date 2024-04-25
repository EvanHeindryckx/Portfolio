#include <stdio.h>
#include <graph.h>
#include "menu.h"

int AfficherMenu() {
    int selection = 1;
    int tailleTexte = 2;

    while (selection == 1) {
        if (SourisCliquee()) {
            int x_souris = _X;
            int y_souris = _Y;

            if (x_souris >= 400 && x_souris <= 600) {
                if (y_souris >= 300 && y_souris <= 325) {
                     return 0 ;

                } else if (y_souris >= 400 && y_souris <= 425) {
                    return 1;
                }
            }
        }

        ChoisirCouleurDessin(CouleurParComposante(163, 183, 99));
        RemplirRectangle(400, 300, 200, 25); 
        RemplirRectangle(400, 400, 200, 25); 
        
        ChoisirCouleurDessin(CouleurParComposante(0, 0, 0));
        EcrireTexte(450, 315, "Jouer", tailleTexte);
        EcrireTexte(450, 415, "Quitter", tailleTexte);

    }

    FermerGraphique();
}

