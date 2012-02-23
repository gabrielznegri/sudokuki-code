////////////////////////////////////////////////////////////////////////////////
// sudokuki - C++ graphical sudoku game                                       //
// Copyright (C) 2007-2009 Sylvain Vedrenne                                   //
//                                                                            //
// This program is free software; you can redistribute it and/or              //
// modify it under the terms of the GNU General Public License                //
// as published by the Free Software Foundation; either version 2             //
// of the License, or (at your option) any later version.                     //
//                                                                            //
// This program is distributed in the hope that it will be useful,            //
// but WITHOUT ANY WARRANTY; without even the implied warranty of             //
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              //
// GNU General Public License for more details.                               //
//                                                                            //
// You should have received a copy of the GNU General Public License along    //
// with this program; if not, write to the Free Software Foundation, Inc.,    //
// 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.              //
////////////////////////////////////////////////////////////////////////////////
//                                                                            //
// The source code below is the result of the initial work of Suexg's author  //
//  -congratulations to him for making Suexg's source code public domain!-    //
// plus modifications by Sudokuki's author, for adaptation to Sudokuki.       //
//                                                                            //
// "Suexg" is a sudoku generator written in C. "Suexg version 12" is included //
// in Sudokuki since version 0.0.12_gtkmm of Sudokuki.                        //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////
// The note below (between /* ... */) is from Suexg's author:                 //
/******************************************************************************/
/*     suexg version 12, small randomized sudoku-generator in C.              */
/*                                                                            */
/*     Generates about 24 sudokus per second with 1GHz CPU.                   */
/*     Based on an exact cover solver, compiled with gcc3.2. Report bugs,     */
/*     improvement suggestions,feedback to sterten@aol.com. For some          */
/*     explanation of the solver see: http://magictour.free.fr/suexco.txt     */
/*     This generator starts from an empty grid and adds clues completely     */
/*     at random. There are faster pseudo-random methods which generate       */
/*     up to 1000 sudokus per second.    [..]                                 */
/*                                                                            */
/*     Send sudokus with rating more than 100000 to sterten@aol.com so they   */
/*     can be included in the list of hardest sudokus at                      */
/*     http://magictour.free.fr/top94    [..]                                 */
/*                                                                            */
/*     This software is public domain.                                        */
/******************************************************************************/
#include <stdlib.h>
#include <stdio.h>

#define MWC ((zr=36969*(zr&65535)+(zr>>16))^(wr=18000*(wr&65535)+(wr>>16)))

unsigned zr = 362436069;
unsigned wr = 521288629;
int A[88];
int C[88];
int I[88];
int P[88];
int V[325];
int W[325];
int Col[730][5];
int Row[325][10];
int Cols[730];
int Rows[325];
int Uc[325];
int Ur[730];
int Two[888];

int a;
int c;
int d;
int f;
int i;
int j;
int k;
int l;
int p;
int r;
int n = 729;
int m = 324;
int s;
int w;
int x;
int y;

int c1;
int c2;
int i1;
int m0;
int m1;
int m2;
int r1;
int s1;

int clues;
int min;
int nodes;
int nt;
int rate;
int sam1;
int samples;
//int seed;
int solutions;
char B[83] = "0111222333111222333111222333444555666"
    "444555666444555666777888999777888999777888999";

int solve();

//------------------------------------------------------------
#ifdef __cplusplus
extern "C" {
#endif
int
grid_generate ( int seed, int requestedRatingMin, int requestedRatingMax, int** grid, int* rating, int** grid_with_clues )
{
//printf("requestedRatingMin: %d\n", requestedRatingMin);
//printf("requestedRatingMax: %d\n", requestedRatingMax);
//int k=0;
//for (k=0; k<1000; k++) {
//  //printf("MWC : %d\n", MWC);
//}

//int ii = 0;
//for(ii = 0; ii<89; ii++) {
//  //printf("B[%d] = %d\n", ii, B[ii]);
//}

    zr ^= seed;
    wr += seed;

    samples = 1; // number of grids to generate (here only one grid is generated)
    rate = 1; // if this value is not zero, the program will calculate the rating (for each grid)

    for ( i=0; i<888; i++ )
    {
        j=1;
        while( j<=i )
        {
            j += j;
        }
        Two[i] = j-1;
    }

    r=0;
    for ( x=1; x<=9; x++ )
    {
        for ( y=1; y<=9; y++ )
        {
            for ( s=1; s<=9; s++ )
            {
                r++;
////printf("r : %d\n", r);
                Cols[r] = 4;
                Col[r][1] = x*9-9+y;
////printf("Col[%d][1] : %d\n", r, Col[r][1]);
                Col[r][2] = (B[x*9-9+y]-48)*9-9+s+81;
////printf("Col[%d][2] : %d\n", r, Col[r][2]);
                Col[r][3] = x*9-9+s+81*2;
////printf("Col[%d][3] : %d\n", r, Col[r][4]);
                Col[r][4] = y*9-9+s+81*3;
////printf("Col[%d][4] : %d\n", r, Col[r][4]);
            }
        }
    }
    for ( c=1; c<=m; c++ )
    {
        Rows[c] = 0;
    }
    for ( r=1; r<=n; r++ )
    {
////printf("r : %d\n", r);
        for ( c=1; c<=Cols[r]; c++ )
        {
            a = Col[r][c];
////printf("a : %d\n", a);
            Rows[a]++;
            Row[a][Rows[a]] = r;
        }
    }

    sam1 = 0;

    m0s:
//printf("m0s\n");
    sam1++;
    if ( sam1 > samples )
    {
        ////printf(".\n");
        return 0;
    }

    m0:
//printf("m0\n");
    for ( i=1; i<=81; i++ )
    {
        A[i] = 0;
    }

    mr1:
//printf("mr1\n");
    i1 = (MWC>>8)&127;
    if ( i1 > 80 )
    {
        goto mr1;
    }
    i1++;
    if ( A[i1] )
    {
        goto mr1;
    }

    mr3:
//printf("mr3\n");
    s = (MWC>>9)&15;
    if ( s>8 )
    {
        goto mr3;
    }
    s++;
    A[i1] = s;
    //printf("\nA[i1:%d] = s:%d\n", i1, s);
    m2 = solve();

    // add a random clue and solve it. No solution ==> remove it again.
    // Not yet a unique solution ==> continue adding clues
    if ( m2<1 )
    {
        A[i1] = 0;
    }
    if ( m2 != 1 )
    {
        goto mr1;
    }

    if ( solve() != 1 )
    {
        goto m0;
    }
    //now we have a unique-solution sudoku. Now remove clues to make it minimal
    {//EXPERIMENTAL: here is the grid with clues in it

        int* p_sol;
        p_sol = *grid_with_clues;

        for ( i=1; i<=81; i++ )
        {
          *p_sol = A[i];
          p_sol++;
        }
    }
    for ( i=1; i<=81; i++ )
    {

        mr4:
//printf("mr4\n");
        x = (MWC>>8)&127;
        if ( x>=i )
        {
            goto mr4;
        }
        x++;
        P[i] = P[x];
        P[x] = i;
    }
    for ( i1=1; i1<=81; i1++ )
    {
        s1 = A[P[i1]];
        A[P[i1]] = 0;
        if ( solve()>1 )
        {
            A[P[i1]] = s1;
        }
    }

    if ( rate )
    {
        nt=0;
        for ( f=0; f<100; f++ )
        {
            solve();
            nt += nodes;
        }
        ////printf ( "new grid, rating:%6i", nt );
		if (nt < requestedRatingMin || requestedRatingMax < nt) {
			goto m0;
		}
		*rating = nt;
    }

    {

        int* p_table;
        p_table = *grid;

        for ( i=1; i<=81; i++ )
        {
          *p_table = A[i];
          p_table++;
        }
    }
    goto m0s;
}
#ifdef __cplusplus
}
#endif

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
int solve()
{//returns 0 (no solution), 1 (unique sol.), 2 (more than one sol.)
//printf("solve()...\n");

    for ( i=0; i<=n; i++ )
    {
        Ur[i] = 0;
    }
    for ( i=0; i<=m; i++ )
    {
        Uc[i] = 0;
    }
    clues = 0;
    for ( i=1; i<=81; i++ )
    {
        if ( A[i] )
        {
		//printf("clues:%d", clues);
            clues++;
            r = i*9-9+A[i];
            for ( j=1; j<=Cols[r]; j++ )
            {
                d=Col[r][j];
                if ( Uc[d] )
                {
                    return 0;
                }
                Uc[d]++;
                for ( k=1; k<=Rows[d]; k++ )
                {
                    Ur[Row[d][k]]++;
                }
            }
        }
    }

    for ( c=1; c<=m; c++ )
    {
        V[c] = 0;
        for ( r=1; r<=Rows[c]; r++ )
        {
            if ( Ur[Row[c][r]] == 0 )
            {
                V[c]++;
            }
        }
    }

    i = clues;
    m0 = 0;
    m1 = 0;
    solutions = 0;
    nodes = 0;
	
    m2:
//printf("M2 ");
    i++;
    I[i] = 0;
    min = n+1;

    if ( i>81 || m0 )
    {
        goto m4;
    }
    if ( m1 )
    {
        C[i] = m1;
        goto m3;
    }

    w = 0;
    for ( c=1; c<=m; c++ )
    {
        if ( !Uc[c] )
        {
            if ( V[c] < 2 )
            {
                C[i] = c;
                goto m3;
            }
            if ( V[c] <= min )
            {
                w++;
                W[w] = c;
            };
            if ( V[c] < min )
            {
                w=1;
                W[w]=c;
                min=V[c];
            }
        }
    }

    mr:
//printf("MR ");
    c2 = MWC&Two[w];
    if ( c2 >= w )
    {
        goto mr;
    }
    C[i] = W[c2+1];
	
    m3:
//printf("M3 ");
    c = C[i];
    I[i]++;
    if ( I[i] > Rows[c] )
    {
        goto m4;
    }
    r = Row[c][I[i]];
    if ( Ur[r] )
    {
        goto m3;
    }
    m0=0;
    m1=0;
    nodes++;//if(nodes>9999 && part==0)return 0;
    for ( j=1; j <= Cols[r]; j++ )
    {
        c1=Col[r][j];
        Uc[c1]++;
    }
    for ( j=1; j<=Cols[r]; j++ )
    {
        c1=Col[r][j];
        for ( k=1; k<=Rows[c1]; k++ )
        {
            r1=Row[c1][k];
            Ur[r1]++;
            if ( Ur[r1] == 1 )
            {
                for ( l=1; l<=Cols[r1]; l++ )
                {
                    c2=Col[r1][l];
                    V[c2]--;
                    if ( Uc[c2]+V[c2] < 1 )
                    {
                        m0=c2;
                    }
                    if ( Uc[c2]==0 && V[c2]<2 )
                    {
                        m1=c2;
                    }
                }
            }
        }
    }
    if ( i == 81 )
    {
        solutions++;
    }
    if ( solutions > 1 )
    {
        goto m9;
    }
    goto m2;
	
    m4:
//printf("M4 ");
    i--;
    c = C[i];
    r = Row[c][I[i]];
    if ( i == clues )
    {
        goto m9;
    }
    for ( j=1; j<=Cols[r]; j++ )
    {
        c1 = Col[r][j];
        Uc[c1]--;
        for ( k=1; k<=Rows[c1]; k++ )
        {
            r1 = Row[c1][k];
            Ur[r1]--;
            if ( Ur[r1] == 0 )
            {
                for ( l=1; l<=Cols[r1]; l++ )
                {
                    c2 = Col[r1][l];
                    V[c2]++;
                }
            }
        }
    }

    if ( i > clues )
    {
        goto m3;
    }
	
    m9:
//printf("\nsolve() => %d\n",solutions);
    return solutions;
}
//-----------------------------------------------------------------------
// EOF
