package mrblock.sabersimulatoraid.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getServer;

public class PlayerJoinServer implements Listener {
    public PlayerJoinServer(Plugin plugin) {
        getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.teleport(new Location(player.getWorld(), -78, 64, 17));
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().setItem(0, name(new ItemStack(Material.STICK), "Сабля-Прошлого"));
        player.getInventory().setItem(4, name(new ItemStack(Material.SPAWNER), "§c§lДанжи!"));
    }
    public ItemStack name(ItemStack item, String name) {
        name = "§r" + name;
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return item;
    }
}
