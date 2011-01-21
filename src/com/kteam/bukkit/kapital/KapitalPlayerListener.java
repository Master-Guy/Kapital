package com.kteam.bukkit.kapital;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

import com.kteam.bukkit.utils.Misc;

/**
 * Kapital Player Listener
 * 
 * @author Ant59 and Master-Guy
 */

public class KapitalPlayerListener extends PlayerListener {
	public static final Logger log = Logger.getLogger("Minecraft"); // Logger
    private final KapitalSettings Settings = new KapitalSettings();
    
    private final Kapital plugin;
    //private final Tile Tile;

    public KapitalPlayerListener(Kapital plugin) {
        this.plugin = plugin;
        //Tile = new Tile(plugin);
    }
    
    	// Old code
    	/*if (split[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "buyPlotCommand", "/buyTile")[0])) {
    		event.getPlayer().sendMessage("Executing buyPlot");
    		Tile.buyTile(event);
    		event.setCancelled(true);
    	}
    	else if (split[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "showPlotXZ", "/tileXZ")[0])) {
    		event.getPlayer().sendMessage("Executing plotXZ");
    		Tile.displayTileXZ(event);
    		event.setCancelled(true);
    	}*/
    
    @Override
	public void onPlayerCommand(PlayerChatEvent event)
	{
		if (event.isCancelled()) return;
		
		Player player = event.getPlayer();
		String[] sects = event.getMessage().split(" +", 2);
		String[] args = (sects.length > 1 ? sects[1].split(" +") : new String[0]);
		Commands cmd;
		try
		{
			cmd = Commands.valueOf(sects[0].substring(1).toUpperCase()); 
		}
		catch (Exception ex)
		{
			return;
		}

		try
		{
			switch (cmd)
			{
			case MSGALL:
				String msg = Misc.arrayToString(args, " ");
				plugin.getKapitalWorld().msgAll(msg);
				break;

			default:
				return;
			}
		}
		catch (NoSuchMethodError ex)
		{
			// Running an old version of CraftBukkit that does not support any methods used.
			plugin.msg(player, "The server is not recent enough to support " + sects[0].toLowerCase() + ".");
		}
		catch (Exception ex)
		{
			// Unexpected error encountered.  Tell the user.  Can be thrown on purpose to notify the user of syntax errors and such.
			plugin.msg(player, ChatColor.RED + "Error: " + ex.getMessage());
		}
		
		plugin.consoleLog(String.format("%1$s issued command: %2$s", player.getName(), event.getMessage()));
		event.setCancelled(true); // Prevent other plugins from processing the command.
	}

	// A list of all supported commands, case-insensitive.
	private enum Commands
	{
		MSGALL
	}
}
