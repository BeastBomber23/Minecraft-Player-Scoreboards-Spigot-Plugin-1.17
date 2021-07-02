package me.beastbomber23.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class BeastScoreboard implements Listener {

    private Main main;

    public BeastScoreboard(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        createBoard(event.getPlayer());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player p = event.getPlayer();

        if(main.brokenBlocks.get(p.getUniqueId().toString()) != null)
        {
            int current = main.brokenBlocks.get(p.getUniqueId().toString());
            main.brokenBlocks.put(p.getUniqueId().toString(), current + 1);
        }
        else
        {
            main.brokenBlocks.put(p.getUniqueId().toString(), 1);
        }

    }

    public void updateScoreboard()
    {
        if(!Bukkit.getOnlinePlayers().isEmpty())
            for(Player online : Bukkit.getOnlinePlayers())
                createBoard(online);
    }

    public int getDistanceTraveled(Player ply) //All 1 cm statistics except for fall and climb added together.
    {
        int Aviate = ply.getStatistic(Statistic.AVIATE_ONE_CM);
        int Crouch = ply.getStatistic(Statistic.CROUCH_ONE_CM);
        int Fly = ply.getStatistic(Statistic.FLY_ONE_CM);
        int Horse = ply.getStatistic(Statistic.HORSE_ONE_CM);
        int Minecart = ply.getStatistic(Statistic.MINECART_ONE_CM);
        int Pig = ply.getStatistic(Statistic.PIG_ONE_CM);
        int Sprint = ply.getStatistic(Statistic.SPRINT_ONE_CM);
        int Strider = ply.getStatistic(Statistic.STRIDER_ONE_CM);
        int Swim = ply.getStatistic(Statistic.SWIM_ONE_CM);
        int WalkOnWater = ply.getStatistic(Statistic.WALK_ON_WATER_ONE_CM);
        int Walk = ply.getStatistic(Statistic.WALK_ONE_CM);
        int WalkUnderWater = ply.getStatistic(Statistic.WALK_UNDER_WATER_ONE_CM);

        return Aviate + Crouch + Fly + Horse + Minecart + Pig + Sprint + Strider + Swim + WalkOnWater + Walk + WalkUnderWater;

    }

    public String distanceShorten(int distance)
    {
        float meter = distance / 100;
        float kilometer = distance / 100000;

        if(meter < 1)
        {
            return distance + "Cm";
        }
        else if(kilometer < 1)
        {
            return meter + "M";
        }
        else
        {
            return kilometer + "Km";
        }

    }

    public String timeShorten(int ticks)
    {

        DecimalFormat df = new DecimalFormat("0.00");

        float seconds = ticks / 20;
        float minutes = seconds / 60;
        float hours = minutes / 60;
        float days = hours / 24;
        float weeks = days / 7;
        float years = days / 356;

        if(hours < 1)
        {
            return df.format(minutes) + " minutes";
        }
        else if(days < 1)
        {
            return df.format(hours) + " hours";
        }
        else if(weeks < 1)
        {
            return df.format(days) + " days";
        }
        else if(years < 1)
        {
            return df.format(weeks) + " weeks";
        }
        else
        {
            return df.format(years) + " years";
        }

    }

    public ChatColor getChatColor(String type, Player ply) //Red = Terrible, Yellow = Eh, Green = Good, Light Purple = Advanced
    {
        switch (type)
        {
            case "online": {
                if(Bukkit.getOnlinePlayers().size() >= 0 && Bukkit.getOnlinePlayers().size() <= 2)
                {
                    return ChatColor.RED;
                }
                else if(Bukkit.getOnlinePlayers().size() >= 3 && Bukkit.getOnlinePlayers().size() <= 10)
                {
                    return ChatColor.YELLOW;
                }
                else if(Bukkit.getOnlinePlayers().size() >= 11 && Bukkit.getOnlinePlayers().size() <= 100)
                {
                    return ChatColor.GREEN;
                }
                else if(Bukkit.getOnlinePlayers().size() >= 101)
                {
                    return ChatColor.LIGHT_PURPLE;
                }
            }

            case "killsM": {
                if(ply.getStatistic(Statistic.MOB_KILLS) >= 0 && ply.getStatistic(Statistic.MOB_KILLS) <= 25)
                {
                    return ChatColor.RED;
                }
                else if(ply.getStatistic(Statistic.MOB_KILLS) >= 26 && ply.getStatistic(Statistic.MOB_KILLS) <= 100)
                {
                    return ChatColor.YELLOW;
                }
                else if(ply.getStatistic(Statistic.MOB_KILLS) >= 101 && ply.getStatistic(Statistic.MOB_KILLS) <= 1000)
                {
                    return ChatColor.GREEN;
                }
                else if(ply.getStatistic(Statistic.MOB_KILLS) >= 1001)
                {
                    return ChatColor.LIGHT_PURPLE;
                }
            }

            case "killsP": {
                if(ply.getStatistic(Statistic.PLAYER_KILLS) >= 0 && ply.getStatistic(Statistic.PLAYER_KILLS) <= 5)
                {
                    return ChatColor.RED;
                }
                else if(ply.getStatistic(Statistic.PLAYER_KILLS) >= 6 && ply.getStatistic(Statistic.PLAYER_KILLS) <= 25)
                {
                    return ChatColor.YELLOW;
                }
                else if(ply.getStatistic(Statistic.PLAYER_KILLS) >= 26 && ply.getStatistic(Statistic.PLAYER_KILLS) <= 500)
                {
                    return ChatColor.GREEN;
                }
                else if(ply.getStatistic(Statistic.PLAYER_KILLS) >= 501)
                {
                    return ChatColor.LIGHT_PURPLE;
                }
            }

            case "deaths": {
                if(ply.getStatistic(Statistic.DEATHS) >= 0 && ply.getStatistic(Statistic.DEATHS) <= 1)
                {
                    return ChatColor.LIGHT_PURPLE;
                }
                else if(ply.getStatistic(Statistic.DEATHS) >= 2 && ply.getStatistic(Statistic.DEATHS) <= 10)
                {
                    return ChatColor.GREEN;
                }
                else if(ply.getStatistic(Statistic.DEATHS) >= 11 && ply.getStatistic(Statistic.DEATHS) <= 25)
                {
                    return ChatColor.YELLOW;
                }
                else if(ply.getStatistic(Statistic.DEATHS) >= 26)
                {
                    return ChatColor.RED;
                }

            }

            case "traveled": {

                int distance = getDistanceTraveled(ply);

                if(distance >= 0 && distance <= 8000) //0 - 500 blocks
                {
                    return ChatColor.RED;
                }
                else if(distance >= 8001 && distance <= 80000) //500 - 5000 blocks
                {
                    return ChatColor.YELLOW;
                }
                else if(distance >= 80001 && distance <= 320000)
                {
                    return ChatColor.GREEN;
                }
                else if(distance >= 320001)
                {
                    return ChatColor.LIGHT_PURPLE;
                }
            }

            case "playTime": {

                if(ply.getStatistic(Statistic.PLAY_ONE_MINUTE) >= 0 && ply.getStatistic(Statistic.PLAY_ONE_MINUTE) <= 72000)
                {
                    return ChatColor.RED;
                }
                else if(ply.getStatistic(Statistic.PLAY_ONE_MINUTE) >= 7201 && ply.getStatistic(Statistic.PLAY_ONE_MINUTE) <= 576000)
                {
                    return ChatColor.YELLOW;
                }
                else if(ply.getStatistic(Statistic.PLAY_ONE_MINUTE) >= 576001 && ply.getStatistic(Statistic.PLAY_ONE_MINUTE) <= 51840000)
                {
                    return ChatColor.GREEN;
                }
                else if(ply.getStatistic(Statistic.PLAY_ONE_MINUTE) >= 51840001)
                {
                    return ChatColor.LIGHT_PURPLE;
                }

            }

            case "jumps": {

                if(ply.getStatistic(Statistic.JUMP) >= 0 && ply.getStatistic(Statistic.JUMP) <= 250)
                {
                    return ChatColor.RED;
                }
                else if(ply.getStatistic(Statistic.JUMP) >= 251 && ply.getStatistic(Statistic.JUMP) <= 2500)
                {
                    return ChatColor.YELLOW;
                }
                else if(ply.getStatistic(Statistic.JUMP) >= 2501 && ply.getStatistic(Statistic.JUMP) <= 50000)
                {
                    return ChatColor.GREEN;
                }
                else if(ply.getStatistic(Statistic.JUMP) >= 50001)
                {
                    return ChatColor.LIGHT_PURPLE;
                }

            }

            case "brokenBlocks": {

                int brokenBlocks = main.brokenBlocks.get(ply.getUniqueId().toString());

                if(brokenBlocks >= 0 && brokenBlocks <= 500)
                {
                    return ChatColor.RED;
                }
                else if(ply.getStatistic(Statistic.JUMP) >= 501 && ply.getStatistic(Statistic.JUMP) <= 5000)
                {
                    return ChatColor.YELLOW;
                }
                else if(ply.getStatistic(Statistic.JUMP) >= 5001 && ply.getStatistic(Statistic.JUMP) <= 500000)
                {
                    return ChatColor.GREEN;
                }
                else if(ply.getStatistic(Statistic.JUMP) >= 500001)
                {
                    return ChatColor.LIGHT_PURPLE;
                }

            }
        }

        return ChatColor.GRAY;

    }

    public void addToBoard(Player player, Objective obj, Integer id, Integer slot)
    {

        if(id != null) {

            switch (id) {
                case 0: {
                    Score score = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&9&l<< &c&l" + player.getDisplayName() + " &9&l>>"));
                    score.setScore(slot);
                    break;
                }

                case 1: {
                    Score score = obj.getScore(ChatColor.GOLD + "Online Players: " + getChatColor("online", player) + Bukkit.getOnlinePlayers().size());
                    score.setScore(slot);
                    break;
                }

                case 2: {
                    Score score = obj.getScore(ChatColor.GOLD + "Play Time: " + getChatColor("playTime", player) + timeShorten(player.getStatistic(Statistic.PLAY_ONE_MINUTE)));
                    score.setScore(slot);
                    break;
                }

                case 3: {
                    int distanceTraveled = getDistanceTraveled(player);
                    Score score = obj.getScore(ChatColor.GOLD + "Distance Traveled: " + getChatColor("traveled", player) + distanceShorten(distanceTraveled));
                    score.setScore(slot);
                    break;
                }

                case 4: {
                    Score score = obj.getScore(ChatColor.GOLD + "Deaths: " + getChatColor("deaths", player) + player.getStatistic(Statistic.DEATHS));
                    score.setScore(slot);
                    break;
                }

                case 5: {
                    Score score = obj.getScore(ChatColor.GOLD + "Player Kills: " + getChatColor("killsP", player) + player.getStatistic(Statistic.PLAYER_KILLS));
                    score.setScore(slot);
                    break;
                }

                case 6: {
                    Score score = obj.getScore(ChatColor.GOLD + "Mob Kills: " + getChatColor("killsM", player) + player.getStatistic(Statistic.MOB_KILLS));
                    score.setScore(slot);
                    break;
                }

                case 7: {
                    Score score = obj.getScore(ChatColor.GOLD + "Blocks Broken: " + getChatColor("brokenBlocks", player) + main.brokenBlocks.get(player.getUniqueId().toString()));
                    score.setScore(slot);
                    break;
                }

                case 8: {
                    Score score = obj.getScore(ChatColor.GOLD + "Jumps: " + getChatColor("jumps", player) + player.getStatistic(Statistic.JUMP));
                    score.setScore(slot);
                    break;
                }

                case 9: {
                    String lengthName = "<< " + player.getDisplayName() + " >>";
                    String repeated = new String(new char[lengthName.length() - 1]).replace("\0", "≡");

                    Score score = obj.getScore(ChatColor.AQUA + repeated);
                    score.setScore(slot);
                    break;
                }
            }
        }
    }


    public void createBoard(Player player)
    {

        Integer[] ids = main.scoreboardIds.get(player.getUniqueId().toString());

        if(ids != null)
        {

            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();

            //Objectives
            Objective obj = board.registerNewObjective("Scoreboard-1", "dummy", " ");

            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            for (int i = 0; i < ids.length; i++)
            {
                addToBoard(player, obj, ids[i], i);
            }

            player.setScoreboard(board);
        }

    }

}
