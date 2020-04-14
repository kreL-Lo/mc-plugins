package test.randomTp.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main extends  JavaPlugin{
    @Override
    public void onEnable() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            int counter =0;
            int t = 300;
            @Override
            public void run() {

                if(Bukkit.getServer().getOnlinePlayers().size()==2) {
                    counter++;
                    if (counter == t) {
                        counter = 0;
                        exchangePlayers();
                    } else if (counter >= t - 10 && counter <= t) {
                        Bukkit.getServer().broadcastMessage("GET YOUR ASS READY ... "+Integer.toString(t - counter));
                    }
                }
                else{
                    counter =0 ;
                }
            }
        }, 0L, 1*20);
    }

    public void exchangePlayers(){
        List<Player > players= new ArrayList<>();
        for(Player p : Bukkit.getServer().getOnlinePlayers())
            players.add(p);
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        Bukkit.getServer().broadcastMessage("Enjoy :)");
        Location tmp = p1.getLocation();
        p1.teleport(p2.getLocation());
        p2.teleport(tmp);
    }
}
