/*
 * Project: WMH-Tools
 * Package: de.sirmythos.wmh_tools.handler
 * File:	Handler_Player.java
 *
 * Date:	02.09.2016
 * Time:	11:40:50
 * 
 * @author 	SirMythos
 */
package de.sirmythos.wmh_tools.handler;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import de.sirmythos.dbhandler.DBHandler;
import de.sirmythos.dbhandler.Database;
import de.sirmythos.wmh_tools.dbobjects.Player;
import de.sirmythos.wmh_tools.enums.PlayerSort;
import de.sirmythos.wmh_tools.gui.Dialog_EditPlayer;

// TODO: Auto-generated Javadoc
/**
 * The Class Handler_Player.
 */
public class Handler_Player {

	/**
	 * Creates the player.
	 *
	 * @param f
	 *            the f
	 * @return the player
	 */
	public static Player createPlayer(JFrame f) {
		Player p = callPlayerDialog(f, "New Player", null);
		if (p != null) {
			p = DBHandler.insertObject(p, Database.DynamicDB);
		}
		return p;
	}

	/**
	 * Delete player.
	 *
	 * @param player
	 *            the player
	 * @param sort
	 *            the sort
	 * @return the player[]
	 */
	public static Player[] deletePlayer(Player player, PlayerSort sort) {
		DBHandler.removeObject(player, Database.DynamicDB);
		return loadPlayers(sort);
	}

	/**
	 * Edits the player.
	 *
	 * @param f
	 *            the f
	 * @param p
	 *            the p
	 * @return the player
	 */
	public static Player editPlayer(JFrame f, Player p) {
		p = callPlayerDialog(f, "Edit Player", p);
		DBHandler.updateObject(p, Database.DynamicDB);
		return p;
	}

	/**
	 * Load players.
	 *
	 * @param sort
	 *            the sort
	 * @return the player[]
	 */
	public static Player[] loadPlayers(PlayerSort sort) {
		List<Player> listPlayer = DBHandler.getObjects(Player.class, Database.DynamicDB);
		Player[] players = new Player[listPlayer.size()];
		for (int i = 0; i < players.length; i++) {
			players[i] = listPlayer.get(i);
		}
		switch (sort) {
		case NAME:
			Arrays.sort(players, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
			break;
		case NICKNAME:

			Arrays.sort(players, (a, b) -> a.getNickname().compareToIgnoreCase(b.getNickname()));
			break;
		case SURNAME:
			Arrays.sort(players, (a, b) -> a.getSurname().compareToIgnoreCase(b.getSurname()));
			break;
		}
		return players;
	}

	/**
	 * Call player dialog.
	 *
	 * @param f
	 *            the f
	 * @param title
	 *            the title
	 * @param p
	 *            the p
	 * @return the player
	 */
	private static Player callPlayerDialog(JFrame f, String title, Player p) {
		Dialog_EditPlayer dialog = new Dialog_EditPlayer(f, title, p);
		dialog.setVisible(true);
		p = dialog.getPlayerData();
		dialog.dispose();
		return p;
	}

	/**
	 * Creates the new player object.
	 *
	 * @return the player
	 */
	public static Player createNewPlayerObject() {
		return DBHandler.createObject(Player.class, Database.DynamicDB);
	}

}
