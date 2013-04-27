/*
 * Sudokuki - essential sudoku game
 * Copyright (C) 2007-2013 Sylvain Vedrenne
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

import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.jankenpoi.i18n.I18n;
import net.jankenpoi.i18n.LocaleListener;
import net.jankenpoi.sudokuki.ui.L10nComponent;

@SuppressWarnings("serial")
public class HelpMenu extends JMenu implements L10nComponent {

	private final JFrame parent;
	private final JMenuItem itemCheckUpdate = new JMenuItem();
	private final Action actionCheckUpdate;
	private final JMenuItem itemUpdateSite = new JMenuItem();
	private final Action actionOpenUpdateSite;
	private final JMenuItem itemTranslate = new JMenuItem();
	private final Action actionTranslate;
	private final JMenuItem itemAbout = new JMenuItem();
	private final Action actionAbout;
	
	private final LocaleListener localeListener;
	@Override
	public void setL10nMessages(Locale locale, String languageCode) {
		setText(_("Help"));
		
		itemCheckUpdate.setText(_("Update"));
		actionCheckUpdate.putValue(Action.SMALL_ICON, StockIcons.ICON_VIEW_REFRESH);
		actionCheckUpdate.putValue(Action.SHORT_DESCRIPTION, _("Check for updates"));
		actionCheckUpdate.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_U));
		
		itemUpdateSite.setText(_("Download"));
		actionOpenUpdateSite.putValue(Action.SMALL_ICON, StockIcons.ICON_UPDATE_AVAILABLE);
		actionOpenUpdateSite.putValue(Action.SHORT_DESCRIPTION, _("Download new version"));
		actionOpenUpdateSite.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_D));
		
		itemTranslate.setText(_("Translate this application"));
		actionTranslate.putValue(Action.SMALL_ICON, StockIcons.ICON_TRANSLATE);
		actionTranslate.putValue(Action.SHORT_DESCRIPTION, _(_("Help translate this application")));
		actionTranslate.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_T));
		
		itemAbout.setText(_("About..."));
		actionAbout.putValue(Action.SMALL_ICON, StockIcons.ICON_HELP_ABOUT);
		actionAbout.putValue(Action.SHORT_DESCRIPTION, _("About Sudokuki..."));
		actionAbout.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_P));
	}

	HelpMenu(ActionsRepository actions, JFrame frame) {
		this.parent = frame;
		setMnemonic(KeyEvent.VK_H);
		getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		
		actionOpenUpdateSite = new OpenUpdateSiteAction(parent);
		actions.put("OpenUpdateSite", actionOpenUpdateSite);
		actionCheckUpdate = new CheckUpdateAction(parent, actionOpenUpdateSite);
		actions.put("CheckUpdate", actionCheckUpdate);
		
		actionTranslate = new TranslateAction(parent);
		actions.put("Translate", actionTranslate);
		
		actionAbout = new AboutAction(parent);
		actions.put("About", actionAbout);
		addItems();
		setL10nMessages(null, _("DETECTED_LANGUAGE"));
		localeListener = new LocaleListenerImpl(this);
		I18n.addLocaleListener(localeListener);
	}

	private void addItems() {
		itemCheckUpdate.setAction(actionCheckUpdate);
		add(itemCheckUpdate);
		
		itemUpdateSite.setAction(actionOpenUpdateSite);
		add(itemUpdateSite);

		addSeparator();

		itemTranslate.setAction(actionTranslate);
		add(itemTranslate);

		addSeparator();

		itemAbout.setAction(actionAbout);
		add(itemAbout);
	}
}
