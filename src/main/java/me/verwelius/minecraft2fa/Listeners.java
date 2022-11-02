package me.verwelius.minecraft2fa;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

public class Listeners implements Listener {

    private final Plugin plugin;

    public Listeners(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String IP = event.getRealAddress().getHostAddress();
        Minecraft2FA.LOGGER.info(String.format("%s (%s) joined with ip %s", player.getName(), player.getUniqueId(), IP));
    }


}