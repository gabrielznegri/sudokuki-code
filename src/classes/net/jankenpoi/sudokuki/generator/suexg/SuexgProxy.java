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

class SuexgProxy extends SuexgGenerator {

	private static final SuexgProxy INSTANCE;
	static {
		boolean exceptionCaught = false;
		try {
			System.loadLibrary("suexg_proxy");
		} catch (Throwable t) {
			exceptionCaught = true;
		} finally {
			if (exceptionCaught) {
				INSTANCE = null;
			} else {
				INSTANCE = new SuexgProxy();
			}
		}
	}
	
	public static SudokuGenerator getGenerator() {
		return INSTANCE;
	}

	private SuexgProxy() {
	}
	
	@Override
	public synchronized SudokuGrid generateGrid(int minRating, int maxRating) {
		Random rand = new Random(System.currentTimeMillis());

		int[] grid = new int[81];
		int[] gridAndClues = new int[81];
		int[] rating = new int[] { -1 };
		int seed = rand.nextInt();
		INSTANCE.generateSuexgGrid(seed, minRating, maxRating, grid, rating,
				gridAndClues);
		//printGrid(grid);
		//printGrid(gridAndClues);
		
		SudokuGrid sudoku = new SudokuGrid(grid);
		return sudoku;
	}
	
	private native int generateSuexgGrid(int inSeed, int minRating, int maxRating, int[] outGrid, int[] outRating,
			int[] outGridWithClues);

	private native int solveCustomGrid(int[] inGrid, int[] outGrid);

}
