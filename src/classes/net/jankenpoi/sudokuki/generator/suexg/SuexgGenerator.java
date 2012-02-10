package net.jankenpoi.sudokuki.generator.suexg;

import net.jankenpoi.sudokuki.generator.SudokuGenerator;

public abstract class SuexgGenerator extends SudokuGenerator {

	private static final SudokuGenerator INSTANCE;
	static {
		if (SuexgProxy.getGenerator() != null) {
			INSTANCE = SuexgProxy.getGenerator();
		} else {
			INSTANCE = SuexgJava.getGenerator();
		}
	}
	
	public final static SudokuGenerator getInstance() {
		return INSTANCE;
	}
	
}
