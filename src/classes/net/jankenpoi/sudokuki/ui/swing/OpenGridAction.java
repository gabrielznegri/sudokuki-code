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

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import net.jankenpoi.sudokuki.view.GridView;

@SuppressWarnings("serial")
public class OpenGridAction extends AbstractAction {

	private final GridView view;
	private final ActionsRepository actionsRepo;

	private final JFrame frame;
	
	OpenGridAction(JFrame frame, GridView view, ActionsRepository actions) {
		this.frame = frame;
		this.view = view;
		this.actionsRepo = actions;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		final JFileChooser fc = new JFileChooser();
		
		fc.setDialogTitle(_("Open grid..."));
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileFilter() {
			
			public String getExtension(File f) {
		        String ext = null;
		        String s = f.getName();
		        int i = s.lastIndexOf('.');

		        if (i > 0 &&  i < s.length() - 1) {
		            ext = s.substring(i+1).toLowerCase();
		        }
		        return ext;
		    }
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return _("Sudokuki grid files");
			}
			
			@Override
			public boolean accept(File f) {
				String extension = getExtension(f);
				System.out
						.println("OpenGridAction ext:"+extension+"|");
				if (f.isDirectory() || "skg".equals(extension)) {
					return true;
				}
				return false;
			}
		});
		int returnVal = fc.showOpenDialog(frame);
		
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File fileToOpen = fc.getSelectedFile();
		if (fileToOpen == null) {
			return;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileToOpen);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (fis == null) {
            JOptionPane.showMessageDialog(frame, "<html>"
                    + "<table border=\"0\">" + "<tr>"
                    + "File not found:" + "</tr>"
                    + "<tr>" + fileToOpen + "</tr>"
                    + "</html>", "Sudokuki", JOptionPane.PLAIN_MESSAGE);
            return;
		}
		
		short[] externalCellInfos = new short[81];
		for (int i=0; i<81; i++) {
			try {
				int lo = fis.read();
				int hi = fis.read();
//				System.out.println("OpenGridAction.actionPerformed() hi:"+hi+" lo:"+lo);
				short together = (short) (hi << 8 | lo);

				externalCellInfos[i] = (short) together;
//				System.out.println("OpenGridAction.actionPerformed() externalCellInfos["+i+"]:"+externalCellInfos[i]);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		view.getController().notifyResetGridFromShorts(externalCellInfos);
		
		try {
			fis.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
}
