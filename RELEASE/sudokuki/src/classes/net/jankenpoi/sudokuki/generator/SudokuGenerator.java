package net.jankenpoi.sudokuki.generator;

import net.jankenpoi.sudokuki.SudokuGrid;

public abstract class SudokuGenerator {

	protected static void printGrid(int[] tab) {
		for (int i = 0; i < tab.length; i++) {
			if (i % 3 == 0)
				System.out.print(" ");
			if (i % 9 == 0)
				System.out.println();
			if (i % 27 == 0)
				System.out.println();
			System.out.print("" + (tab[i] == 0 ? "-" : tab[i]));
		}
		System.out.println();
	}
	
	public abstract SudokuGrid generateGrid(final int minRating, final int maxRating);

	public abstract SudokuGrid generateGrid();
	
}
