/*
 * Sudokuki - essential sudoku game
 * Copyright (C) 2007-2013 Sylvain Vedrenne
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
#include <stdlib.h>

#include "suexg/gene_suexg_v12.h"

#include "net_jankenpoi_sudokuki_generator_suexg_SuexgProxy.h"

/*
 * Class:     net_jankenpoi_sudokuki_generator_suexg_SuexgProxy
 * Method:    generateSuexgGrid
 * Signature: (III[I[I[I)I
 */
JNIEXPORT jint JNICALL Java_net_jankenpoi_sudokuki_generator_suexg_SuexgProxy_generateSuexgGrid
  (JNIEnv * env, jobject j_obj, jint seed, jint requestedRatingMin, jint requestedRatingMax, jintArray result_grid, jintArray result_rating, jintArray result_grid_and_clues)
{
        //printf("\nBEGINNING OF PROXY NATIVE METHOD\n");

	jint* grid = malloc(81 * sizeof(int));
    jint* grid_and_clues = malloc(81 * sizeof(int));

	//printf("grid before call to suexg code:\n");

	//int i = 0;
	//for (i=0; i<81; i++) {
        //		printf("%d", grid[i]);
	//}

	//printf("\n");

        jint rating = -2;
	grid_generate (seed, requestedRatingMin, requestedRatingMax, (int**)&grid, (int*)&rating, (int**)&grid_and_clues );

	//printf("after call to grid_generate - rating:%d\n", rating);

	//printf("grid:\n");

	//for (i=0; i<81; i++) {
        //		printf("%d", grid[i]);
	//}

	//printf("\n");

	//printf("returning grid:0x%x\n", grid);

	(*env)->SetIntArrayRegion(env, result_grid, 0, 81, grid);
        (*env)->SetIntArrayRegion(env, result_rating, 0, 1, &rating);
        (*env)->SetIntArrayRegion(env, result_grid_and_clues, 0, 81, grid_and_clues);

	//printf("END OF PROXY NATIVE METHOD\n");

	return 0;
}
