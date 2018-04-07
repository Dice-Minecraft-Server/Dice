package me.sheiun.dice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author SheiUn
 */
public class Dice extends JavaPlugin {

    public Map<UUID, Dicer> dicers = new HashMap<UUID, Dicer>();
    public PlayerListener pListener;

    @Override
    public void onEnable() {
        pListener = new PlayerListener(this);

        saveData();

        loadData();

        getServer().getPluginManager().registerEvents(pListener, this);

        getLogger().log(Level.INFO, "啟用!");
    }

    @Override
    public void onDisable() {

        getLogger().log(Level.INFO, "停用!");
    }

    public void saveData() {
        // save Dicers data to data.yml

    }

    public void loadData() {
        YamlConfiguration config = new YamlConfiguration();
        File dataFile = new File(this.getDataFolder(), "data.yml");
        try {
            config.load(dataFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dicer getDicer(UUID id) {
        Dicer dicer = null;
        if (dicers.containsKey(id)) {
            dicer = dicers.get(id);
        }
        if (dicer == null) {
            dicer = new Dicer(this);
            dicer.id = id;
            if (dicer.loadData() == true) {
                dicers.put(id, dicer);
            }
        }
        return dicer;
    }
}
