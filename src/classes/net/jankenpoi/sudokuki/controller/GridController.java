/*
 * Sudokuki - essential sudoku game
 * Copyright (C) 2007-2011 Sylvain Vedrenne
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
package net.jankenpoi.sudokuki.controller;

import java.util.ArrayList;
import java.util.List;

import net.jankenpoi.sudokuki.model.GridChangedEvent;
import net.jankenpoi.sudokuki.model.GridModel;
import net.jankenpoi.sudokuki.model.GridModel.GridValidity;
import net.jankenpoi.sudokuki.view.GridView;

public class GridController {

	private GridModel model;

	List<GridView> views = new ArrayList<GridView>();

	public GridController(GridModel model) {
		this.model = model;
	}

	public void displayViews() {
		for (GridView view : views) {
			view.display();
		}
	}

	public void closeViews() {
		for (GridView view : views) {
			view.close();
		}
	}

	public void notifyGridChanged() {
		model.fireGridChanged(new GridChangedEvent(model, 0, 0, (short)0));
	}
	
	public void notifyGridValueChanged(int li, int co, int value) {
//		System.out.println("GridController.notifyGridValueChanged("+li+", "+co+", "+value+")");
		model.setCellValue(li, co, value);
		
		////////////////////////////// EXPERIMENTAL......
		lastLI = li;
		lastCO = co;
	}

	public void notifyGridMemosChanged(int li, int co, byte[] memos) {
//		System.out.println("GridController.notifyGridMemosChanged("+li+", "+co+", "+memos+")");
		model.clearCellMemos(li, co);
		model.setCellMemos(li, co, memos);
		
		////////////////////////////// EXPERIMENTAL......
		lastLI = li;
		lastCO = co;
	}

	public void addView(GridView view) {
		views.add(view);
		view.setController(this);
		view.gridChanged(new GridChangedEvent(model, 0, 0, (short)0));
		model.addGridListener(view);
		view.display();
	}

	public void notifySetAllMemosRequested() {
		model.setMemosForAllCells();
	}

	public void notifyClearAllMovesRequested() {
		model.clearAllUserMoves();
	}
	
	public void notifyClearAllMemosRequested() {
		model.clearAllUserMemos();
	}
	
	public void notifyEnterCustomGridMode() {
//		System.out.println("GridController.notifyEnterCustomGridMode()");
		model.enterCustomGridMode();
	}
	
	public void notifyExitCustomGridModeRequested() {
		GridValidity validity = model.getGridValidity();
		if (validity.isGridValid()) {
			model.exitCustomGridMode();
		}
		model.fireGridChanged(new GridChangedEvent(model, 0, 0, (short)0));
	}
	
	public void notifyNewGridRequested() {
		model.requestNewGrid();
		model.fireGridChanged(new GridChangedEvent(model, 0, 0, (short)0));
	}

	private int lastLI = 0; // EXPERIMENTAL, wait until it is possible to play with the keyboard...
	private int lastCO = 0; // EXPERIMENTAL, wait until it is possible to play with the keyboard...
	public void notifySetMemosHere() {
//		System.out.println("GridController.notifySetMemosHere() LAST CLICKED CELL : "+lastLI+","+lastCO);
		model.setMemosForThisCell(lastLI, lastCO);
	}

	public void notifyResetGridFromShorts(short[] externalCellInfos) {
		model.resetGridModelFromShorts(externalCellInfos);
		model.fireGridChanged(new GridChangedEvent(model, 0, 0, (short)0));
	}

	public int[] getCellInfosFromModel() {
		return model.asIntArray();
	}

	public void notifyGridComplete() {
		model.setGridComplete();
	}
}
