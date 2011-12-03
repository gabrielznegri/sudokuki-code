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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.jankenpoi.sudokuki.model.GridModel.GridValidity;
import net.jankenpoi.sudokuki.model.Position;
import net.jankenpoi.sudokuki.preferences.UserPreferences;
import net.jankenpoi.sudokuki.view.GridView;

public class SwingGrid extends JPanel implements Printable {

	private static final long serialVersionUID = 1L;

	private GridView view;

	private static final int offX = 2;

	private static final int offY = 2;

	private static final int CELL_SIZE = 26;

	private int FONT_SIZE = 20;
	// private static final int CELL_SIZE = 22;
	// private int FONT_SIZE = 18;

	private MouseListener innerMouseListener = new InnerMouseListener();

	private KeyListener innerKeyListener = new InnerKeyListener();

	/*
	 * Column number of the focus mark
	 */
	private int posX = 4;

	/*
	 * Line number of the focus mark
	 */
	private int posY = 4;
	
	private JFrame parent;

	SwingGrid(GridView view, JFrame parent) {
		this.parent = parent;
		this.view = view;

		addMouseListener(this.innerMouseListener);
		addKeyListener(innerKeyListener);
		setPreferredSize(new Dimension(columns[columns.length - 1].getEnd()
				- columns[0].getStart() + offX * 2,
				rows[rows.length - 1].getEnd() - rows[0].getStart() + offY * 2));
	}

	/**
	 * Returns the position where to draw a digit in the grid.
	 * 
	 * @param li
	 *            Line number (must be between 1 and 9) of a cell in the grid
	 * @param co
	 *            Bar number (must be between 1 and 9) of a cell in the grid
	 * @return A Point giving the position where to draw the digit for cell (li,
	 *         co)
	 */
	private Point getPosition(Graphics2D g2, int li, int co) {
		if (!(0 <= li && li < 9 && 0 <= co && co < 9)) {
			throw new IllegalArgumentException();
		}

		FontMetrics fm = getFontMetrics(g2.getFont());
		int h = fm.getHeight();
		int w = fm.stringWidth("X");

		int x = columns[co].getStart() + CELL_SIZE / 2 - w / 2;
		int y = rows[li].getStart() + CELL_SIZE / 2 + h / 4;

		return new Point(x, y);
	}

	/**
	 * Returns the position where to draw a memo in the grid.
	 * 
	 * @param li
	 *            Line number (must be between 1 and 9) of a cell in the grid
	 * @param co
	 *            Bar number (must be between 1 and 9) of a cell in the grid
	 * @return A Point giving the position where to draw the digit for cell (li,
	 *         co)
	 */
	private Point getPositionForMemo(Graphics2D g2, int li, int co, int value) {
		if (!(0 <= li && li < 9 && 0 <= co && co < 9)) {
			throw new IllegalArgumentException();
		}
		
 		FontMetrics fm = getFontMetrics(g2.getFont());
 		int h = fm.getHeight();
		int w = fm.stringWidth("X");
 
		int x = columns[co].getStart() + CELL_SIZE / 2 - w / 2;
		int y = rows[li].getStart() + CELL_SIZE / 2 + h / 4;

 		int xx = (9 - value) % 3 - 1;
		// System.out.println("val: " + value + " xx : " + xx);
 		int yy = (9 - value) / 3 - 1;
		// System.out.println("val: " + value + " yy : " + yy);
 		x = x - xx * 8 + 1;
		y = y - yy * 8 + 1;
 
 		return new Point(x, y);
 	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		paintGridBoard(g2);
		paintFocusMark(g2);
		boolean kanjiMode = UserPreferences.getInstance().getBoolean("kanjiMode", false);
		paintGridNumbers(g2, kanjiMode);
		paintPlayerMemos(g2);
		paintPlayerNumbers(g2, kanjiMode);
	}


	private void paintGridNumbers(Graphics2D g2, boolean kanjiMode) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		// Font font = new Font("Serif", Font.BOLD, FONT_SIZE);
		Font font = new Font("Serif", Font.BOLD, FONT_SIZE
				- (kanjiMode ? 4 : 0));
		g2.setFont(font);

		for (int li = 0; li < 9; li++) {
			for (int co = 0; co < 9; co++) {
				if (view.isCellReadOnly(li, co)) {
					Point pos = getPosition(g2, li, co);
					// System.out.println("SwingGrid.paintGridNumbers() li:"+li+" co:"+co+" value:"+getValueAsStringAt(li,
					// co));
					g2.drawString(getValueAsStringAt(li, co, kanjiMode), pos.x, pos.y);
				}
			}
		}
	}

	private String getValueAsStringAt(int li, int co, boolean kanjiMode) {
		int value = view.getValueAt(li, co);
		String result = "";
		if (kanjiMode) {
			switch (value) {
			case 1:
				result = "\u4e00";
				break;
			case 2:
				result = "\u4E8C";
				break;
			case 3:
				result = "\u4e09";
				break;
			case 4:
				result = "\u56DB";
				break;
			case 5:
				result = "\u4E94";
				break;
			case 6:
				result = "\u516D";
				break;
			case 7:
				result = "\u4E03";
				break;
			case 8:
				result = "\u516B";
				break;
			case 9:
				result = "\u4E5D";
				break;
			}
		} else {
			result = (value == 0) ? "" : String.valueOf(value);
		}
		return result; 
	}

	private void paintFocusMark(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g2.setColor(Color.DARK_GRAY);
		g2.drawRect(columns[posX].getStart(), rows[posY].getStart(),
				CELL_SIZE,
				CELL_SIZE);
		g2.setColor(Color.GRAY);
		g2.drawRect(columns[posX].getStart() + 1 , rows[posY].getStart() + 1,
				CELL_SIZE - 2,
				CELL_SIZE - 2);
		g2.setColor(Color.LIGHT_GRAY);
		g2.drawRect(columns[posX].getStart() + 2 , rows[posY].getStart() + 2,
				CELL_SIZE - 4,
				CELL_SIZE - 4);
	}
	
	private void paintPlayerNumbers(Graphics2D g2, boolean kanjiMode) {
		g2.setColor(Color.BLUE);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Serif", Font.PLAIN, FONT_SIZE- (kanjiMode?4:0));
		g2.setFont(font);

		GridValidity validity = view.getGridValidity();
		Integer firstErrorLine = validity.getFirstErrorLine(); 
		Integer firstErrorColumn = validity.getFirstErrorColumn();
		Integer firstErrorSquareX = validity.getFirstErrorSquareX();
		Integer firstErrorSquareY = validity.getFirstErrorSquareY();
		
		for (int li = 0; li < 9; li++) {
			for (int co = 0; co < 9; co++) {
				if (!view.isCellReadOnly(li, co)) {
					Point pos = getPosition(g2, li, co);
					
					if ((firstErrorLine != null && firstErrorLine == li)
							|| (firstErrorColumn != null && firstErrorColumn == co)
							|| ((firstErrorSquareX != null && firstErrorSquareX <= co && co < firstErrorSquareX + 3) &&
									((firstErrorSquareY != null && firstErrorSquareY <= li && li < firstErrorSquareY + 3)))) {
						g2.setColor(Color.RED);
					} else {
						g2.setColor(Color.BLUE);
					}
					
					g2.drawString(getValueAsStringAt(li, co, kanjiMode), pos.x, pos.y);
				}
			}
		}
	}

	/**
	 * FIXME: this is just an example. TODO: implement for real.
	 * 
	 * @param g2
	 */
	private void paintPlayerMemos(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLUE);
		Font font = new Font("Serif", Font.PLAIN, 9);
		g2.setFont(font);

		for (int li = 0; li < 9; li++) {
			for (int co = 0; co < 9; co++) {
				for (int k = 1; k <= 9; k++) {
					if (view.isCellMemoSet(li, co, (byte) k)) {
						Point pos = getPositionForMemo(g2, li, co, k);
						g2.drawString(String.valueOf(k), pos.x, pos.y);
					}
				}
			}
		}
	}

	private void paintGridBoard(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g2.setColor(Color.WHITE);
		g2.fillRect(columns[0].getStart(), rows[0].getStart(),
				columns[columns.length - 1].getEnd() - 1,
				rows[rows.length - 1].getEnd() - 1);

		g2.setColor(new Color(0xEEEEEE));
		g2.fillRect(columns[3].getStart(), rows[0].getStart(),
				columns[5].getEnd() - columns[3].getStart(),
				rows[rows.length - 1].getEnd());
		g2.fillRect(columns[0].getStart(), rows[3].getStart(),
				columns[columns.length - 1].getEnd(), rows[5].getEnd()
						- rows[3].getStart());
		g2.setColor(Color.WHITE);
		g2.fillRect(columns[3].getStart(), rows[3].getStart(),
				columns[5].getEnd() - columns[3].getStart(), rows[5].getEnd()
						- rows[3].getStart());

		g2.setColor(Color.BLACK);
		for (int li = 0; li < 9; li++) {
			g2.drawLine(columns[0].getStart(), rows[li].getStart(),
					columns[columns.length - 1].getEnd(), rows[li].getStart());
			g2.drawLine(columns[0].getStart(), rows[li].getEnd(),
					columns[columns.length - 1].getEnd(), rows[li].getEnd());
		}
		for (int co = 0; co < 9; co++) {
			g2.drawLine(columns[co].getStart(), rows[0].getStart(),
					columns[co].getStart(), rows[rows.length - 1].getEnd());
			g2.drawLine(columns[co].getEnd(), rows[0].getStart(),
					columns[co].getEnd(), rows[rows.length - 1].getEnd());
		}
		g2.setColor(Color.BLACK);
		g2.drawRect(columns[0].getStart() - 1, rows[0].getStart() - 1,
				columns[columns.length - 1].getEnd() - columns[0].getStart()
						+ 2,
				rows[rows.length - 1].getEnd() - rows[0].getStart() + 2);
	}

	/**
	 * 
	 * @param inPos
	 *            The position (pixels, pixels) in the grid.
	 * @return A Point corresponding to the cell where the input position falls
	 *         in the grid. The x and y of this Point correspond to the column
	 *         and the line of the cell. x or y is 0 in case the given position
	 *         in pixels falls out of the grid.
	 */
	private Position getLiCoForPos(Point inPos) {
		int li = -1;
		int co = -1;
		for (int l = 0; l < rows.length; l++) {
			if (rows[l].getStart() + 2 <= inPos.y && inPos.y < rows[l].getEnd() + 2) {
				li = l;
				System.out.println("SwingGrid.getLiCoForPos() li:" + li);
				break;
			}
		}
		for (int c = 0; c < columns.length; c++) {
			if (columns[c].getStart() <= inPos.x
					&& inPos.x < columns[c].getEnd()) {
				co = c;
				System.out.println("SwingGrid.getLiCoForPos() co:" + co);
				break;
			}
		}

		if (li == -1)
			System.out
					.println("\nSwingGrid.getLiCoForPos() line is out of the grid!!!\n");
		if (co == -1)
			System.out
					.println("\nSwingGrid.getLiCoForPos() column is out of the grid!!!\n");

		return new Position(li, co);
	}

	private Point getTopLeftPoint(int li, int co) {
		int x = columns[co].getStart();
		int y = rows[li].getStart();
		return new Point(x, y);
	}
	
	private class Strip {
		private int start, end;

		Strip(int s, int e) {
			start = s;
			end = e;
		}

		int getStart() {
			return start;
		}

		int getEnd() {
			return end;
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pf, int pageIndex)
			throws PrinterException {

		if (pageIndex != 0)
			return NO_SUCH_PAGE;

		Graphics2D g2 = (Graphics2D) graphics;
		/*
		 * User (0,0) is typically outside the imageable area, so we must
		 * translate by the X and Y values in the PageFormat to avoid clipping
		 */
		Font font = new Font("Serif", Font.PLAIN, 24);
		g2.setFont(font);
		FontMetrics metrics = g2.getFontMetrics();
		int fontHeight = metrics.getHeight();
		g2.translate(pf.getImageableX(), pf.getImageableY());
		g2.translate(0, fontHeight);
		g2.drawString("Sudokuki - essential sudoku game", 0, 0);
		g2.translate(0, fontHeight);
		font = new Font("Serif", Font.PLAIN, 20);
		g2.setFont(font);
		fontHeight = g2.getFontMetrics().getHeight();
		g2.drawString("http://sudokuki.sourceforge.net/", 0, fontHeight);
		g2.translate(240, -40);
		ImageIcon icon = Images.ICON_APPLICATION_LOGO_SMALL;
		g2.drawImage(icon.getImage(), 130, 0, this);
		/* Now we perform our rendering */
		g2.translate(-160, 140);

		paintGridBoard(g2);
		boolean kanjiMode = UserPreferences.getInstance().getBoolean("kanjiMode", false);
		paintGridNumbers(g2, kanjiMode);
		paintPlayerNumbers(g2, kanjiMode);

		return PAGE_EXISTS;
	}

	private class InnerMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent evt) {
			switch (evt.getButton()) {
			case MouseEvent.BUTTON1:
				pressedLeft(evt);
				return;
			case MouseEvent.BUTTON3:
				pressedRight(evt);
				return;
			default:
				System.out
						.println("SwingGrid.InnerMouseListener.mousePressed() center button??");
			}
		}

		private void pressedLeft(MouseEvent evt) {
			System.out.println("SwingGrid.InnerMouseListener.mousePressed()");
			Point pos = evt.getPoint();
			System.out.println("SwingGrid.InnerMouseListener.mousePressed() x:"
					+ pos.x + " y:" + pos.y);
			Position cellPos = getLiCoForPos(pos);
			System.out
					.println("SwingGrid.InnerMouseListener.mousePressed() li:"
							+ cellPos.getLi() + " co:" + cellPos.getCo());

			int li = cellPos.getLi();
			int co = cellPos.getCo();
			if (li == -1 || co == -1) {
				return;
			}
			posY = li;
			posX = co;
			repaint();
			selectValue(li, co, pos.x, pos.y);
			view.getController().notifyFocusPositionChanged(li, co);
		}

		private void pressedRight(MouseEvent evt) {
			System.out.println("SwingGrid.InnerMouseListener.mousePressed()");
			Point pos = evt.getPoint();
			System.out.println("SwingGrid.InnerMouseListener.mousePressed() x:"
					+ pos.x + " y:" + pos.y);
			Position cellPos = getLiCoForPos(pos);
			System.out
					.println("SwingGrid.InnerMouseListener.mousePressed() li:"
							+ cellPos.getLi() + " co:" + cellPos.getCo());

			int li = cellPos.getLi();
			int co = cellPos.getCo();
			if (li == -1 || co == -1) {
				return;
			}
			posY = li;
			posX = co;
			repaint();
			selectMemos(li, co, pos.x, pos.y);
			view.getController().notifyFocusPositionChanged(li, co);
		}

	}

	private void selectValue(int li, int co, int x, int y) {
		if (view.isGrigComplete() || view.isCellReadOnly(li, co)) {
			return;
		}
		pickUpValueOrMemos(true, li, co, x, y);
	}
	
	private void selectMemos(int li, int co, int x, int y) {
		if (view.isGrigComplete() || view.isCellReadOnly(li, co)) {
			return;
		}
		pickUpValueOrMemos(false, li, co, x, y);
	}

	private void pickUpValueOrMemos(boolean valuePickerOnTop, int li, int co, int x, int y) {
		
		byte previousValue = view.getValueAt(li, co);
		Vector<Byte> vec = new Vector<Byte>();
		for (byte i = 1; i <= 9; i++) {
			if (view.isCellMemoSet(li, co, i)) {
				vec.add(new Byte(i));
			}
		}
		Byte[] previousMemos = new Byte[vec.size()];
		previousMemos = vec.toArray(previousMemos);

		DualSwingSelector selector = new DualSwingSelector(valuePickerOnTop, SwingGrid.this.parent,
				SwingGrid.this, x, y, previousValue, previousMemos);
		
		// Handle possible value selection
		int selected = selector.retrieveNumber();
		System.out.println("SwingGrid.pickUpValueOrMemos() selected:"+selected);
		if (selected == previousValue) {
			selected = 0; // Clear the value
		}
		if (0 <= selected && selected <= 9) {
			view.getController().notifyGridValueChanged(li, co, selected);
		}

		// Handle possible memos selection
		byte[] selectedMemos = selector.retrieveMemos();
		if (selectedMemos != null) {
			view.getController().notifyGridMemosChanged(li, co, selectedMemos);
		}
	}

	private class InnerKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke) {
			System.out.println("SwingGrid.InnerKeyAdapter.keyPressed() ke:"+ke);
			int code = ke.getKeyCode();
			boolean hasMoved = false;
			if (code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_DOWN) {
				if (posY < 8) {
					posY++;
					hasMoved = true;
					repaint();
				}
			}
			else if (code == KeyEvent.VK_KP_UP || code == KeyEvent.VK_UP) {
				if (posY > 0) {
					posY--;
					hasMoved = true;
					repaint();
				}
			}
			else if (code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_LEFT) {
				if (posX > 0) {
					posX--;
					hasMoved = true;
					repaint();
				}
			}
			else if (code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_RIGHT) {
				if (posX < 8) {
					posX++;
					hasMoved = true;
					repaint();
				}
			}
			if (hasMoved) {
				System.out
				.println("SwingGrid.InnerKeyListener.keyPressed() has moved...");
				view.getController().notifyFocusPositionChanged(posY, posX);
			}
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			System.out.println("SwingGrid.InnerKeyAdapter.keyReleased() ke:"+ke);
			int code = ke.getKeyCode();
			if (code == KeyEvent.VK_SPACE) {
				Point pos = getTopLeftPoint(posY, posX);
				selectValue(posY, posX, pos.x, pos.y);
			}
			else if (code == KeyEvent.VK_SHIFT) {
				Point pos = getTopLeftPoint(posY, posX);
				selectMemos(posY, posX, pos.x, pos.y);
			}
		}
	}
	
	private Strip[] rows = new Strip[] { new Strip(offY, offY + CELL_SIZE),
			new Strip(offY + CELL_SIZE, offY + 2 * CELL_SIZE),
			new Strip(offY + 2 * CELL_SIZE, offY + 3 * CELL_SIZE),

			new Strip(offY + 3 * CELL_SIZE + 1, offY + 4 * CELL_SIZE + 1),
			new Strip(offY + 4 * CELL_SIZE + 1, offY + 5 * CELL_SIZE + 1),
			new Strip(offY + 5 * CELL_SIZE + 1, offY + 6 * CELL_SIZE + 1),

			new Strip(offY + 6 * CELL_SIZE + 2, offY + 7 * CELL_SIZE + 2),
			new Strip(offY + 7 * CELL_SIZE + 2, offY + 8 * CELL_SIZE + 2),
			new Strip(offY + 8 * CELL_SIZE + 2, offY + 9 * CELL_SIZE + 2), };

	private Strip[] columns = new Strip[] { new Strip(offX, offX + CELL_SIZE),
			new Strip(offX + CELL_SIZE, offX + 2 * CELL_SIZE),
			new Strip(offX + 2 * CELL_SIZE, offX + 3 * CELL_SIZE),

			new Strip(offX + 3 * CELL_SIZE + 1, offX + 4 * CELL_SIZE + 1),
			new Strip(offX + 4 * CELL_SIZE + 1, offX + 5 * CELL_SIZE + 1),
			new Strip(offX + 5 * CELL_SIZE + 1, offX + 6 * CELL_SIZE + 1),

			new Strip(offX + 6 * CELL_SIZE + 2, offX + 7 * CELL_SIZE + 2),
			new Strip(offX + 7 * CELL_SIZE + 2, offX + 8 * CELL_SIZE + 2),
			new Strip(offX + 8 * CELL_SIZE + 2, offX + 9 * CELL_SIZE + 2),

	};

}
