package mrblock.sabersimulatoraid.npc;

import mrblock.sabersimulatoraid.SaberSimulatorAid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShopClasses implements CommandExecutor, Listener {
    public ShopClasses(Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("Npc_classes")) {
                Location location = ((Player) sender).getLocation();
                Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
                villager.setCustomName("§4Магазин классов!");
                villager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 5, true, false));
                villager.setCollidable(false);
                villager.setAI(false);
                villager.setCustomNameVisible(true);
                return true;
            }
        }
        return false;
    }
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Inventory shop = Bukkit.createInventory(player, 9, "§4Магазин классов!");
        if (event.getRightClicked() instanceof Villager) {

            if (event.getRightClicked().getName().equals("§4Магазин классов!")) {
                shop.setItem(0, SaberSimulatorAid.createMetaData(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lУЧЕНИК", "§6§lЦена: 2млн Монет! "));
                shop.setItem(1, SaberSimulatorAid.createMetaData(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lСОЛДАТ", "§6§lЦена: 64млн Монет! "));
              /*  shop.setItem(12, SaberSimulatorAid.createMetaData(new ItemStack(Material.IRON_CHESTPLATE), "§b§lПАЛАДИН", "§6§lЦена: 128млн Монет! \n х8 удары! "));
                shop.setItem(14, SaberSimulatorAid.createMetaData(new ItemStack(Material.GOLDEN_CHESTPLATE), "§b§lУБИЙЦА", "§6§lЦена: 256млн Монет! \n х16 удары! "));
                shop.setItem(15, SaberSimulatorAid.createMetaData(new ItemStack(Material.DIAMOND_CHESTPLATE), "§b§lТИТАН", "§6§lЦена: 512млн Монет! \n х32 удары! "));
                shop.setItem(16, SaberSimulatorAid.createMetaData(new ItemStack(Material.NETHERITE_CHESTPLATE), "§b§lКИБОРГ", "§6§lЦена: 2млрд Монет! \n х64 удары! ")); */

                player.openInventory(shop);
            }
        }
    }
}
