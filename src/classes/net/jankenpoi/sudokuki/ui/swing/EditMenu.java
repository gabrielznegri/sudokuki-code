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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import net.jankenpoi.i18n.I18n;
import net.jankenpoi.i18n.LocaleListener;
import net.jankenpoi.sudokuki.ui.L10nComponent;

@SuppressWarnings("serial")
public class EditMenu extends JMenu implements L10nComponent {

        private final JMenuItem itemClearAllMoves = new JMenuItem();
        private final JMenuItem itemEraseAllMemos = new JMenuItem();
        private final JMenuItem itemCustomGrid = new JMenuItem();
        private final JMenuItem itemPlayCustomGrid = new JMenuItem();
        private final Action actionClearAllMoves;
        private final Action actionEraseAllMemos;
        private final Action actionCustomGrid;
        private final Action actionPlayCustomGrid;
        
        private final LocaleListener localeListener;
        private LevelMenu levelMenu;
        private CheatMenu cheatMenu;
        
        @Override
        public void setL10nMessages(Locale locale, String languageCode) {
                setText(_("Edit"));
                itemClearAllMoves.setText(_("Clear moves"));
                actionClearAllMoves.putValue(Action.SHORT_DESCRIPTION, _("Clear all moves"));
                itemEraseAllMemos.setText(_("Hide memos..."));
                actionEraseAllMemos.putValue(Action.SHORT_DESCRIPTION, _("Hide all memos..."));
                itemCustomGrid.setText(_("Custom grid"));
                actionCustomGrid.putValue(Action.SHORT_DESCRIPTION, _("Compose a custom grid..."));
                itemPlayCustomGrid.setText(_("Play grid"));
                actionPlayCustomGrid.putValue(Action.SHORT_DESCRIPTION, _("Play with current grid..."));
        }

        EditMenu(ActionsRepository actions, JFrame parent, SwingView view) {
                setMnemonic(KeyEvent.VK_E);
                getAccessibleContext().setAccessibleDescription(
                                "Edit menu");
                
                actionClearAllMoves = new ClearAllMovesAction("Clear all moves",
                                StockIcons.ICON_CLEAR, "Clear all my moves", new Integer(
                                                KeyEvent.VK_X), view);
                actions.put("ClearAllMoves", actionClearAllMoves);
                
                actionEraseAllMemos = new EraseAllMemosAction("Erase all memos",
                                StockIcons.ICON_CLEAR_ALL_MEMOS, "Erase all memos...", new Integer(
                                                KeyEvent.VK_X), view);
                actions.put("EraseAllMemos", actionEraseAllMemos);
                
                actionCustomGrid = new CustomGridAction("Custom grid",
                                StockIcons.ICON_EDIT, "Compose a custom grid...", new Integer(
                                                KeyEvent.VK_G), view);
                actions.put("CustomGrid", actionCustomGrid);

                actionPlayCustomGrid = new PlayCustomGridAction("Play custom grid",
                                StockIcons.ICON_PLAY, "Play with current grid...", new Integer(
                                                KeyEvent.VK_H), view);
                actions.put("PlayCustomGrid", actionPlayCustomGrid);
                
                addItems(actions, parent, view);
                
                setL10nMessages(null, _("DETECTED_LANGUAGE"));
                localeListener = new LocaleListenerImpl(this);
                I18n.addLocaleListener(localeListener);
        }

        private void addItems(ActionsRepository actions, JFrame parent, SwingView view) {
                
                itemClearAllMoves.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                                ActionEvent.CTRL_MASK));
                itemClearAllMoves.setAction(actionClearAllMoves);
                actionClearAllMoves.setEnabled(false);
                add(itemClearAllMoves);

                itemEraseAllMemos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
                                ActionEvent.CTRL_MASK));
                itemEraseAllMemos.setAction(actionEraseAllMemos);
                actionEraseAllMemos.setEnabled(false);
                add(itemEraseAllMemos);
                
                addSeparator();

                levelMenu = new LevelMenu();
                add(levelMenu);

                add(new NumbersMenu(view));
                
                add(new LanguageMenu());
                
                addSeparator();
                
                cheatMenu = new CheatMenu(actions, parent, view);
                add(cheatMenu);
                
                addSeparator();
                
                itemCustomGrid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                                ActionEvent.CTRL_MASK));
                itemCustomGrid.setAction(actionCustomGrid);
                itemCustomGrid.setEnabled(true);
                add(itemCustomGrid);

                itemPlayCustomGrid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
                                ActionEvent.CTRL_MASK));
                itemPlayCustomGrid.setAction(actionPlayCustomGrid);
                itemPlayCustomGrid.setEnabled(false);
                add(itemPlayCustomGrid);
        }

        public CheatMenu getCheatMenu() {
                return cheatMenu;
        }

        public LevelMenu getLevelMenu() {
                return levelMenu;
        }

}
