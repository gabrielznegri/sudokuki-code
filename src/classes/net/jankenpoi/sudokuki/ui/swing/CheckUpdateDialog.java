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

import java.awt.Container;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import net.jankenpoi.sudokuki.Version;

/**
 * CheckUpdateDialog.java
 * 
 * @author svedrenne
 */
@SuppressWarnings("serial")
public class CheckUpdateDialog extends JDialog {

	private JFrame parent;

	private int isNewVersionAvailable = -1;

	public CheckUpdateDialog(JFrame parent,
			final CheckUpdateAction checkUpdateAction) {
		super(parent, true);
		this.parent = parent;
		initComponents();
		setResizable(false);

		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {

			private boolean isNewVersionFound = false;

			@Override
			protected String doInBackground() throws Exception {
				/* Executed in a background thread */
				String str = getHttpLatestVersionString();

				if (!str.startsWith("Sudokuki")) {
					System.out
							.println("CheckUpdateDialog.CheckUpdateDialog(...).new SwingWorker<String,Void>() {...}.doInBackground() LATEST NOT FOUND!!!");

					// TODO: in this case, consider the download of LATEST has
					// failed!!!
				}

				if (!str.equals(Version.versionString)) {
					isNewVersionFound = true;
				}
				return str;
			}

			@Override
			protected void done() {
				/* Executed in the EDT */
				checkUpdateAction.notifyNewVersionFound(isNewVersionFound);
				dispose();
			}
		};
		worker.execute();
	}

	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		Container pane = getContentPane();
		GridLayout btnLayout = new GridLayout(4, 1);
		pane.setLayout(btnLayout);

		JLabel messageLbl1 = new JLabel(
				"<html>"
				+ "<table border=\"0\">"
				+ "<tr>"
				+ "<td align=\"center\">"
				+"Checking for available updates.</td>"
				+"</tr><html>");
		JLabel messageLbl2 = new JLabel(
				"<html>"
				+ "<table border=\"0\">"
				+ "<tr>"
				+ "<td align=\"center\">"
				+"Please wait...</td>"
				+"</tr><html>");
		JLabel messageLbl3 = new JLabel("");
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		cancelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonClicked();
			}
		});

		pane.add(messageLbl1);
		pane.add(messageLbl2);
		pane.add(messageLbl3);
		pane.add(cancelBtn);

		pack();
		setLocationRelativeTo(parent);
	}

	private void buttonClicked() {
		System.out.println("CheckUpdateDialog.buttonClicked()");
		dispose();
	}

	private String getHttpLatestVersionString() {
		String line = null;
		BufferedReader dis = null;
		try {
			URL url;
			URLConnection urlConn;

			url = new URL(
					"http://sourceforge.net/projects/sudokuki/files/sudokuki/1.1/Beta/LATEST/download");

			// Note: a more portable URL:
			// url = new URL(getCodeBase().toString() +
			// "/ToDoList/ToDoList.txt");

			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setUseCaches(false);

			// BufferedReader d
			// = new BufferedReader(new InputStreamReader(in));

			dis = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			line = dis.readLine();
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out
				.println("SwingApp.getHttpLatestVersionString() line:" + line);
		String versionString = "";
		if (line != null) {
			// line.startsWith("Sudokuki ");
			// line.endsWith(") is the latest version.");
			String[] strs = line.split(" is the latest version.");
			if (strs.length >= 1) {
				versionString = strs[0];
			}
		}
		System.out
				.println("SwingApp.getHttpLatestVersionString() Version.versionString:"
						+ Version.versionString);
		System.out
				.println("SwingApp.getHttpLatestVersionString() versionString:"
						+ versionString);

		if (versionString.equals(Version.versionString)) {
			System.out
					.println("SwingApp.getHttpLatestVersionString() This version is up-to-date");
			isNewVersionAvailable = 0;
		} else {
			System.out
					.println("SwingApp.getHttpLatestVersionString() This version is outdated. Please download the latest version from Sourceforge.");
			isNewVersionAvailable = 1;
		}
		return versionString;
	}

	public int getResult() {
		return isNewVersionAvailable;
	}
	
}
