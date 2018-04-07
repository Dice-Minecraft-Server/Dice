package me.sheiun.dice;

import java.io.File;
import java.lang.reflect.Constructor;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

/**
 *
 * @author SheiUn
 */
public class PlayerListener implements Listener {

    final Dice plugin;

    public PlayerListener(Dice newPlugin) {
        plugin = newPlugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Dicer dicer = new Dicer(plugin);
        dicer.id = event.getPlayer().getUniqueId();
        
        /* 建立 Dicer 資料 */
        if (new File(plugin.getDataFolder(), "data" + File.separator + dicer.id + ".yml").exists()) {
            dicer.loadData(); //有資料就讀取
        } else {
            dicer.saveData(); //否則用save建立
        }
        plugin.dicers.put(event.getPlayer().getUniqueId(), dicer);

        /* 顯示文字 */
        sendTitle(event.getPlayer(), "測試", 1, 5, 1, ChatColor.BLUE);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Dicer dicer = plugin.getDicer(event.getPlayer().getUniqueId());
        dicer.saveData();
        plugin.dicers.remove(dicer.id);
    }

    //GUI 介面
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemSwapEvent(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        Dicer dicer = (Dicer) player;
        if (player.isSneaking()) {
            event.setCancelled(true);
            Inventory invntr = getServer().createInventory(null, 27, ChatColor.BLUE + "選單");

            ItemStack diceInactivated = new ItemStack(Material.WOOL, dicer.getDiceInactivated(), (byte) 1);
            diceInactivated.setData(new Wool(DyeColor.BLUE));
            player.openInventory(invntr);
        }
    }

    //Dice 撿起判定
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (event.getItem().getItemStack().getType() == Material.WOOL) {
            event.getPlayer().chat("撿到羊毛了");
            event.getItem().remove();
        }
    }

    //Dice 死亡
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
    }

    /**
     * Send a title to player
     *
     * @param player Player to send the title to
     * @param text The text displayed in the title
     * @param fadeInTime The time the title takes to fade in
     * @param showTime The time the title is displayed
     * @param fadeOutTime The time the title takes to fade out
     * @param color The color of the title
     */
    public void sendTitle(Player player, String text, int fadeInTime, int showTime, int fadeOutTime, ChatColor color) {
        try {
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");

            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle, fadeInTime, showTime, fadeOutTime);

            sendPacket(player, packet);
        } catch (Exception ex) {
            //Do something
        }
    }

    private void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception ex) {
            //Do something
        }
    }

    /**
     * Get NMS class using reflection
     *
     * @param name Name of the class
     * @return Class
     */
    private Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server" + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException ex) {
            //Do something
        }
        return null;
    }

}
