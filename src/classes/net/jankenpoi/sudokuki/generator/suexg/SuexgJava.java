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
package net.jankenpoi.sudokuki.generator.suexg;

import java.util.Random;

import net.jankenpoi.sudokuki.SudokuGrid;
import net.jankenpoi.sudokuki.generator.SudokuGenerator;

/*
 *****************************************************************************
 * This file results from Suexg's author initial work in the public domain   *
 * and from Sudokuki's author.                                               *
 *                                                                           *
 * This is a GPLv3+ Java transposition of Suexg's original C source code.    *
 * N.B: Instructions like 'goto label' are valid in C but not in Java,       *
 * so I have transposed them using 'continue' inside a global while loop     *
 * with boolean values and 'if' instructions to simulate the labels.         *
 * And I replaced the B[] char table using the actual char values as int     *
 *****************************************************************************
 *  "Suexg version 12" is included in Sudokuki since version 0.0.12_gtkmm    *
 *  of Sudokuki. The two reasons why I chose Suexg in the first place are    *
 *  that it works and that it was public domain, so GPL compatible.          *
 *  **************************************************************************
 *  The note below (between * ... *) is from Suexg's author:                 *
 *  **************************************************************************
 *  * suexg version 12, small randomized sudoku-generator in C.            * *
 *  *                                                                      * *
 *  * Generates about 24 sudokus per second with 1GHz CPU.                 * *
 *  * Based on an exact cover solver, compiled with gcc3.2. Report bugs,   * *
 *  * improvement suggestions,feedback to sterten@aol.com. For some        * *
 *  * explanation of the solver see: http://magictour.free.fr/suexco.txt   * *
 *  * This generator starts from an empty grid and adds clues completely   * *
 *  * at random. There are faster pseudo-random methods which generate     * *
 *  * up to 1000 sudokus per second.    [..]                               * *
 *  *                                                                      * *
 *  * Send sudokus with rating more than 100000 to sterten@aol.com so they * *
 *  * can be included in the list of hardest sudokus at                    * *
 *  * http://magictour.free.fr/top94    [..]                               * *
 *  *                                                                      * *
 *****************************************************************************
 */
class SuexgJava extends SuexgGenerator {

	private static long zr = 362436069;
	private static long wr = 521288629;
	private final static int[] A = new int[88];
	private final static int[] C = new int[88];
	private final static int[] I = new int[88];
	private final static int[] P = new int[88];
	private final static int[] V = new int[325];
	private final static int[] W = new int[325];
	private final static int[][] Col = new int[730][5];
	private final static int[][] Row = new int[325][10];
	private final static int[] Cols = new int[730];
	private final static int[] Rows = new int[325];
	private final static int[] Uc = new int[325];
	private final static int[] Ur = new int[730];
	private final static int[] Two = new int[888];

	private static int a;
	private static int c;
	private static int d;
	private static int f;
	private static int i;
	private static int j;
	private static int k;
	private static int l;
	private static int r;
	private static int n = 729;
	private static int m = 324;
	private static int s;
	private static int w;
	private static int x;
	private static int y;

	private static int c1;
	private static int c2;
	private static int i1;
	private static int m0;
	private static int m1;
	private static int m2;
	private static int r1;
	private static int s1;

	private static int clues;
	private static int min;
	private static int nodes;
	private static int nt;
	private static int rate;
	private static int sam1;
	private static int samples;
	// private static int seed;
	private static int solutions;
	private final static int[] B = new int[] { 48, 49, 49, 49, 50, 50, 50, 51,
			51, 51, 49, 49, 49, 50, 50, 50, 51, 51, 51, 49, 49, 49, 50, 50, 50,
			51, 51, 51, 52, 52, 52, 53, 53, 53, 54, 54, 54, 52, 52, 52, 53, 53,
			53, 54, 54, 54, 52, 52, 52, 53, 53, 53, 54, 54, 54, 55, 55, 55, 56,
			56, 56, 57, 57, 57, 55, 55, 55, 56, 56, 56, 57, 57, 57, 55, 55, 55,
			56, 56, 56, 57, 57, 57 };

	private static int MWC() {
		final int result;
		zr = 36969 * (zr & 65535) + (zr >> 16);
		wr = 18000 * (wr & 65535) + (wr >> 16);
		result = (int) (zr ^ wr);
		return result;
	}

	@Override
	public synchronized SudokuGrid generateGrid(final int requestedRatingMin, final int requestedRatingMax) {
		Random rand = new Random(System.currentTimeMillis());

		int[] grid = new int[81];
		int[] gridAndClues = new int[81];
		int[] rating = new int[] { -1 };
		int seed = rand.nextInt();
		gridGenerate(seed, requestedRatingMin, requestedRatingMax, grid, rating, gridAndClues);
		//printGrid(grid);
		//printGrid(gridAndClues);

		SudokuGrid sudoku = new SudokuGrid(grid);
		return sudoku;
	}


	private int
	gridGenerate(final int seed, final int requestedRatingMin, final int requestedRatingMax, final int[] grid, final int[] rating,
			final int[] grid_with_clues) {

		/*
		 * boolean values used to simulate the infamous goto used in the
		 * original C suexg algorithm
		 */
		boolean gotoM0S = true;
		boolean gotoMR1 = false, gotoMR3 = false, gotoM0 = false;

		zr ^= seed;
		wr += seed;

		samples = 1; // number of grids to generate (here only one grid is
						// generated)
		rate = 1; // if this value is not zero, the program will calculate the
					// rating (for each grid)

		for (i = 0; i < 888; i++) {
			j = 1;
			while (j <= i) {
				j += j;
			}
			Two[i] = j - 1;
		}

		r = 0;
		for (x = 1; x <= 9; x++) {
			for (y = 1; y <= 9; y++) {
				for (s = 1; s <= 9; s++) {
					r++;
					Cols[r] = 4;
					Col[r][1] = x * 9 - 9 + y;
					Col[r][2] = (B[x * 9 - 9 + y] - 48) * 9 - 9 + s + 81;
					Col[r][3] = x * 9 - 9 + s + 81 * 2;
					Col[r][4] = y * 9 - 9 + s + 81 * 3;
				}
			}
		}
		for (c = 1; c <= m; c++) {
			Rows[c] = 0;
		}
		for (r = 1; r <= n; r++) {
			for (c = 1; c <= Cols[r]; c++) {
				a = Col[r][c];
				Rows[a]++;
				Row[a][Rows[a]] = r;
			}
		}

		sam1 = 0;

		while (true) {
			// m0s:
			if (gotoM0S) {
				gotoM0S = false;
				sam1++;
				if (sam1 > samples) {
					return 0;
				} else {
					gotoM0 = true;
				}
			} // end of MOS if

			// m0:
			if (gotoM0) {
				gotoM0 = false;
				for (i = 1; i <= 81; i++) {
					A[i] = 0;
				}
				gotoMR1 = true;
			} // end of M0 if

			// mr1:
			if (gotoMR1) {
				gotoMR1 = false;
				i1 = (MWC() >> 8) & 127;
				if (i1 > 80) {
					gotoMR1 = true;
					continue; // these 2 instructions stand for: goto mr1;
				}
				i1++;
				if (A[i1] != 0) {
					gotoMR1 = true;
					continue; // these 2 instructions stand for: goto mr1;
				}
				gotoMR3 = true;
			}

			// mr3:
			if (gotoMR3) {
				gotoMR3 = false;
				s = (MWC() >> 9) & 15;
				if (s > 8) {
					gotoMR3 = true;
					continue; // these 2 instructions stand for: goto mr3;
				}
				s++;
				A[i1] = s;
				m2 = solve();

				// add a random clue and solve it. No solution ==> remove it
				// again.
				// Not yet a unique solution ==> continue adding clues
				if (m2 < 1) {
					A[i1] = 0;
				}
				if (m2 != 1) {
					gotoMR1 = true;
					continue; // these 2 instructions stand for: goto mr1;
				}

				if (solve() != 1) {
					gotoM0 = true;
					continue; // these 2 instructions stand for: goto m0;
				}
				// now we have a unique-solution sudoku. Now remove clues to
				// make it minimal
				{// EXPERIMENTAL: here is the grid with clues in it
					for (i = 1; i <= 81; i++) {
						grid_with_clues[i - 1] = A[i];
					}
				}
				for (i = 1; i <= 81; i++) {

					x = i;
					while (x >= i) {
						// mr4:
						x = (MWC() >> 8) & 127;
					}
					x++;
					P[i] = P[x];
					P[x] = i;
				}
				for (i1 = 1; i1 <= 81; i1++) {
					s1 = A[P[i1]];
					A[P[i1]] = 0;
					if (solve() > 1) {
						A[P[i1]] = s1;
					}
				}

				if (rate != 0) {
					nt = 0;
					for (f = 0; f < 100; f++) {
						solve();
						nt += nodes;
					}
					
					if (nt < requestedRatingMin|| requestedRatingMax < nt) {
						gotoM0 = true;
						continue;
					}
					rating[0] = nt;
				}

				{

					for (i = 1; i <= 81; i++) {
						grid[i - 1] = A[i];
					}
				}
				gotoM0S = true;
				continue; // these 2 instructions stand for: goto m0s;
			} // end of gotoMR3 if

		} // outer while loop
	}

	// -----------------------------------------------------------------------
	// -----------------------------------------------------------------------

	private static int solve() {// returns 0 (no solution), 1 (unique sol.), 2
								// (more
		// than one sol.)

		/*
		 * boolean values used to simulate the infamous goto used in the
		 * original C suexg algorithm
		 */
		boolean gotoM2 = true, gotoM3 = false, gotoM4 = false, gotoMR = false;

		for (i = 0; i <= n; i++) {
			Ur[i] = 0;
		}
		for (i = 0; i <= m; i++) {
			Uc[i] = 0;
		}
		clues = 0;
		for (i = 1; i <= 81; i++) {
			if (A[i] != 0) {
				clues++;
				r = i * 9 - 9 + A[i];
				for (j = 1; j <= Cols[r]; j++) {
					d = Col[r][j];
					if (Uc[d] != 0) {
						return 0;
					}
					Uc[d]++;
					for (k = 1; k <= Rows[d]; k++) {
						Ur[Row[d][k]]++;
					}
				}
			}
		}

		for (c = 1; c <= m; c++) {
			V[c] = 0;
			for (r = 1; r <= Rows[c]; r++) {
				if (Ur[Row[c][r]] == 0) {
					V[c]++;
				}
			}
		}

		i = clues;
		m0 = 0;
		m1 = 0;
		solutions = 0;
		nodes = 0;

		whileloop: while (true) {

			// m2: //////////
			if (gotoM2) {
				gotoM2 = false;

				i++;
				I[i] = 0;
				min = n + 1;

				if (i > 81 || m0 != 0) {
					gotoM4 = true;
					continue; // simulates: goto m4;
				}
				if (m1 != 0) {
					C[i] = m1;
					gotoM3 = true;
					continue; // simulates: goto m3;
				}

				w = 0;
				for (c = 1; c <= m; c++) {
					if (Uc[c] == 0) {
						if (V[c] < 2) {
							C[i] = c;
							gotoM3 = true;
							continue whileloop; // simulates: goto m3;
						}
						if (V[c] <= min) {
							w++;
							W[w] = c;
						}
						
						if (V[c] < min) {
							w = 1;
							W[w] = c;
							min = V[c];
						}
					}
				}
				gotoMR = true;
			}

			// /mr:
			if (gotoMR) {
				gotoMR = false;

				c2 = (MWC() & Two[w]);
				if (c2 >= w) {
					gotoMR = true;
					continue; // simulates: goto mr;
				}
				C[i] = W[c2 + 1];
				gotoM3 = true;
			}

			// m3: //////
			if (gotoM3) {
				gotoM3 = false;

				c = C[i];
				I[i]++;
				if (I[i] > Rows[c]) {
					gotoM4 = true;
					continue; // simulates: goto m4;
				}
				r = Row[c][I[i]];
				if (Ur[r] != 0) {
					gotoM3 = true;
					continue; // simulates: goto m3;
				}
				m0 = 0;
				m1 = 0;
				nodes++;// if(nodes>9999 && part==0)return 0;
				for (j = 1; j <= Cols[r]; j++) {
					c1 = Col[r][j];
					Uc[c1]++;
				}
				for (j = 1; j <= Cols[r]; j++) {
					c1 = Col[r][j];
					for (k = 1; k <= Rows[c1]; k++) {
						r1 = Row[c1][k];
						Ur[r1]++;
						if (Ur[r1] == 1) {
							for (l = 1; l <= Cols[r1]; l++) {
								c2 = Col[r1][l];
								V[c2]--;
								if (Uc[c2] + V[c2] < 1) {
									m0 = c2;
								}
								if (Uc[c2] == 0 && V[c2] < 2) {
									m1 = c2;
								}
							}
						}
					}
				}
				if (i == 81) {
					solutions++;
				}
				if (solutions > 1) {
					break; // simulates: goto m9;
				}
				gotoM2 = true;
				continue; // simulates: goto m2;
			} // end of m3 if

			// m4: ////
			if (gotoM4) {
				gotoM4 = false;

				i--;
				c = C[i];
				r = Row[c][I[i]];
				if (i == clues) {
					break; // simulates: goto m9;
				}
				for (j = 1; j <= Cols[r]; j++) {
					c1 = Col[r][j];
					Uc[c1]--;
					for (k = 1; k <= Rows[c1]; k++) {
						r1 = Row[c1][k];
						Ur[r1]--;
						if (Ur[r1] == 0) {
							for (l = 1; l <= Cols[r1]; l++) {
								c2 = Col[r1][l];
								V[c2]++;
							}
						}
					}
				}

				if (i > clues) {
					gotoM3 = true;
					continue; // simulates: goto m3;
				}
			} // end of m4 if

			break;
		} // outer while loop

		// m9: /////
		return solutions;
	}

	private static final SuexgJava INSTANCE;
	static {
		INSTANCE = new SuexgJava();
	}
	
	public static SudokuGenerator getGenerator() {
		return INSTANCE;
	}

}