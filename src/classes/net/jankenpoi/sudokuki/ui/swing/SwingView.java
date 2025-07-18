/*
 * Sudokuki - essential sudoku game
 * Copyright (C) 2007-2016 Sylvain Vedrenne
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

import static net.jankenpoi.i18n.I18n.gtxt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import net.jankenpoi.sudokuki.model.GridChangedEvent;
import net.jankenpoi.sudokuki.model.GridModel;
import net.jankenpoi.sudokuki.view.GridView;

public class SwingView extends GridView {

	private SwingGrid grid;
	private JFrame frame;

	private ActionsRepository actions;
	private CheatMenu cheatMenu;
	private LevelMenu levelMenu;

	private Timer timer;
	private int seconds = 0;
	private JLabel timerLabel;
	private JButton startButton;
	private boolean running = false;
	
	public SwingView(GridModel model) {
		super(model);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame = new JFrame("Sudokuki");
				frame.setLayout(new BorderLayout());
				grid = new SwingGrid(SwingView.this, frame);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(true);
				
				frame.setIconImage(Images.ICON_APPLICATION.getImage());
				
				MenuBar menuBar = new MenuBar(frame, grid, SwingView.this);
				levelMenu = menuBar.getLevelMenu();
				cheatMenu = menuBar.getCheatMenu();
				frame.setJMenuBar(menuBar);
				
				JToolBar toolbar = new ToolBar(frame, menuBar.getActions());
				frame.getContentPane().add(toolbar, BorderLayout.PAGE_START);
				frame.getContentPane().add(grid, BorderLayout.CENTER);

				timerLabel = new JLabel("00:00");
				startButton = new JButton("Iniciar Timer");

				timer = new Timer(1000, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						seconds++;
						int mins = seconds / 60;
						int secs = seconds % 60;
						timerLabel.setText(String.format("%02d:%02d", mins, secs));
					}
				});

				startButton.addActionListener(e -> toggleTimer());

				JPanel timerPanel = new JPanel();
				timerPanel.add(startButton);
				timerPanel.add(timerLabel);

				frame.getContentPane().add(timerPanel, BorderLayout.SOUTH);

				actions = menuBar.getActions();
				
				frame.pack();
				frame.setLocationRelativeTo(null);
				grid.requestFocusInWindow();
				/*
				 * Avoid TAB key presses to cause the focus to go out of the
				 * grid
				 */
				grid.setFocusTraversalKeysEnabled(false);
			}
		});
	}

	@Override
	public void display() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setVisible(true);
				frame.setSize(frame.getPreferredSize());
			}
		});
	}

	public void gridChanged(final GridChangedEvent event) {
		resetTimer();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				grid.repaint();
				
				GridModel model = (GridModel) event.getSource();
				refreshSaveAsAction(model);
				refreshPrintGridAction(model);
				refreshClearAllMovesAction(model);
				
				refreshClearAllMemosAction(model);
				refreshLevelMenu(model);
				refreshSolutionMenu(model);
				
				refreshResolveAction(model);
				refreshCustomGridAction(model);
				refreshPlayCustomGridAction(model);
			}

		});
	}
	
	@Override
	public void gridComplete() {
		resetTimer();

		JOptionPane.showMessageDialog(frame, 
		"<html>"
        + "<table border=\"0\">"
        + "<tr>"
        + "<td align=\"center\"><b>"
        + gtxt("Congratulations!")
        + "</b></td>"
        + "</tr>"
        + "<tr>"
        + "</tr>"
        + "<tr>"
        + "<td align=\"center\">"
        + gtxt("Grid complete!") + "</td>"
        + "</tr>" + "</table>" + "</html>", "Sudokuki", JOptionPane.PLAIN_MESSAGE);
	}
	
	@Override
	public void gridResolved() {
		resetTimer();
		
		JOptionPane.showMessageDialog(frame, "<html>" + "<table border=\"0\">"
				+ "<tr>" + gtxt("Grid resolved with success.") + "</tr>"
				+ "</html>", "Sudokuki", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void refreshSaveAsAction(GridModel model) {
		if (actions == null)
			return;
		Action saveAsAction = actions.get("SaveAs");
		saveAsAction.setEnabled(!model.getCustomGridMode());
	}
	
	private void refreshPrintGridAction(GridModel model) {
		if (actions == null)
			return;
		Action printGridAction = actions.get("Print");
		printGridAction.setEnabled(!model.getCustomGridMode());
	}
	
	private void refreshClearAllMemosAction(GridModel model) {
		if (actions == null)
			return;
		Action eraseAllMemosAction = actions.get("EraseAllMemos");
		eraseAllMemosAction.setEnabled(model.areSomeMemosSet());
	}

	private void refreshClearAllMovesAction(GridModel model) {
		if (actions == null) {
			return;
		}
		Action clearAllMovesAction = actions.get("ClearAllMoves"); 
		clearAllMovesAction.setEnabled(!model.isGridComplete() && model.areSomeCellsFilled());
	}
	
	private void refreshLevelMenu(GridModel model) {
		levelMenu.setEnabled(!model.getCustomGridMode());
	}

	private void refreshSolutionMenu(GridModel model) {
		cheatMenu.setEnabled(!model.getCustomGridMode() && model.areSomeCellsEmpty());
	}

	protected void refreshResolveAction(GridModel model) {
		if (actions == null) {
			return;
		}
		Action resolveAction = actions.get("ResolveGrid"); 
		resolveAction.setEnabled(!model.getCustomGridMode() && model.areSomeCellsEmpty());
	}
	
	private void refreshCustomGridAction(GridModel model) {
		if (actions == null) {
			return;
		}
		Action customGridAction = actions.get("CustomGrid"); 
		customGridAction.setEnabled(!model.isGridComplete() && !model.getCustomGridMode() && !model.isGridFull());
	}

	private void refreshPlayCustomGridAction(GridModel model) {
		if (actions == null) {
			return;
		}
		Action playCustomGridAction = actions.get("PlayCustomGrid"); 
		playCustomGridAction.setEnabled(model.getCustomGridMode() && model.getGridValidity().isGridValid());
	}

	private void toggleTimer() {
		if (running) {
			timer.stop();
			startButton.setText("Iniciar Timer");
		} else {
			timer.start();
			startButton.setText("Pausar Timer");
		}
		running = !running;
	}

	private void resetTimer() {
		if (timer != null) {
			timer.stop();
		}
		seconds = 0;
		running = false;
		if (startButton != null) {
			startButton.setText("Iniciar Timer");
		}
		if (timerLabel != null) {
			timerLabel.setText("00:00");
		}
	}

	@Override
	public void close() {
	}

}
