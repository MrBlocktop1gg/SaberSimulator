package mrblock.sabersimulatoraid.dungeons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class PlayerClickInventory implements Listener {

    public PlayerClickInventory(Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerClickBook(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = Bukkit.createInventory(player, 27, "Выбор данжа!");
        if (player.getItemInHand().getType() == Material.SPAWNER) {

            inventory.setItem(10, NameItem(new ItemStack(Material.SILVERFISH_SPAWN_EGG), "§7§lTest dungeons"));
            inventory.setItem(12, NameItem(new ItemStack(Material.ZOMBIE_SPAWN_EGG), "§7§lTest dungeons"));
            inventory.setItem(14, NameItem(new ItemStack(Material.SKELETON_SPAWN_EGG), "§7§lTest dungeons"));
            inventory.setItem(16, NameItem(new ItemStack(Material.WITHER_SKELETON_SPAWN_EGG), "§7§lTest dungeons"));

            player.openInventory(inventory);
        }
    }

    public ItemStack NameItem(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return item;
    }
}
