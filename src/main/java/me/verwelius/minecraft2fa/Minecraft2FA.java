package me.verwelius.minecraft2fa;

import me.verwelius.minecraft2fa.database.Database;
import me.verwelius.minecraft2fa.database.IDatabase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.logging.Logger;

public final class Minecraft2FA extends JavaPlugin {
    public static final Logger LOGGER = Logger.getLogger("Minecraft2FA");


    @Override
    public void onEnable() {
        // Plugin startup logic

        IDatabase database = new Database(this);
        Map<String, Long> ids = database.getAll();
        ids.keySet().forEach(s -> LOGGER.info(s + " telegram id is " + ids.get(s)));

        Bukkit.getPluginManager().registerEvents(new Listeners(this), this);

        LOGGER.info("Enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LOGGER.info("Disabled!");
    }
}
