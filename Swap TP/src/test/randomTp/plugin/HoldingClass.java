package test.randomTp.plugin;

import org.bukkit.Location;

public class HoldingClass {
    String name;
    Location location;
    public HoldingClass(String name, Location location){
        this. name= name;
        this.location= location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
