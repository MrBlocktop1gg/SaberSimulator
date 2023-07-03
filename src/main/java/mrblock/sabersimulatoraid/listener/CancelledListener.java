package mrblock.sabersimulatoraid.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

public class CancelledListener implements Listener {
    public CancelledListener(Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void offHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
    @EventHandler
    public void offRain(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
   /* @EventHandler
    public void dropInventoryItems(PlayerDropItemEvent event) {
        event.setCancelled(true);
    } */
   @EventHandler
   public void offFireMonsters(EntityCombustEvent event) {
       event.setCancelled(true);
   }
}
