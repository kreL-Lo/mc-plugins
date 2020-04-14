package test.randomTp.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main extends  JavaPlugin{
    BukkitScheduler scheduler;
    boolean check_running;
    int id_task;
    @Override
    public void onEnable() {
        check_running=false;
        scheduler = getServer().getScheduler();
        this.getCommand("run-swap").setExecutor(this::onCommand);

    }
    public void exchangePlayers(){
        Bukkit.getServer().getLogger().info("Running");
        List<Player> players = new ArrayList<>();
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            players.add(p);
        }
        Collections.shuffle(players);

        List<HoldingClass>locations = new ArrayList<>();

        int n = players.size();
        for(Player p : players){
            locations.add(new HoldingClass(p.getName(),p.getLocation()));
        }
        for(int i = 0 ;i<n-1;i++){
            players.get(i).teleport(locations.get(i+1).getLocation());
            players.get(i).sendRawMessage("Tp-ed from "+ locations.get(i+1).getName());
        }
        players.get(n-1).teleport(locations.get(0).getLocation());
        players.get(n-1).sendRawMessage("Tp-ed from "+ locations.get(0).getName());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String    label, String[] args) {

        if(check_running==false) {
            Bukkit.getServer().getLogger().info("Run TP Swap Began, get ready... next tp in 5 min");
            id_task = scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                int counter = 0;
                int t = 300;
                @Override
                public void run() {
                    if (Bukkit.getServer().getOnlinePlayers().size() >= 2) {
                        counter++;
                        if (counter == t) {
                            counter = 0;
                            Bukkit.getServer().getLogger().info("Tp-ed");
                            exchangePlayers();
                            Bukkit.getServer().getLogger().info("... next tp in 5 min");

                        } else if (counter >= t - 10 && counter <= t) {
                            Bukkit.getServer().broadcastMessage("GET YOUR ASS READY ... " + Integer.toString(t - counter));
                        }
                    } else {
                        counter = 0;
                    }
                }
            }, 0L, 1 * 20);
            check_running=true;
        }
        else{
            Bukkit.getServer().getLogger().info("Run TP Swap Began is off, get safe");
            Bukkit.getScheduler().cancelTask(id_task);
            check_running= false;
        }
        return super.onCommand(sender, command, label, args);
    }

}
