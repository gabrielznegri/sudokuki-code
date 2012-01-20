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

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import net.jankenpoi.sudokuki.Version;
import net.jankenpoi.sudokuki.resources.UIResources;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

        private Frame parent;

        public AboutDialog(Frame parent) {
                super(parent, true);
                this.parent = parent;
                initComponents();
                setTitle("About Sudokuki...");
                pack();
        }

        private void initComponents() {
                URI sudokukiURI = null;
                try {
                        sudokukiURI = new URI("http://sudokuki.sourceforge.net");
                } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                }
                URI forumURI = null;
                try {
                        forumURI = new URI(
                                        "http://sourceforge.net/projects/sudokuki/forums");
                } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                }

                JTabbedPane tabbedPane = new JTabbedPane();
                tabbedPane.addTab("Information", null, makeInfoPanel(sudokukiURI),
                                "Information");

                String license_html = null;
                try {
                        license_html = readTextFile("text/COPYING.html");
                } catch (IOException e) {
                }

                tabbedPane
                                .addTab("Contributors",
                                                null,
                                                makeTextPanel(// "   name_of_contributor | translation   <= each line (aligned on '|')"

                                                "<html>"
                                                                + "<table border=\"0\">"
                                                                + "<tr>"
                                                                + "<td align=\"right\"><b>Aritz Beraza</b></td>"
                                                                + "<td> </td>"
                                                                + "<td align=\"left\">Spanish translations (v1.0)</td>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "<td align=\"right\"><b>H&eacute;l&egrave;ne Damianakos</b></td>"
                                                                + "<td> </td>"
                                                                + "<td align=\"left\">Greek translations (v1.0)</td>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "<td align=\"right\"><b>Jeanne Peng</b></td>"
                                                                + "<td> </td>"
                                                                + "<td align=\"left\">Chinese translations</td>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "<td align=\"right\"><b>Claus Poerschke</b></td>"
                                                                + "<td> </td>"
                                                                + "<td align=\"left\">Patches (v1.0) and German translations</td>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "<td align=\"right\"><b>Dmitry Salnikov</b></td>"
                                                                + "<td> </td>"
                                                                + "<td align=\"left\">Portuguese and Russian translations</td>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "<td align=\"right\"><b>H&eacute;l&egrave;ne Vedrenne</b></td>"
                                                                + "<td> </td>"
                                                                + "<td align=\"left\">Japanese translations (v1.0)</td>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "<td align=\"right\"><b>Sylvain Vedrenne</b></td>"
                                                                + "<td> </td>"
                                                                + "<td align=\"left\">Esperanto, French and Japanese translations</td>"
                                                                + "</tr>"
                                                                + "</table>"

                                                                + "</html>"), "Contributors");

                tabbedPane.addTab("Feedback", null, makeFeedbackPanel(forumURI),
                                "Feedback");

                tabbedPane
                                .addTab("Transfer",
                                                null,
                                                makeTextPanel("<html>"
                                                                + "<table border=\"0\">"
                                                                + "<tr>" + "<td> </td>"+ "<td> </td>"
                                                                + "<td>"
                                                                + "<p>"
                                                                + "<b>Sudokuki</b> is released under the terms of the GNU General Public "
                                                                + "License version 3 or later (GPL v3+).<br/>"
                                                                + "</p>"
                                                                + "</td>"
                                                                + "<td> </td>"+ "<td> </td>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "<td> </td>"+ "<td> </td>"
                                                                + "<td>"
                                                                + "<p>"
                                                                + "The full license text is available in the file called COPYING that "
                                                                + "must be included in every copy of the program.<br/>"
                                                                + "</p>"
                                                                + "</td>"
                                                                + "<td> </td>"+ "<td> </td>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "<td> </td>"+ "<td> </td>"
                                                                + "<td>"
                                                                + "<p>"
                                                                + "This program is Free Software (\"Free\" as in \"Freedom\") developped "
                                                                + "during the author's free-time in the hope that some users will find it "
                                                                + "useful, but WITHOUT ANY WARRANTY of any kind.<br/>"
                                                                + "</p>"
                                                                + "</td>"
                                                                + "<td> </td>"+ "<td> </td>"
                                                                + "</tr>"
                                                                + "<td> </td>"+ "<td> </td>"
                                                                + "<td>"
                                                                + "<p>"
                                                                + "You are welcome to transfer this program to other people as long as "
                                                                + "you respect the license terms. Read the GNU General Public License "
                                                                + "for more details.<br/>"
                                                                + "</p>"
                                                                + "</td>"
                                                                + "<td> </td>"+ "<td> </td>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "</tr>"
                                                                + "<tr>"
                                                                + "<td> </td>"+ "<td> </td>"
                                                                + "<td>"
                                                                + "<p>"
                                                                + "Copyright (C) 2007-2012 Sylvain Vedrenne<br/>"
                                                                + "</p>" + "</td>"
                                                                + "<td> <td/>"
                                                                + "</tr>" + "</table>"),
                                                "Transfer"); // tooltip text

                JEditorPane editPane = new JEditorPane("text/html", license_html);
                editPane.setEditable(false);
                editPane.setBackground(Color.WHITE);
                editPane.setCaretPosition(0);
                JScrollPane scrollPane = new JScrollPane(editPane);
                tabbedPane.addTab("License", null, scrollPane, "License");

                Dimension parentDim = parent.getPreferredSize();
                Dimension dim = new Dimension();
                dim.setSize(parentDim.getHeight() * 1.3, parentDim.getWidth() * 1.3);
                tabbedPane.setPreferredSize(dim);
                add(tabbedPane);
                pack();
                setLocationRelativeTo(parent);
        }

        protected JComponent makeTextPanel(String text) {
                JPanel panel = new JPanel(false);
                JLabel label = new JLabel(text);
                label.setHorizontalAlignment(JLabel.CENTER);
                panel.setLayout(new GridLayout(1, 1));
                panel.add(label);
                return panel;
        }

        protected JComponent makeInfoPanel(final URI sudokukiURI) {
                JPanel panel = new JPanel(false);
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JPanel iconPanel = new JPanel(false);
                ImageIcon icon = Images.ICON_APPLICATION_LOGO;
                JLabel iconLabel = new JLabel(icon);
                iconPanel.add(iconLabel);
                panel.add(iconPanel);

                JPanel feedbackPanel = new JPanel(false);
                String feedbackStr = "<html>"
                                + "<table border=\"0\">"
                                + "<tr>"
                                + "<td align=\"center\"><b>"
                                + Version.versionString
                                + "</b></td>"
                                + "</tr>"
                                + "<tr>"
                                + "</tr>"
                                + "<tr>"
                                + "<td align=\"center\">Copyright 2007-2012 Sylvain Vedrenne</td>"
                                + "</tr>" + "</table>" + "</html>";
                JLabel label = new JLabel(feedbackStr);
                feedbackPanel.add(label);

                panel.add(feedbackPanel);

                JPanel linkPanel = new JPanel(false);
                JButton linkButton = new JButton();

                linkButton
                                .setText("<HTML><FONT color=\"#000099\"><U>Sudokuki "+_("on the Web")+"</U></FONT></HTML>");
                linkButton.setHorizontalAlignment(SwingConstants.CENTER);
                linkButton.setBorderPainted(false);
                linkButton.setOpaque(false);
                linkButton.setBackground(Color.WHITE);
                linkButton.setToolTipText(sudokukiURI.toString());
                linkButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                AboutDialog.this.open(sudokukiURI);
                        }
                });
                linkButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                linkPanel.add(linkButton);
                panel.add(linkPanel);

                return panel;
        }

        protected JComponent makeFeedbackPanel(final URI forumURI) {
                JPanel panel = new JPanel(false);
                String feedbackStr = "<html>" + "<table border=\"0\">" + "<tr></tr>"
                                + "<tr></tr>" + "<tr></tr>" + "<tr></tr>" + "<tr>"
                                + "<td>    </td>"+ "<td>    </td>"+"<td>Feel free to request features and report bugs "
                                + "on the Sudokuki Forums hosted by Sourceforge!</td>"+"<td>    </td>"
                                + "</tr>" + "<tr>" + "</tr>" + "</table>" + "</html>";
                JLabel label = new JLabel(feedbackStr);
                label.setHorizontalAlignment(JLabel.CENTER);
                panel.setLayout(new GridLayout(2, 1));
                panel.add(label);

                JPanel linkPanel = new JPanel(false);
                JButton linkButton = new JButton();

                linkButton
                                .setText("<HTML><FONT color=\"#000099\"><U>"+_("Sudokuki Forums")+"</U></FONT></HTML>");
                linkButton.setHorizontalAlignment(SwingConstants.CENTER);
                linkButton.setBorderPainted(false);
                linkButton.setOpaque(false);
                linkButton.setBackground(Color.WHITE);
                linkButton.setToolTipText(forumURI.toString());
                linkButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                AboutDialog.this.open(forumURI);
                        }
                });
                linkButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                linkPanel.add(linkButton);
                panel.add(linkPanel);

                return panel;
        }

        private void open(URI uri) {
                if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                                desktop.browse(uri);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                } else {
                        // TODO: error handling
                }
        }

        private static String readTextFile(String path) throws IOException {

                InputStream is = UIResources.class.getResourceAsStream(path);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                }
                br.close();
                isr.close();
                is.close();
                return sb.toString();
        }

}