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

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.jankenpoi.i18n.I18n;
import net.jankenpoi.i18n.LocaleListener;
import net.jankenpoi.sudokuki.ui.L10nComponent;

@SuppressWarnings("serial")
public class LanguageDialog extends JDialog implements L10nComponent {

	private HashMap<String, JRadioButton> itemsMap = new HashMap<String, JRadioButton>();

	private LocaleListener localeListener;
	
	public LanguageDialog(JFrame parent, ToolBar toolbar) {
		super(parent, true);
		setTitle(_("Language"));

		initComponents();
		setResizable(false);
		pack();

		okBtn.requestFocus();
		
		Point toolBarLoc = toolbar.getLocationOnScreen();
		setLocation(toolBarLoc.x + toolbar.getWidth() / 2 - getWidth() / 2,
				toolBarLoc.y + toolbar.getHeight());
		setSize(getPreferredSize());
		
        localeListener = new LocaleListenerImpl(this);
        I18n.addLocaleListener(localeListener);
	}

	private JPanel panel = new JPanel();
	private JPanel btnPanel = new JPanel();
	private JButton okBtn = new JButton(_("Ok"));
	
	private void initComponents() {
		
		Container pane = getContentPane();
		BoxLayout globalLayout = new BoxLayout(pane, BoxLayout.Y_AXIS);
		pane.setLayout(globalLayout);

		GridLayout pnlLayout = new GridLayout(5, 1);
		panel.setLayout(pnlLayout);

        icons.put("ar", StockIcons.ICON_FLAG_AR);
        icons.put("de", StockIcons.ICON_FLAG_DE);
        icons.put("el", StockIcons.ICON_FLAG_EL);
        icons.put("eo", StockIcons.ICON_FLAG_EO);
        icons.put("en", StockIcons.ICON_FLAG_EN);
        icons.put("es", StockIcons.ICON_FLAG_ES);
        icons.put("fr", StockIcons.ICON_FLAG_FR);
        icons.put("hu", StockIcons.ICON_FLAG_HU);
        icons.put("ja", StockIcons.ICON_FLAG_JA);
        icons.put("lv", StockIcons.ICON_FLAG_LV);
        icons.put("nl", StockIcons.ICON_FLAG_NL);
        icons.put("pt", StockIcons.ICON_FLAG_PT);
        icons.put("pt_BR", StockIcons.ICON_FLAG_BR);
        icons.put("ru", StockIcons.ICON_FLAG_RU);
        icons.put("zh", StockIcons.ICON_FLAG_ZH);
        
		ButtonGroup myGroup = new ButtonGroup();
        addItem("ar", "\u0627\u0644\u0639\u0631\u0628\u064a\u0629", myGroup);
        addItem("de", "Deutsch", myGroup);
        addItem("el", "E\u03bb\u03bb\u03b7\u03bd\u03b9\u03ba\u03ac", myGroup);
        addItem("en", "English", myGroup);
        addItem("eo", "Esperanto", myGroup);
        addItem("es", "Espa\u00f1ol", myGroup);
        addItem("fr", "Fran\u00e7ais", myGroup);
        addItem("hu", "Hungarian", myGroup);
        addItem("ja", "\u65e5\u672c\u8a9e", myGroup);
        addItem("lv", "Latvie\u0161u", myGroup);
        addItem("nl", "Nederlands", myGroup);
        addItem("pt", "Portugu\u00eas", myGroup);
        addItem("pt_BR", "Portugu\u00eas (Brasil)", myGroup);
        addItem("ru", "\u0420\u0443\u0441\u0441\u043a\u0438\u0439", myGroup);
        addItem("zh", "\u4e2d\u6587", myGroup);
        
		pane.add(panel);
		
		FlowLayout btnLayout = new FlowLayout(1);
		btnPanel.setLayout(btnLayout);
		okBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		okBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okButtonClicked();
			}

			private void okButtonClicked() {
				dispose();
			}
		});
		btnPanel.add(okBtn);		
		
		pane.add(btnPanel);
	}

	@Override
	public void setL10nMessages(Locale locale, String languageCode) {
		setTitle(_("Language"));
		okBtn.setText(_("Ok"));
	}

    private final HashMap<String, Icon> icons = new HashMap<String, Icon>();
	
	private void addItem(final String code, String language, ButtonGroup group) {
		final JRadioButton radioItem = new JRadioButton(language, icons.get(code));
		itemsMap.put(code, radioItem);
		
		if (code.equals(code)) {
			radioItem.setSelected(true);
		}
        radioItem.setAction(new AbstractAction(language, icons.get(code)) {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                    I18n.reset(code);
            }
        });
        radioItem.addKeyListener(new KeyAdapter() {
        	@Override
    		public void keyPressed(KeyEvent ke) {
        		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
        			dispose();
        		}
        	}
		});
        
		group.add(radioItem);
		panel.add(radioItem);
	}

}
