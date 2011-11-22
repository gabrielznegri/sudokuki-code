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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;

/*
 * InputDialog.java
 *
 * Created on Mar 25, 2011, 5:50:15 PM
 */

/**
 * 
 * @author svedrenne
 */
@SuppressWarnings("serial")
public class SelectMemosDialog extends JDialog {

	private JButton btnClear;
	private JCheckBox[] ckb = new JCheckBox[9];
	private int focusedElement;
	private boolean focusedOkButton;
	private boolean focusedClearButton;
	private JButton btnConfirm;
	private JPanel panelClear = new JPanel(new GridLayout());
	private JPanel panel789 = new JPanel(new GridLayout());
	private JPanel panel456 = new JPanel(new GridLayout());
	private JPanel panel123 = new JPanel(new GridLayout());
	private JPanel panelConfirm = new JPanel(new GridLayout());
	private HashSet<Byte> memos = new HashSet<Byte>();

	private Font BOLD_FONT = new Font("Serif", Font.BOLD, 18);
	private Font NORMAL_FONT = new Font("Serif", Font.PLAIN, 18);

	private InnerKeyListener innerKeyListener = new InnerKeyListener();
	private InnerFocusListener innerFocusListener = new InnerFocusListener();
	
	public SelectMemosDialog(Frame parent, Byte[] previousMemos) {
		super(parent, true);
		initComponents(previousMemos);
		setResizable(false);
	}

	private void configureCheckBox(JCheckBox btn, String text, final int button) {
		// System.out.println("InputDialog.configureButton() btn:"+btn+" text:"+text+" value:"+value);

//		btn.setBorderPainted(false);
		btn.setFont(NORMAL_FONT);

//		btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btn.setToolTipText(text);
		btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonClicked(button);
			}
		});
		btn.addKeyListener(innerKeyListener);
		btn.addFocusListener(innerFocusListener);
	}

	private void initComponents(Byte[] previousMemos) {

		if (previousMemos != null) {
			for (int i = 0; i < previousMemos.length; i++) {
				memos.add(previousMemos[i]);
			}
		}
		for (int i = 0; i < ckb.length; i++) {
			ckb[i] = new JCheckBox(String.valueOf(i+1));
		}
		btnConfirm = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		btnClear = new JButton();
		btnClear.setText("Clear memos");
		btnClear.setEnabled(true);
		btnClear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				clearClicked();
			}
			private void clearClicked() {
				for (byte i=1; i<=ckb.length; i++) {
					memos.remove(Byte.valueOf(i));
					ckb[i-1].setFont(NORMAL_FONT);
					ckb[i-1].setSelected(false);
				}
			}
		});
		btnClear.addKeyListener(innerKeyListener);
		btnClear.addFocusListener(innerFocusListener);
		configureCheckBox(ckb[6], "7", 6);
		configureCheckBox(ckb[7], "8", 7);
		configureCheckBox(ckb[8], "9", 8);
		configureCheckBox(ckb[3], "4", 3);
		configureCheckBox(ckb[4], "5", 4);
		configureCheckBox(ckb[5], "6", 5);
		configureCheckBox(ckb[0], "1", 0);
		configureCheckBox(ckb[1], "2", 1);
		configureCheckBox(ckb[2], "3", 2);
		btnConfirm.setText("Ok");
		btnConfirm.setEnabled(true);
		btnConfirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				confirmClicked();
			}

			private void confirmClicked() {
				dispose();
			}
		});
		btnConfirm.addKeyListener(innerKeyListener);
		btnConfirm.addFocusListener(innerFocusListener);

		Iterator<Byte> it = memos.iterator();
		while (it.hasNext()) {
			JToggleButton button = ckb[it.next()-1];
			button.setSelected(true);
			button.setFont(BOLD_FONT);
		}
		

		Container pane = getContentPane();
		GridLayout btnLayout = new GridLayout(5, 1);
		pane.setLayout(btnLayout);
		pane.add(panelClear);
		pane.add(panel123);
		pane.add(panel456);
		pane.add(panel789);
		pane.add(panelConfirm);

		panelClear.add(btnClear);
		panel789.add(ckb[6], BorderLayout.LINE_START);
		panel789.add(ckb[7], BorderLayout.CENTER);
		panel789.add(ckb[8], BorderLayout.LINE_END);
		panel456.add(ckb[3], BorderLayout.LINE_START);
		panel456.add(ckb[4], BorderLayout.CENTER);
		panel456.add(ckb[5], BorderLayout.LINE_END);
		panel123.add(ckb[0], BorderLayout.LINE_START);
		panel123.add(ckb[1], BorderLayout.CENTER);
		panel123.add(ckb[2], BorderLayout.LINE_END);
		panelConfirm.add(btnConfirm);

		pack();
		
		ckb[4].requestFocusInWindow();
		focusedElement = 4;
	}

	private void buttonClicked(int button) {
		System.out.println("InputDialog.buttonClicked() button:" + button);
		byte value = (byte)(button + 1);
		if (ckb[button].isSelected()) {
			memos.add(value);
			ckb[button].setFont(BOLD_FONT);
		} else {
			memos.remove(value);
			ckb[button].setFont(NORMAL_FONT);
		}
	}

	public byte[] getSelectedMemos() {
		byte[] memosArray = new byte[memos.size()];

		Iterator<Byte> it = memos.iterator();
		int i = 0;
		while (it.hasNext()) {
			memosArray[i] = it.next();
			i++;
		}
		return memosArray;
	}

	private class InnerKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke) {
			int code = ke.getKeyCode();
			if (code == KeyEvent.VK_KP_UP || code == KeyEvent.VK_UP) {
				if (focusedClearButton) {
					return;
				}
				if (focusedOkButton) {
					focusedOkButton = false;
					ckb[focusedElement].requestFocusInWindow();
					return;
				}
				if (focusedElement / 3 == 0) {
					btnClear.requestFocusInWindow();
					return;
				}
				focusedElement = Math.max(0, focusedElement-3);
				ckb[focusedElement].requestFocusInWindow();
			}
			else if (code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_DOWN) {
				if (focusedOkButton) {
					return;
				}
				if (focusedClearButton) {
					focusedClearButton = false;
					ckb[focusedElement].requestFocusInWindow();
					return;
				}
				if (focusedElement / 3 == 2) {
					btnConfirm.requestFocusInWindow();
					return;
				}
				focusedElement = Math.min(8, focusedElement+3);
				ckb[focusedElement].requestFocusInWindow();
			}
			else if (code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_LEFT) {
				if (focusedElement%3 == 0 || focusedOkButton || focusedClearButton) {
					return;
				}
				focusedElement = Math.max(0, focusedElement-1);
				ckb[focusedElement].requestFocusInWindow();
			}
			else if (code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_RIGHT) {
				if (focusedElement%3 == 2 || focusedOkButton || focusedClearButton) {
					return;
				}
				focusedElement = Math.min(8, focusedElement+1);
				ckb[focusedElement].requestFocusInWindow();
			}
			else if (code == KeyEvent.VK_ESCAPE) {
				dispose();
			}
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			int code = ke.getKeyCode();
			if (code == KeyEvent.VK_SPACE) {
			}
			else if (code == KeyEvent.VK_ENTER) {
			}
		}

	}
	
	private class InnerFocusListener extends FocusAdapter {

		@Override
		public void focusGained(FocusEvent e) {
			focusedClearButton = false;
			focusedOkButton = false;
			
			Component comp = e.getComponent();
			if (comp == ckb[focusedElement]) {
				return;
			}
			for (int i=0; i<ckb.length; i++) {
				if (comp == ckb[i]) {
					focusedElement = i;
					return;
				}
			}
			if (comp == btnClear) {
				focusedClearButton = true;
				focusedElement = focusedElement%3;
				return;
			}
			if (comp == btnConfirm) {
				focusedOkButton = true;
				focusedElement = 6 + focusedElement%3;
				return;
			}
		}
	}
	
}
