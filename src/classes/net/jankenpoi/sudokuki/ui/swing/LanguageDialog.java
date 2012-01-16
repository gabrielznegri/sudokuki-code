/*
 * Sudokuki - essential sudoku game
 * Copyright (C) 2007-2012 Sylvain Vedrenne
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

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.jankenpoi.i18n.I18n;

@SuppressWarnings("serial")
public class LanguageDialog extends JDialog {

//	private JFrame parent;

	public LanguageDialog(JFrame parent, ToolBar toolbar) {
		super(parent, true);
		initComponents();
		setResizable(false);
		pack();
		
		Point toolBarLoc = toolbar.getLocationOnScreen();
		setLocation(toolBarLoc.x + toolbar.getWidth()/2 - getWidth()/2, toolBarLoc.y + toolbar.getHeight());
		setSize(getPreferredSize());
	}

	private void initComponents() {
		JPanel panel = new JPanel();
        GridLayout btnLayout = new GridLayout(8, 1);
        panel.setLayout(btnLayout);
		
		String detectedLanguage = _("DETECTED_LANGUAGE");
		System.out.println("LanguageMenu.addItems() detected language:"
				+ detectedLanguage);

		ButtonGroup myGroup = new ButtonGroup();

		JRadioButton radioItem = new JRadioButton("German", StockIcons.ICON_FLAG_DE);
		if (detectedLanguage.equals("de"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("German") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("de");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);

		radioItem = new JRadioButton("Greek", StockIcons.ICON_FLAG_EL);
		if (detectedLanguage.equals("el"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("Greek") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("el");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);

		radioItem = new JRadioButton("English", StockIcons.ICON_FLAG_EN);
		if (detectedLanguage.equals("en"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("English") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("en");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);

		radioItem = new JRadioButton("Esperanto", StockIcons.ICON_FLAG_EO);
		if (detectedLanguage.equals("eo"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("Esperanto") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("eo");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);

		radioItem = new JRadioButton("Spanish", StockIcons.ICON_FLAG_ES);
		if (detectedLanguage.equals("es"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("Spanish") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("es");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);

		radioItem = new JRadioButton("French", StockIcons.ICON_FLAG_FR);
		if (detectedLanguage.equals("fr"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("French") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("fr");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);
		
		radioItem = new JRadioButton("Japanese", StockIcons.ICON_FLAG_JA);
		if (detectedLanguage.equals("ja"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("Japanese") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("ja");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);
		
		radioItem = new JRadioButton("Portuguese", StockIcons.ICON_FLAG_PT);
		radioItem.setEnabled(true);
		if (detectedLanguage.equals("pt"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("Portuguese") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("pt");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);

		radioItem = new JRadioButton("Russian", StockIcons.ICON_FLAG_RU);
		radioItem.setEnabled(true);
		if (detectedLanguage.equals("ru"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("Russian") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("ru");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);
		
		radioItem = new JRadioButton("Chinese", StockIcons.ICON_FLAG_ZH);
		radioItem.setEnabled(true);
		if (detectedLanguage.equals("zh"))
			radioItem.setSelected(true);
		radioItem.setAction(new AbstractAction("Chinese") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				I18n.reset("zh");
			}
		});
		myGroup.add(radioItem);
		panel.add(radioItem);

		
		
		add(panel);
	}

}