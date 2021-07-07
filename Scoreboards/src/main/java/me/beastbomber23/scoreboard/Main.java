package me.beastbomber23.scoreboard;

import me.beastbomber23.scoreboard.commands.EditDefaultScoreboard;
import me.beastbomber23.scoreboard.commands.EditPlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin implements Listener {

    public Map<String, Integer> brokenBlocks = new HashMap<String, Integer>();
    public Map<String, Integer[]> scoreboardIds = new HashMap<String, Integer[]>();
    public List<String> defaulters = new ArrayList<>();

    public PlayerConfig pData;

    @Override
    public void onEnable()
    {

        BeastScoreboard bScoreboard = new BeastScoreboard(this);
        EditPlayerScoreboard editPScoreboard = new EditPlayerScoreboard(this);
        EditDefaultScoreboard editDScoreboard = new EditDefaultScoreboard(this);
        FirstJoinSetup fjSetup = new FirstJoinSetup(this);

        getServer().getPluginManager().registerEvents(bScoreboard, this);
        getServer().getPluginManager().registerEvents(editPScoreboard, this);
        getServer().getPluginManager().registerEvents(editDScoreboard, this);
        getServer().getPluginManager().registerEvents(fjSetup, this);

        getServer().getPluginCommand("editpscoreboard").setExecutor(editPScoreboard);
        getServer().getPluginCommand("setdefaultscoreboard").setExecutor(editDScoreboard);

        this.pData = new PlayerConfig(this);

        this.pData.saveDefaultConfig();

        if(this.pData.getConfig().contains("data"))
        {
            restore();
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                bScoreboard.updateScoreboard();
            }
        }, 0, 20);

    }

    @Override
    public void onDisable()
    {
        if(!brokenBlocks.isEmpty() || !scoreboardIds.isEmpty())
        {
            save();
        }
    }

    public void save()
    {
        for(Map.Entry<String, Integer[]> entry : scoreboardIds.entrySet())
        {
            this.pData.getConfig().set("data.scoreboards." + entry.getKey(), entry.getValue());
        }

        for(Map.Entry<String, Integer> entry : brokenBlocks.entrySet())
        {
            this.pData.getConfig().set("data.stats." + entry.getKey(), entry.getValue());
        }

        for(String uuid : defaulters)
        {
            this.pData.getConfig().set("data.defaulters." + uuid, true);
        }

        this.pData.saveConfig();
    }

    public void print(String out)
    {
        System.out.println("<Player Scoreboards> " + out);
    }

    public void restore()
    {
        if(this.pData.getConfig().getConfigurationSection("data.scoreboards") != null)
        {
            this.pData.getConfig().getConfigurationSection("data.scoreboards").getKeys(false).forEach(key ->{
                @SuppressWarnings("unchecked")
                Integer[] content = ((List<Integer>) this.pData.getConfig().get("data.scoreboards." + key)).toArray(new Integer[0]);
                scoreboardIds.put(key, content);
            });
        }

        if(this.pData.getConfig().getConfigurationSection("data.stats") != null)
        {
            this.pData.getConfig().getConfigurationSection("data.stats").getKeys(false).forEach(key ->{
                int blockBroken = this.pData.getConfig().getInt("data.stats." + key);
                brokenBlocks.put(key, blockBroken);
            });
        }

        if(this.pData.getConfig().getConfigurationSection("data.defaulters") != null)
        {
            ConfigurationSection sec = getConfig().getConfigurationSection("data.defaulters");

            for(String key : sec.getKeys(false)){
                defaulters.add(pData.getConfig().getString(key));
            }

        }

    }

}
