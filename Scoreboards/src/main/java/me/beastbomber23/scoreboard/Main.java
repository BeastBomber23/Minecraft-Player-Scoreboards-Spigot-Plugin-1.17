package me.beastbomber23.scoreboard;

import me.beastbomber23.scoreboard.commands.EditPlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin implements Listener {

    public Map<String, Integer> brokenBlocks = new HashMap<String, Integer>();
    public Map<String, Integer[]> scoreboardIds = new HashMap<String, Integer[]>();

    @Override
    public void onEnable()
    {

        BeastScoreboard bScoreboard = new BeastScoreboard(this);
        EditPlayerScoreboard editPScoreboard = new EditPlayerScoreboard(this);

        getServer().getPluginManager().registerEvents(bScoreboard, this);
        getServer().getPluginManager().registerEvents(editPScoreboard, this);

        getServer().getPluginCommand("editpscoreboard").setExecutor(editPScoreboard);

        this.saveDefaultConfig();

        if(this.getConfig().contains("data"))
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
            this.getConfig().set("data.scoreboards." + entry.getKey(), entry.getValue());
        }

        for(Map.Entry<String, Integer> entry : brokenBlocks.entrySet())
        {
            this.getConfig().set("data.stats." + entry.getKey(), entry.getValue());
        }
        this.saveConfig();
    }

    public void restore()
    {
        if(this.getConfig().getConfigurationSection("data.scoreboards") != null)
        {
            this.getConfig().getConfigurationSection("data.scoreboards").getKeys(false).forEach(key ->{
                @SuppressWarnings("unchecked")
                Integer[] content = ((List<Integer>) this.getConfig().get("data.scoreboards." + key)).toArray(new Integer[0]);
                scoreboardIds.put(key, content);
            });
        }

        if(this.getConfig().getConfigurationSection("data.stats") != null)
        {
            this.getConfig().getConfigurationSection("data.stats").getKeys(false).forEach(key ->{
                int blockBroken = this.getConfig().getInt("data.stats." + key);
                brokenBlocks.put(key, blockBroken);
            });
        }

    }

}
