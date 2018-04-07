package me.sheiun.dice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author SheiUn
 */
public class Dicer {

    UUID id;
    private int diceInactivated = 0;
    private int dicePoint = 0;
    private int diceActivated = 0;
    Dice plugin;

    public Dicer(Dice newPlugin) {
        plugin = newPlugin;
    }

    public int getDiceInactivated() {
        return diceInactivated;
    }

    public void setDiceInactivated(int diceInactivated) {
        this.diceInactivated = diceInactivated;
    }

    public int getDicePoint() {
        return dicePoint;
    }

    public void setDicePoint(int dicePoint) {
        this.dicePoint = dicePoint;
    }

    public int getDiceActivated() {
        return diceActivated;
    }

    public void setDiceActivated(int diceActivated) {
        this.diceActivated = diceActivated;
    }

    public Player getPlayer() {
        return Bukkit.getServer().getPlayer(id);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getServer().getOfflinePlayer(id);
    }

    public boolean loadData() {
        FileConfiguration data = new YamlConfiguration();
        try {
            File dataFile = new File(plugin.getDataFolder(), "data" + File.separator + id.toString() + ".yml");
            if (dataFile.exists() == false) {
                OfflinePlayer p = getOfflinePlayer();
                dataFile = new File(plugin.getDataFolder(), "data" + File.separator + p.getName() + ".yml");
                if (dataFile.exists() == false) {
                    return false;
                }
            }
            data.load(dataFile);
        } catch (IOException e) {
            return false;
        } catch (InvalidConfigurationException e) {
            return false;
        }
        //hardClear(); //²M±¼Map
        return true;
    }

    public void saveData() {
        FileConfiguration data = new YamlConfiguration();
        // data processing
        try {
            data.save(new File(plugin.getDataFolder(), "data" + File.separator + id + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
