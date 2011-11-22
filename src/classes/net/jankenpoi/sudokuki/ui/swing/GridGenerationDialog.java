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

import static net.jankenpoi.i18n.I18n._;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import net.jankenpoi.sudokuki.view.GridView;
/**
 * CheckUpdateDialog.java
 * 
 * @author svedrenne
 */
@SuppressWarnings("serial")
public class GridGenerationDialog extends JDialog {

	private JFrame parent;

	private int status = -1;

	private final GridView view;

	private final SwingWorker<Integer, Void> worker;

	public GridGenerationDialog(JFrame parent, final GridView view) {

		super(parent, true);
		this.parent = parent;
		this.view = view;
		setResizable(false);

		short[] flagsTable = new short[81];
		for (int li = 0; li < 9; li++) {
			for (int co = 0; co < 9; co++) {
				if (view.isCellReadOnly(li, co)) {
					flagsTable[9 * li + co] = view.getValueAt(li, co);
				}
			}
		}
		worker = new SwingWorker<Integer, Void>() {

			@Override
			/* Executed in the SwingWorker thread */
			protected Integer doInBackground() {
				return generateGrid();
			}

			@Override
			/* Executed in the EDT, triggered when the SwingWorker has completed */
			protected void done() {
				boolean isGenerated = false;
				try {
					status = get();
					if (status == 0) {
						isGenerated = true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				} catch (ExecutionException e) {
					e.printStackTrace();
					return;
				}
				dispose();
			}
		};
		initComponents();
		worker.execute();
	}

	private void initComponents() {

		// FIXME: TODO: search how to do something special in case the window is
		// closed => set status to "CANCELLED"
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		Container pane = getContentPane();
		GridLayout btnLayout = new GridLayout(4, 1);
		pane.setLayout(btnLayout);

		
		JLabel messageLbl1 = new JLabel(	
		"<html>"
		+ "<table border=\"0\">" + "<tr>"
		+ _("Generating grid...") + "</tr>"
		+ "</html>");
		
		JLabel messageLbl3 = new JLabel("");
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		cancelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				clickedCancel();
			}
		});

		pane.add(messageLbl1);
		pane.add(messageLbl3);
		pane.add(cancelBtn);

		pack();
		setLocationRelativeTo(parent);
	}

	private void clickedCancel() {
		System.out.println("CheckUpdateDialog.buttonClicked()");
		/**
		 * CANCELLED
		 */
		System.out
				.println("ResolveGridDialog.ResolveGridDialog(...) CANCELLED");
	}

	/**
	 * 
	 * @return <b>0</b> if the resolution was successful<br/>
	 *         <b>1</b> if the solving process was canceled by the user before
	 *         completion<br/>
	 *         <b>2</b> if the process failed to resolve the grid
	 */
	public int getResult() {
		return status;
	}

	private int generateGrid() {
		view.getController().notifyNewGridRequested();
		return 0;
	}

}
