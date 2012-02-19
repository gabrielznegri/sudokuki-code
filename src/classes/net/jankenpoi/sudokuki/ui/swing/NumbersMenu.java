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

import java.awt.event.ActionEvent;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import net.jankenpoi.i18n.I18n;
import net.jankenpoi.sudokuki.preferences.UserPreferences;
import net.jankenpoi.sudokuki.ui.L10nComponent;

@SuppressWarnings("serial")
public class NumbersMenu extends JMenu implements L10nComponent {

	private final SwingView view;
	private final JRadioButtonMenuItem itemArabicNumbers = new JRadioButtonMenuItem();
	private final JRadioButtonMenuItem itemChineseNumbers = new JRadioButtonMenuItem();
	private final Action actionArabicNumbers;
	private final Action actionChineseNumbers;
	private LocaleListenerImpl localeListener;

	public NumbersMenu(SwingView view) {
		this.view = view;

		actionArabicNumbers = new AbstractAction(_("Arabic"), null) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setKanjiMode(false);
			}
		};
		
		actionChineseNumbers = new AbstractAction(_("Chinese"), null) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setKanjiMode(true);
			}
		};

		addItems();
		setIcon(StockIcons.ICON_FONT);
		
		setL10nMessages(null, _("DETECTED_LANGUAGE"));
		localeListener = new LocaleListenerImpl(this);
		I18n.addLocaleListener(localeListener);
		
		setEnabled(true);
	}

	@Override
	public void setL10nMessages(Locale locale, String languageCode) {
		setText(_("Numbers"));
		itemArabicNumbers.setText(_("Arabic"));
		itemChineseNumbers.setText(_("Chinese"));
	}
	
	private void addItems() {
		
		ButtonGroup numbersGroup = new ButtonGroup();
		
		boolean kanjiMode = UserPreferences.getInstance().getBoolean("KanjiMode", false);
		
		itemArabicNumbers.setAction(actionArabicNumbers);
		numbersGroup.add(itemArabicNumbers);
		itemArabicNumbers.setSelected(!kanjiMode);
		add(itemArabicNumbers);
		
		itemChineseNumbers.setAction(actionChineseNumbers);
		numbersGroup.add(itemChineseNumbers);
		itemChineseNumbers.setSelected(kanjiMode);
		add(itemChineseNumbers);
	}
	
	private void setKanjiMode(boolean mode) {
		UserPreferences.getInstance().set("kanjiMode", mode);
		view.getController().notifyGridChanged();
	}
	
}
