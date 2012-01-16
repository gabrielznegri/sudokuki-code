/*
 * Sudokuki - essential sudoku game
 * Copyright (C) 2007-2012 Sylvain Vedrenne
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
		String javaLibraryPath = System.getProperty("java.library.path");
		System.out.println("SuexgProxy.enclosing_method() java.library.path:"
				+ javaLibraryPath);
		boolean exceptionCaught = false;
		try {
			System.loadLibrary("suexg_proxy");
		} catch (Throwable t) {
			System.out.println("SuexgProxy() unable to load library 'suexg_proxy', reason: "+t);
			exceptionCaught = true;
		} finally {
			if (exceptionCaught) {
				INSTANCE = null;
			} else {
				INSTANCE = new SuexgProxy();
			}
		}
		System.out.println("SuexgProxy.enclosing_method() after call to loadLibrary()");
	}
	
	public static SudokuGenerator getGenerator() {
		System.out.println("SuexgProxy.getGenerator() INSTANCE : "+INSTANCE);
		return INSTANCE;
	}

	private SuexgProxy() {
	}
	
	@Override
	public SudokuGrid generateGrid(int minRating, int maxRating) {
		Random rand = new Random(System.currentTimeMillis());

		int[] grid = new int[81];
		int[] gridAndClues = new int[81];
		int[] rating = new int[] { -1 };
		System.out.println("\nSuexgProxy.main() APRES");
		int seed = rand.nextInt();
		int status = INSTANCE.generateSuexgGrid(seed, minRating, maxRating, grid, rating,
				gridAndClues);
		System.out.println("rating:" + rating[0] + "\nstatus:" + status);
		System.out.println("\n*** generated grid ***");
		printGrid(grid);
		System.out.println("\n*** generated grid with clues ***");
		printGrid(gridAndClues);
		
		SudokuGrid sudoku = new SudokuGrid(grid);
		return sudoku;
	}
	
	native int generateSuexgGrid(int inSeed, int minRating, int maxRating, int[] outGrid, int[] outRating,
			int[] outGridWithClues);

	native int solveCustomGrid(int[] inGrid, int[] outGrid);

}
