#include "collisions.h"
#include <graph.h>
#include <stdio.h>
#include <stdlib.h>
int Collision_Murs(int x, int y){
	if((x<0) || (x>1200) || (y<0) || (y>800)){
		return 2;
	} else {
		return 0;
	}	
}
