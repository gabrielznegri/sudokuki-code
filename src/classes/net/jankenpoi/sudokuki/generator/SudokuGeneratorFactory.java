package net.jankenpoi.sudokuki.generator;

import net.jankenpoi.sudokuki.generator.suexg.SuexgGenerator;

public class SudokuGeneratorFactory {

	public static SudokuGenerator getGenerator() {
		return SuexgGenerator.getInstance(); 
	}
	
}
