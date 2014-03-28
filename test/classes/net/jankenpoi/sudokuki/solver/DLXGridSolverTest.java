package net.jankenpoi.sudokuki.solver;

import static org.junit.Assert.*;
import net.jankenpoi.sudokuki.model.GridModel;

import org.junit.Test;


public class DLXGridSolverTest {

	GridModel problemGrid1 = new GridModel(GridSolverTests.strProblemGrid1);
	GridModel solutionGrid1 = new GridModel(GridSolverTests.strSolutionGrid1);
	GridModel problemGridDifficult = new GridModel(GridSolverTests.strProblemGridDifficult);
	GridModel solutionGridDifficult = new GridModel(GridSolverTests.strSolutionDifficult);
	GridModel problemGridDiabolic = new GridModel(GridSolverTests.strProblemGridDiabolic);
	GridModel solutionGridDiabolic = new GridModel(GridSolverTests.strSolutionGridDiabolic);
	
	@Test
	public void testCopyCurrentFlagsToNextPosition() {
		BruteForceGridSolver solver = new BruteForceGridSolver(problemGrid1);
		solver.copyCurrentFlagsToNextPosition();
		solver.forwardToNextPosition();
		
		int currentIndex = solver.getCurrentIndex();
		int[] cellShadowMemory = solver.getCellShadowMemory();
		assertTrue(currentIndex - GridSolver.GRID_LENGTH >= 0);
		for (int i=currentIndex - GridSolver.GRID_LENGTH; i<currentIndex; i++) {
			assertEquals(cellShadowMemory[i], cellShadowMemory[i+GridSolver.GRID_LENGTH]);
		}
	}
	
	@Test
	public void testResolveGrid1() {
		GridSolver solver = new BruteForceGridSolver(problemGrid1);
		GridSolution solution = solver.resolve();
		assertTrue(solution.isSolved());
		
		GridModel solGrid = solution.getSolutionGrid();
		for (int li=0; li<9; li++) {
			for (int co=0; co<9; co++) {
				System.out.print(solGrid.getValueAt(li, co));
			}
		}
		System.out.println();
		
		assertTrue(areAllValuesEqual(solution.getSolutionGrid(), solutionGrid1));
	}

	@Test
	public void testResolveDifficultGrid() {
		GridSolver solver = new BruteForceGridSolver(problemGridDifficult);
		GridSolution solution = solver.resolve();
		assertTrue(solution.isSolved());
		
		GridModel solGrid = solution.getSolutionGrid();
		for (int li=0; li<9; li++) {
			for (int co=0; co<9; co++) {
				System.out.print(solGrid.getValueAt(li, co));
			}
		}
		System.out.println();
		
		assertTrue(areAllValuesEqual(solution.getSolutionGrid(), solutionGridDifficult));
	}
	
	@Test
	public void testResolveDiabolicGrid() {
		GridSolver solver = new BruteForceGridSolver(problemGridDiabolic);
		System.out.println();
		System.out
				.println("BruteForceGridSolverTest.testResolveDifficultGrid() *** start test ***");
		System.out.println();
		GridSolution solution = solver.resolve();
		assertTrue(solution.isSolved());
		
		GridModel solGrid = solution.getSolutionGrid();
		for (int li=0; li<9; li++) {
			for (int co=0; co<9; co++) {
				System.out.print(solGrid.getValueAt(li, co));
			}
		}
		System.out.println();
		
		assertTrue(areAllValuesEqual(solution.getSolutionGrid(), solutionGridDiabolic));
	}
	
	@Test
	public void testResolve10RandomGrids() {
		for (int i=0; i<10; i++) {
			GridSolver solver = new BruteForceGridSolver(new GridModel());
			GridSolution solution = solver.resolve();
			assertTrue(solution.isSolved());
		}
	}
	
	private boolean areAllValuesEqual(GridModel solutionGrid,
			GridModel otherGrid) {
		int[] solCells = solutionGrid.cloneCellInfosAsInts();
		int[] otherCells = otherGrid.cloneCellInfosAsInts();
		boolean allEqual = true;
		for (int li=0; li<9; li++) {
			for (int co=0; co<9; co++) {
				allEqual &= (solCells[9*li+co] == otherCells[9*li+co]);
			}
		}
		return allEqual;
	}
	
}
