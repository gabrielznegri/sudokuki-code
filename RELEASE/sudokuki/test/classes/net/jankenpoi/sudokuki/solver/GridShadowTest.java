package net.jankenpoi.sudokuki.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.jankenpoi.sudokuki.model.GridModel;

import org.junit.Test;

public class GridShadowTest {

	public static final String strGrid1ToResolve = ""
		+ "402006000"
		+ "013002070"
		+ "000000000"
		+ "006090020"
		+ "007005000"
		+ "000020504"
		+ "590080000"
		+ "004070009"
		+ "600300002";
	public static final String strGrid1PosValsNB = ""
		+ "030530454" 
		+ "200420403"
		+ "343646775" 
		+ "340405404" 
		+ "540440554" 
		+ "423404050"
		+ "001402544" 
		+ "430401450" 
		+ "022033440";
	public static final String strgGrid1Solution = ""
		+ "482716395"
		+ "913542678"
		+ "765938241"
		+ "856493127"
		+ "247165983"
		+ "139827564"
		+ "591284736"
		+ "324671859"
		+ "678359412";

	GridModel problemGrid1 = new GridModel(strGrid1ToResolve);
	GridModel solutionGrid1 = new GridModel(strgGrid1Solution);

	@Test
	public void testFlagsInitGetNumberOfPossibleValuesAndGetValueAt() {
		GridShadow gs = new GridShadow(problemGrid1.cloneCellInfosAsInts(), 0, true);
		
		for (int li = 0; li < 9; li++) {
			for (int co = 0; co < 9; co++) {
				if (gs.isCellFilled(li, co)) {
					assertEquals(
							Character.digit(
									strGrid1ToResolve.charAt(9 * li + co), 10),
							gs.getValueAt(li, co));
				} else {
					assertEquals(
							Character.digit(
									strGrid1PosValsNB.charAt(9 * li + co), 10),
							gs.getNumberOfPossibleValues(li, co));
				}
			}
		}
	}

	@Test
	public void testFlagsInitAndIsCellFilled() {
		assertEquals(81, problemGrid1.cloneCellInfosAsInts().length);
		GridShadow gs = new GridShadow(problemGrid1.cloneCellInfosAsInts(), 0, true);
		System.out
				.println("GridShadowTest.testFlagsInitializationAndIsCellFilled()");

		for (int li = 0; li < 9; li++) {
			for (int co = 0; co < 9; co++) {
				int digit = Character.digit(
						strGrid1PosValsNB.charAt(9 * li + co), 10);
				System.out.print(digit + " ");
				if (digit == 0) {
					assertTrue("(li:" + li + ", co:" + co
							+ ") should contain a value",
							gs.isCellFilled(li, co));
				} else {
					assertFalse("(li:" + li + ", co:" + co
							+ ") should NOT contain a value",
							gs.isCellFilled(li, co));
				}
			}
			System.out.println();
		}
	}

	@Test
	public void testFlagsInitAndPopFirstCellWithMinPossValues() {
		GridShadow gs = new GridShadow(problemGrid1.cloneCellInfosAsInts(), 0, true);
		System.out
				.println("GridShadowTest.testFlagsInitializationAndIsCellFilled()");
		int[] cell = gs.popFirstCellWithMinPossValues();
		assertEquals(6, cell[0]);
		assertEquals(2, cell[1]);
	}

	@Test
	public void testNumberOfPossibleValuesJustAfterGridInit() {
		GridShadow gs = new GridShadow(problemGrid1.cloneCellInfosAsInts(), 0, true);
		int nbPossVals = gs.getNumberOfPossibleValues(0, 0);
		assertEquals(0, nbPossVals);
		nbPossVals = gs.getNumberOfPossibleValues(0, 1);
		assertEquals(3, nbPossVals);
	}

	@Test
	public void testNumberOfPossibleValuesAfterFillingACell() {
		System.out
				.println("\nGridShadowTest.testNumberOfPossibleValuesAfterFillingACell()");
		GridShadow gs = new GridShadow(problemGrid1.cloneCellInfosAsInts(), 0, true);
		gs.debugDump();
		// fill a cell
		gs.setCellValueAt(0, 1, (byte) 8);
		gs.debugDump();
		final String strGridPosValsNB = "" 
			+ "000430343" 
			+ "100420403"
			+ "232646775" 
			+ "330405404" 
			+ "530440554" 
			+ "413404050"
			+ "001402544" 
			+ "420401450" 
			+ "012033440";
		// check nb of poss values correctly updated (square, line, column)
		checkPossibleValuesNB(strGridPosValsNB, gs);
	}

	private void checkPossibleValuesNB(String refStrGridPossVals, GridShadow gs) {
		for (int li = 0; li < 9; li++) {
			for (int co = 0; co < 9; co++) {
				System.out.println("li:" + li + ",co:" + co + " ");
				if (!gs.isCellFilled(li, co)) {
					assertEquals(
							Character.digit(
									refStrGridPossVals.charAt(9 * li + co), 10),
							gs.getNumberOfPossibleValues(li, co));
				}
			}
		}
		
	}
	
	@Test
	public void testNumberOfPossibleValuesAfterFillingElevenCells() {
		GridShadow gs = new GridShadow(problemGrid1.cloneCellInfosAsInts(), 0, true);
		gs.setCellValueAt(0, 1, (byte) 8);
		gs.setCellValueAt(2, 2, (byte) 5);
		gs.setCellValueAt(1, 4, (byte) 4);
		gs.setCellValueAt(5, 2, (byte) 9);
		gs.setCellValueAt(6, 2, (byte) 1);
		gs.setCellValueAt(8, 1, (byte) 7);
		gs.setCellValueAt(8, 2, (byte) 8);
		gs.setCellValueAt(8, 4, (byte) 5);
		gs.setCellValueAt(8, 5, (byte) 9);
		gs.setCellValueAt(7, 5, (byte) 1);
		gs.setCellValueAt(6, 5, (byte) 4);

		
		final String strGridPosValsNB = "" 
			+ "000420343" 
			+ "100300303"
			+ "210423764" 
			+ "330403404" 
			+ "430430554" 
			+ "310403040"
			+ "000200323" 
			+ "220200340" 
			+ "000000220";
		checkPossibleValuesNB(strGridPosValsNB, gs);
	}

	@Test
	public void testFlagsInitAndPopFirstCellWithMinPossValues10Cells() {
		GridShadow gs = new GridShadow(problemGrid1.cloneCellInfosAsInts(), 0, true);
		System.out
				.println("GridShadowTest.testFlagsInitializationAndIsCellFilled()");
		int[] cell = gs.popFirstCellWithMinPossValues(); // 6,2:1
		assertEquals(6, cell[0]);
		assertEquals(2, cell[1]);
		gs.setCellValueAt(cell[0], cell[1], (byte) 1);
		
		cell = gs.popFirstCellWithMinPossValues(); // 6,5:4
		assertEquals(6, cell[0]);
		assertEquals(5, cell[1]);
		gs.setCellValueAt(cell[0], cell[1], (byte) 4);
		
		cell = gs.popFirstCellWithMinPossValues(); // 7,5:1
		assertEquals(7, cell[0]);
		assertEquals(5, cell[1]);
		gs.setCellValueAt(cell[0], cell[1], (byte) 1);
		
		cell = gs.popFirstCellWithMinPossValues(); // 8,2:8
		assertEquals(8, cell[0]);
		assertEquals(2, cell[1]);
		gs.setCellValueAt(cell[0], cell[1], (byte) 8);
		
		cell = gs.popFirstCellWithMinPossValues(); // 5,2:9
		assertEquals(5, cell[0]);
		assertEquals(2, cell[1]);
		gs.setCellValueAt(cell[0], cell[1], (byte) 9);
		
		cell = gs.popFirstCellWithMinPossValues(); // 2,2:5
		assertEquals(2, cell[0]);
		assertEquals(2, cell[1]);
		gs.setCellValueAt(cell[0], cell[1], (byte) 5);
		
		cell = gs.popFirstCellWithMinPossValues(); // 8,1:7
		assertEquals(8, cell[0]);
		assertEquals(1, cell[1]);
		gs.setCellValueAt(cell[0], cell[1], (byte) 7);
		
		cell = gs.popFirstCellWithMinPossValues(); // 0,1:8
		assertEquals(0, cell[0]);
		assertEquals(1, cell[1]);
		gs.setCellValueAt(cell[0], cell[1], (byte) 8);
				
		cell = gs.popFirstCellWithMinPossValues(); // 1,0:9
		assertEquals(1, cell[0]);
		assertEquals(0, cell[1]);
		gs.setCellValueAt(cell[0], cell[1], (byte) 9);
		
		cell = gs.popFirstCellWithMinPossValues(); // 2,0:7
		assertEquals(2, cell[0]);
		assertEquals(0, cell[1]);
	}

	@Test
	public void testPopValueForCellBasic() {
		GridShadow gs = new GridShadow(problemGrid1.cloneCellInfosAsInts(), 0, true);
		gs.debugDump();
		byte value = gs.popFirstValueForCell(1, 0);
		assertEquals(8, value);
		value = gs.popFirstValueForCell(2, 0);
		assertEquals(7, value);
		value = gs.popFirstValueForCell(3, 0);
		assertEquals(1, value);
		value = gs.popFirstValueForCell(4, 0);
		assertEquals(1, value);
		value = gs.popFirstValueForCell(5, 0);
		assertEquals(1, value);
		value = gs.popFirstValueForCell(7, 0);
		assertEquals(1, value);
		value = gs.popFirstValueForCell(1, 0);
		assertEquals(8, value);
		value = gs.popFirstValueForCell(1, 3);
		assertEquals(4, value);
		value = gs.popFirstValueForCell(1, 4);
		assertEquals(4, value);
		value = gs.popFirstValueForCell(1, 6);
		assertEquals(4, value);
		value = gs.popFirstValueForCell(1, 8);
		assertEquals(5, value);
	}
	
	@Test
	public void testPopValueForCellCombinedWithSetCellValue() {
		GridShadow gs = new GridShadow(problemGrid1.cloneCellInfosAsInts(), 0, true);
		byte value = gs.popFirstValueForCell(2, 0);
		assertEquals(7, value);
		value = gs.popFirstValueForCell(3, 0);
		assertEquals(1, value);
		
		int li = 1;
		int co = 0;
		value = gs.popFirstValueForCell(li, co);
		assertEquals(8, value);
		gs.setCellValueAt(li, co, value);

		li = 2;
		co = 0;
		value = gs.popFirstValueForCell(li, co);
		assertEquals(7, value);
		gs.setCellValueAt(li, co, value);
		
		value = gs.popFirstValueForCell(4, 0);
		assertEquals(1, value);
		
		li = 3;
		co = 0;
		value = gs.popFirstValueForCell(li, co);
		assertEquals(1, value);
		gs.setCellValueAt(li, co, value);
		
		value = gs.popFirstValueForCell(4, 0);
		assertEquals(2, value);
	}

}
