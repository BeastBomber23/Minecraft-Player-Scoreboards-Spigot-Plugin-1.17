package me.beastbomber23.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinSetup implements Listener {

    private Main main;

    public FirstJoinSetup(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player ply = event.getPlayer();

        if(!main.scoreboardIds.containsKey(ply.getUniqueId().toString()))
        {
            if(main.scoreboardIds.containsKey("default"))
            {
                main.defaulters.add(ply.getUniqueId().toString());
            }
            else
            {
                main.print("Default not set. Cannot set player default scoreboard.");
            }
        }
    }

}
