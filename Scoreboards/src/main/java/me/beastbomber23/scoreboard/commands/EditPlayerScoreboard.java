package me.beastbomber23.scoreboard.commands;

import me.beastbomber23.scoreboard.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditPlayerScoreboard implements CommandExecutor, Listener {

    private Inventory gui;
    private Main main;

    public EditPlayerScoreboard(Main main)
    {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player ply = (Player) sender;

        if(ply.hasPermission("scoreboards.edit"))
        {
            openEditGui((Player) sender);
        }

        return false;
    }

    public void openEditGui(Player p)
    {

        gui = Bukkit.createInventory(null, 36, ChatColor.DARK_PURPLE + "Scoreboard Customization");

        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();

        for(int i = 9; i < 18; i++)
        {

            int slotNum = i - 8;

            meta.setDisplayName(String.valueOf(slotNum));
            item.setItemMeta(meta);
            gui.setItem(i, item);
        }

        ItemStack item1 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setDisplayName("Section");
        item1.setItemMeta(meta1);
        gui.setItem(18, item1);

        ItemStack item2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("Online");
        item2.setItemMeta(meta2);
        gui.setItem(19, item2);

        ItemStack item3 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta3 = item3.getItemMeta();
        meta3.setDisplayName("Mob Kills");
        item3.setItemMeta(meta3);
        gui.setItem(20, item3);

        ItemStack item4 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta4 = item4.getItemMeta();
        meta4.setDisplayName("Player Kills");
        item4.setItemMeta(meta4);
        gui.setItem(21, item4);

        ItemStack item5 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta5 = item5.getItemMeta();
        meta5.setDisplayName("Deaths");
        item5.setItemMeta(meta5);
        gui.setItem(22, item5);

        ItemStack item6 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta6 = item6.getItemMeta();
        meta6.setDisplayName("Distance Traveled");
        item6.setItemMeta(meta6);
        gui.setItem(23, item6);

        ItemStack item7 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta7 = item2.getItemMeta();
        meta7.setDisplayName("Play Time");
        item7.setItemMeta(meta7);
        gui.setItem(24, item7);

        ItemStack item8 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta8 = item8.getItemMeta();
        meta8.setDisplayName("Username");
        item8.setItemMeta(meta8);
        gui.setItem(25, item8);

        ItemStack item9 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta9 = item9.getItemMeta();
        meta9.setDisplayName("Blocks Broken");
        item9.setItemMeta(meta9);
        gui.setItem(26, item9);

        ItemStack item10 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta10 = item2.getItemMeta();
        meta10.setDisplayName("Jumps");
        item10.setItemMeta(meta10);
        gui.setItem(27, item10);

        p.openInventory(gui);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent event)
    {
        if(!event.getInventory().equals(gui))
        {
            return;
        }

        if(event.getSlot() >= 9 && event.getSlot() <= 17)
        {
            event.setCancelled(true);
            return;
        }

        if(event.getSlot() > 36)
        {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void guiCloseEvent(InventoryCloseEvent event)
    {
        if(!event.getInventory().equals(gui))
        {
            return;
        }

        Integer[] ids = new Integer[9];

        for (int i = 0; i < 9; i++)
        {
            ItemStack item = event.getInventory().getItem(i);

            if(item != null)
            {
                ItemMeta meta = item.getItemMeta();
                String name = meta.getDisplayName();

                switch (name)
                {

                    case "Username":
                    {
                        ids[i] = 0;
                        break;
                    }

                    case "Online":
                    {
                        ids[i] = 1;
                        break;
                    }

                    case "Play Time":
                    {
                        ids[i] = 2;
                        break;
                    }

                    case "Distance Traveled":
                    {
                        ids[i] = 3;
                        break;
                    }

                    case "Deaths":
                    {
                        ids[i] = 4;
                        break;
                    }

                    case "Player Kills":
                    {
                        ids[i] = 5;
                        break;
                    }

                    case "Mob Kills":
                    {
                        ids[i] = 6;
                        break;
                    }

                    case "Blocks Broken":
                    {
                        ids[i] = 7;
                        break;
                    }

                    case "Jumps":
                    {
                        ids[i] = 8;
                        break;
                    }

                    case "Section":
                    {
                        ids[i] = 9;
                        break;
                    }
                }

                main.scoreboardIds.put(event.getPlayer().getUniqueId().toString(), ids);

            }
        }

    }

}
