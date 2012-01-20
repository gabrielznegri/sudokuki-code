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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import net.jankenpoi.i18n.I18n;
import net.jankenpoi.i18n.LocaleListener;
import net.jankenpoi.sudokuki.ui.L10nComponent;

@SuppressWarnings("serial")
public class LanguageMenu extends JMenu {

        private HashMap<String, JRadioButtonMenuItem> itemsMap = new HashMap<String, JRadioButtonMenuItem>();

        private String langCode;
        
        public LanguageMenu() {
                icons.put("de", StockIcons.ICON_FLAG_DE);
                icons.put("el", StockIcons.ICON_FLAG_EL);
                icons.put("eo", StockIcons.ICON_FLAG_EO);
                icons.put("en", StockIcons.ICON_FLAG_EN);
                icons.put("es", StockIcons.ICON_FLAG_ES);
                icons.put("fr", StockIcons.ICON_FLAG_FR);
                icons.put("ja", StockIcons.ICON_FLAG_JA);
                icons.put("pt", StockIcons.ICON_FLAG_PT);
                icons.put("ru", StockIcons.ICON_FLAG_RU);
                icons.put("zh", StockIcons.ICON_FLAG_ZH);
                addItems();
                setIcon(StockIcons.ICON_GO_HOME);
                
                addMenuListener(new MenuListener() {
                        
                        @Override
                        public void menuSelected(MenuEvent arg0) {
                                final String detectedLanguage = _("DETECTED_LANGUAGE");
                                langCode = detectedLanguage;
                        }
                        
                        @Override
                        public void menuDeselected(MenuEvent arg0) {
                                I18n.reset(langCode);
                        }
                        
                        @Override
                        public void menuCanceled(MenuEvent arg0) {
                                I18n.reset(langCode);
                        }
                });
                
                final String detectedLanguage = _("DETECTED_LANGUAGE");
                JRadioButtonMenuItem selectedItem = itemsMap.get(detectedLanguage);
                setText(_("Language"));
                if (selectedItem != null) {
                        selectedItem.setSelected(true);
                }
        }

        private final HashMap<String, Icon> icons = new HashMap<String, Icon>();

        private void addItems() {
                ButtonGroup myGroup = new ButtonGroup();
                addItem("de", _("German"), myGroup);
                addItem("el", _("Greek"), myGroup);
                addItem("en", _("English"), myGroup);
                addItem("eo", _("Esperanto"), myGroup);
                addItem("es", _("Spanish"), myGroup);
                addItem("fr", _("French"), myGroup);
                addItem("ja", _("Japanese"), myGroup);
                addItem("pt", _("Portuguese"), myGroup);
                addItem("ru", _("Russian"), myGroup);
                addItem("zh", _("Mandarin"), myGroup);
        }

        private void addItem(final String code, String language, ButtonGroup group) {
                JRadioButtonMenuItem radioItem;

                radioItem = new JRadioButtonMenuItem(language);
                itemsMap.put(code, radioItem);
                if (code.equals(code)) {
                        radioItem.setSelected(true);
                }
//              System.out.println("LanguageMenu.addItem() icons.get(code):"+code+" "+icons.get(code));
                radioItem.setAction(new AbstractAction(language, icons.get(code)) {

                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                                I18n.reset(code);
                        }
                });

                radioItem.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseEntered(MouseEvent e) {
                                I18n.reset(code);
                        }
                        
                        @Override
                        public void mouseExited(MouseEvent e) {
                                I18n.reset(langCode);
                        }

                });
                group.add(radioItem);
                add(radioItem);
        }

}