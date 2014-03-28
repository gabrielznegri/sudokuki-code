package net.jankenpoi.sudokuki.generator;

import net.jankenpoi.sudokuki.generator.suexg.SuexgGenerator;
import net.sourceforge.plantuml.sudoku.SuexgGenerator2;

public class SudokuGeneratorFactory {

	public static SudokuGenerator getGenerator() {
		return SuexgGenerator2.getInstance(); 
	}
	
}
