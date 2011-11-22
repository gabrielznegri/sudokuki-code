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
package net.jankenpoi.sudokuki.ui.swing;

import java.awt.Frame;

import javax.swing.JPanel;

import net.jankenpoi.sudokuki.ui.MemosSelector;

public class SwingMemosSelector implements MemosSelector {

	private byte[] memos;

	SwingMemosSelector(Frame frame, JPanel panel, int invokedX, int invokedY, Byte[] previousMemos) {
		SelectMemosDialog dlg = new SelectMemosDialog(frame, previousMemos);
		int width = dlg.getWidth();
		int height = dlg.getHeight();
		
		int leftLimit = (int)panel.getLocationOnScreen().getX();
		int rightLimit = leftLimit + panel.getWidth() - width;
		int upperLimit = (int)panel.getLocationOnScreen().getY();
		int lowerLimit = upperLimit + panel.getHeight() - height;
		
		int x = leftLimit + invokedX - width/2;
		x = Math.max(leftLimit, x);
		x = Math.min(rightLimit, x);
		
		int y = upperLimit + invokedY - height/2;
		y = Math.max(upperLimit, y);
		y = Math.min(lowerLimit, y);
		
		System.out.println("SwingSelector.SwingSelector() setLocation("+x+","+y+")");
		dlg.setLocation(x, y);
		
		dlg.setVisible(true);
		
		memos = dlg.getSelectedMemos();
		System.out.println("SwingSelector.SwingSelector() selected memos:" + memos);
	}

	@Override
	public byte[] retrieveMemos() {
		System.out.println("SwingSelector.retrieveMemos() memos:" + memos);
		return memos;
	}

}
