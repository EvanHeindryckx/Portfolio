#ifndef SERPENT_H
#define SERPENT_H
#define SEGMENTSMAX 2390
void Serpent(int *segmentsX, int *segmentsY, int NOMBRE_SEGMENTS);
int Deplacement(int *segmentsX, int *segmentsY, int NOMBRE_SEGMENTS, char direction);
int Check_Collisions(int *segmentsX, int *segmentsY, int NOMBRE_SEGMENTS);
void Attendre(int microsecondes);
void Keys();

#endif