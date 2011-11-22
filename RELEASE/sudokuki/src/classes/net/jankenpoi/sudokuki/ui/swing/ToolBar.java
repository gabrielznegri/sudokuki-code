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

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar {
	
	private JFrame frame;
	
	ToolBar(JFrame frame, ActionsRepository actions) {
		this.frame = frame;
		setFloatable(false);
		Action newGridAction = actions.get("NewGrid");
		Action printAction = actions.get("Print");
		Action checkUpdateAction = actions.get("CheckUpdate");
		Action openUpdateSiteAction = actions.get("OpenUpdateSite");
		Action aboutAction = actions.get("About");
		addButtons(newGridAction, printAction, checkUpdateAction, openUpdateSiteAction, aboutAction);

		Component[] components = getComponents();
		for (int i=0; i<components.length; i++) {
			components[i].setFocusable(false);
		}
	}

	private void addButtons(Action newGridAction, Action printAction, Action checkUpdateAction, Action openUpdateSiteAction, Action aboutAction) {
		JButton button = new JButton(StockIcons.ICON_NEW);
		button.setAction(newGridAction);
	    this.add(button);
	    
		button = new JButton(printAction);
	    this.add(button);

		button = new JButton(StockIcons.ICON_OPEN);
	    button.setEnabled(false);
	    this.add(button);

		button = new JButton(StockIcons.ICON_SAVE_AS);
	    button.setEnabled(false);
	    this.add(button);

	    button = new JButton(openUpdateSiteAction);
		button.setText("");
	    button.setEnabled(false);
	    this.add(button);
	    
	    Action actionInvokeLanguageMenu = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LanguageDialog dlg = new LanguageDialog(frame, ToolBar.this);
				dlg.setVisible(true);
			}

	    };
	    button = new JButton(actionInvokeLanguageMenu);
	    button.setEnabled(true);
	    actionInvokeLanguageMenu.putValue(Action.SMALL_ICON, StockIcons.ICON_GO_HOME);
	    actionInvokeLanguageMenu.putValue(Action.SHORT_DESCRIPTION, _("Language"));

		button.setText("");
	    this.add(button);
	}

}
