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

public class ShopSaber implements CommandExecutor, Listener {
    public ShopSaber(Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("Npc_shop")) {
                Location location = ((Player) sender).getLocation();
                Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
                villager.setCustomName("§4Магазин саблей!");
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
        Inventory shop = Bukkit.createInventory(player, 18, "§4Магазин саблей!");
        if (event.getRightClicked() instanceof Villager) {

            if (event.getRightClicked().getName().equals("§4Магазин саблей!")) {
                shop.setItem(0, SaberSimulatorAid.createMetaData(new ItemStack(Material.WOODEN_SWORD), "§7§lСабля-Начинаний!", "§6§lЦена: 256 Монет!"));
                shop.setItem(1, SaberSimulatorAid.createMetaData(new ItemStack(Material.GOLDEN_SWORD), "§7§lСабля Арбузная-Свежесть!", "§6§lЦена: 516 Монет!"));
                shop.setItem(2, SaberSimulatorAid.createMetaData(new ItemStack(Material.STONE_SWORD), "§7§lСабля Сырная радость", "§6§lЦена: 1040 Монет!"));
                shop.setItem(3, SaberSimulatorAid.createMetaData(new ItemStack(Material.IRON_SWORD), "§7§lСабля Эндер", "§6§lЦена: 2400 Монет!"));
                shop.setItem(4, SaberSimulatorAid.createMetaData(new ItemStack(Material.DIAMOND_SWORD), "§7§lСабля-воина!", "§6§lЦена: 4960 Монет!"));
                shop.setItem(5, SaberSimulatorAid.createMetaData(new ItemStack(Material.NETHERITE_SWORD), "§7§lСабля-Наследия", "§6§lЦена: 18800 Монет!"));
                shop.setItem(6, SaberSimulatorAid.createMetaData(new ItemStack(Material.WOODEN_AXE), "§7§lТопор Правосудия", "§6§lЦена: 32000 Монет!"));
                shop.setItem(7, SaberSimulatorAid.createMetaData(new ItemStack(Material.GOLDEN_AXE), "§7§lСтеклянное Пророчество", "§6§lЦена: 63744 Монет!"));
                shop.setItem(8, SaberSimulatorAid.createMetaData(new ItemStack(Material.STONE_AXE), "§7§lКвантовая Сабля", "§6§lЦена: 127488 Монет!"));
                shop.setItem(9, SaberSimulatorAid.createMetaData(new ItemStack(Material.IRON_AXE), "§7§lСабля-nameTest", "§6§lЦена: 254876 Монет!"));
                shop.setItem(10, SaberSimulatorAid.createMetaData(new ItemStack(Material.DIAMOND_AXE), "§7§lСабля-nameTest", "§6§lЦена: 550912 Монет!"));
                shop.setItem(11, SaberSimulatorAid.createMetaData(new ItemStack(Material.NETHERITE_AXE), "§7§lСабля-nameTest", "§6§lЦена: 1142784 Монет!"));
                shop.setItem(12, SaberSimulatorAid.createMetaData(new ItemStack(Material.TRIDENT), "§7§lСабля-nameTest", "§6§lЦена: 2367488 Монет!"));

                player.openInventory(shop);
            }
        }
    }
}
