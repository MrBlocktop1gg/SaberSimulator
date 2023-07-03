package mrblock.sabersimulatoraid.listener;

import mrblock.sabersimulatoraid.SaberSimulatorAid;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;


public class PlayerAddPointClick implements Listener {
    /**
     * <b>Количество убийств до завершения</b>
     */
    public static final float KILL_COUNT = 10.0f;
    //Цены
    public static final int WOODEN_SWORD_PRICE = 256;
    public static final int GOLDEN_SWORD_PRICE = 516;
    public static final int STONE_SWORD_PRICE = 1040;
    public static final int IRON_SWORD_PRICE = 2400;
    public static final int DIAMOND_SWORD_PRICE = 4960;
    public static final int NETHERITE_SWORD_PRICE = 18800;
    public static final int WOODEN_AXE_PRICE = 32000;
    public static final int GOLDEN_AXE_PRICE = 63744;
    public static final int STONE_AXE_PRICE = 127488;
    public static final int IRON_AXE_PRICE = 254876;
    public static final int DIAMOND_AXE_PRICE = 550912;
    public static final int NETHERITE_AXE_PRICE = 1142784;
    public static final int TRIDENT_PRICE = 2367488;
    //Классы
    public static final int LEATHER_CHESTPLATE_PRICE = 2000000;
    public static final int CHAINMAIL_CHESTPLATE_PRICE = 128000000;

    /*int clickBuyIronChestplate = 10240;
    int clickBuyGoldenChestplate = 24200;
    int clickBuyDiamondChestplate = 49260;
    int clickBuyNetheriteChestplate = 182800; */

    private final Location testDungeonsLoc = new Location(Bukkit.getWorld("world"), -72, 69, 281);
    private final BossBar bar;
    private final Scoreboard board;
    private final Objective objClick, objKills;
    private final Team team_student;
    private final Team team_soldier;

    public PlayerAddPointClick(Plugin plugin) {
        //Регистрация ивентов
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);

        //Главный скорборд
        board = Bukkit.getScoreboardManager().getMainScoreboard();

        //Регистрация задачи SaberSimulator
        if (board.getObjective("SaberSimulator") == null)
            board.registerNewObjective("SaberSimulator", "dummy");
        objClick = board.getObjective("SaberSimulator");
        objClick.setDisplayName("  §bSaberSimulator  ");
        objClick.setDisplaySlot(DisplaySlot.SIDEBAR);

        //Регистрация задачи Kills
        if (board.getObjective("Kills") == null)
            board.registerNewObjective("Kills", "dummy");
        objKills = board.getObjective("Kills");

        //Создание боссбара
        bar = Bukkit.getServer().createBossBar("§4§lОсталось убить мобов: " + 10, BarColor.RED, BarStyle.SEGMENTED_10);

        //Создание команды
        Team team_student = board.getTeam("team_student");
        Team team_soldier = board.getTeam("team_soldier");
        if (team_student == null) {
            team_student = board.registerNewTeam("team_student");
            team_student.setPrefix(ChatColor.AQUA +"[УЧЕНИК] " + ChatColor.WHITE);
        }
        if (team_soldier == null) {
            team_soldier = board.registerNewTeam("team_soldier");
            team_soldier.setPrefix(ChatColor.AQUA +"[СОЛДАТ] " + ChatColor.WHITE);
        }
        this.team_student = team_student;
        this.team_soldier = team_soldier;
    }

    @EventHandler
    public void clickAddPoints(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;

        final Player player = event.getPlayer();
        final int click = objClick.getScore(player).getScore();
        int points = switch(event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case STICK -> 1;
            case WOODEN_SWORD -> 2;
            case GOLDEN_SWORD -> 4;
            case STONE_SWORD -> 8;
            case IRON_SWORD -> 16;
            case DIAMOND_SWORD -> 32;
            case NETHERITE_SWORD -> 64;
            case WOODEN_AXE -> 128;
            case GOLDEN_AXE -> 256;
            case STONE_AXE -> 512;
            case IRON_AXE -> 1024;
            case DIAMOND_AXE -> 2048;
            case NETHERITE_AXE -> 4096;
            case TRIDENT -> 8192;
            default -> 0;
        };

        if(points == 0) return;

        player.setScoreboard(board);

        if (player.getInventory().getChestplate() != null) {
           points *= switch (player.getInventory().getChestplate().getType()) {
               case LEATHER_CHESTPLATE -> 2;
               case CHAINMAIL_CHESTPLATE -> 4;
               case GOLDEN_CHESTPLATE -> 6;
               case IRON_CHESTPLATE -> 9;
               case DIAMOND_CHESTPLATE -> 12;
               case NETHERITE_CHESTPLATE -> 16;
               default -> 1;
           };
        }

        player.sendTitle("", "§2§l+" + points, 2, 6, 2);
        objClick.getScore(player).setScore(click + points);
    }
    @EventHandler
    public void playerClickItemsInventory(InventoryClickEvent event) {
        event.setCancelled(true);

        final Player player = Bukkit.getPlayer(event.getWhoClicked().getName());
        final int click = objClick.getScore(event.getWhoClicked().getName()).getScore();

        PlayerInventory inventory = player.getInventory();
        ItemStack chestplate = player.getInventory().getChestplate();

        int price = 0;

        ItemStack item = null;

        if(event.getClickedInventory() == null || !event.getClickedInventory().getType().equals(InventoryType.CHEST)) return;

        if (event.getCurrentItem() == null)return;
        switch (event.getCurrentItem().getType()) {
            case WOODEN_SWORD:
                price = WOODEN_SWORD_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.WOODEN_SWORD), "§7§lСабля-Начинаний!", null);
                break;
            case GOLDEN_SWORD:
                price = GOLDEN_SWORD_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.GOLDEN_SWORD), "§7§lСабля Арбузная-Свежесть!", null);
                break;
            case STONE_SWORD:
                price = STONE_SWORD_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.STONE_SWORD), "§7§lСабля Сырная радость", null);
                break;
            case IRON_SWORD:
                price = IRON_SWORD_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.IRON_SWORD), "Сабля-Эндер", null);
                break;
            case DIAMOND_SWORD:
                price = DIAMOND_SWORD_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.DIAMOND_SWORD), "§7§lСабля-воина!", null);
                break;
            case NETHERITE_SWORD:
                price = NETHERITE_SWORD_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.NETHERITE_SWORD), "§7§lСабля-Наследия", null);
                break;
            case WOODEN_AXE:
                price = WOODEN_AXE_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.WOODEN_AXE), "§7§lТопор Правосудия", null);
                break;
            case GOLDEN_AXE:
                price = GOLDEN_AXE_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.GOLDEN_AXE), "§7§lСтеклянное Пророчество", null);
                break;
            case STONE_AXE:
                price = STONE_AXE_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.STONE_AXE), "§7§lКвантовая Сабля", null);
                break;
            case IRON_AXE:
                price = IRON_AXE_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.IRON_AXE), "§7§lСабля-nameTest", null);
                break;
            case DIAMOND_AXE:
                price = DIAMOND_AXE_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.DIAMOND_AXE), "§7§lСабля-nameTest", null);
                break;
            case NETHERITE_AXE:
                price = NETHERITE_AXE_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.NETHERITE_AXE), "§7§lСабля-nameTest", null);
                break;
            case TRIDENT:
                price = TRIDENT_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.TRIDENT), "§7§lСабля-nameTest", null);
                break;
            case LEATHER_CHESTPLATE:
                price = LEATHER_CHESTPLATE_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lУЧЕНИК ", "§6§lТеперь ваши удары увеличились в §c§l2 Раза!");
                break;
            case CHAINMAIL_CHESTPLATE:
                price = CHAINMAIL_CHESTPLATE_PRICE;
                item = SaberSimulatorAid.createMetaData(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lСОЛДАТ ", "§6§lТеперь ваши удары увеличились в §c§l4 Раза!");
                break;
            default:
                return;
        }

        if (chestplate != null && isChestplate(item.getType())) {
            if(getPrice(item.getType()) <= getPrice(chestplate.getType())) return;
        } else if (getPrice(inventory.getItem(0).getType()) >= getPrice(event.getCurrentItem().getType()))
            return;

        if (click < price) {
            player.closeInventory();
            player.sendTitle("", "§4§lНЕ ДОСТАТОЧНО МОНЕТ!");
        } else {
            objClick.getScore(player).setScore(click - price);
            player.closeInventory();
            player.sendTitle("", "§2§lПокупка прошла успешна!");
            if (isChestplate(item.getType()))
                 inventory.setItem(0, SaberSimulatorAid.createMetaData(new ItemStack(Material.STICK), "Сабля-Прошлого",null));
            inventory.setItem(isChestplate(item.getType()) ? 38 : 0, item);
        }
    }
    @EventHandler
    public void dungeons(InventoryClickEvent event) {
        Player player = Bukkit.getPlayer(event.getWhoClicked().getName());
        EntityType type = null;
        Location mobLocation = new Location(Bukkit.getWorld("world"), -88, 69, 288);
        Location location = testDungeonsLoc;
        if (event.getCurrentItem() == null)return;
        switch (event.getCurrentItem().getType()) {
            case SILVERFISH_SPAWN_EGG:
                type = EntityType.SILVERFISH;
                break;
            case ZOMBIE_SPAWN_EGG:
                type = EntityType.ZOMBIE;
                break;
            case SKELETON_SPAWN_EGG:
                type = EntityType.SKELETON;
                break;
            case WITHER_SKELETON_SPAWN_EGG:
                type = EntityType.WITHER_SKELETON;
                break;
            default:
                return;
        }
        bar.addPlayer(player);
        player.setBedSpawnLocation(location);
        player.setScoreboard(board);
        objKills.getScore(player).setScore((int) KILL_COUNT);
        player.teleport(location);
        player.sendMessage("§7§lВы телепортированы в §4§lTest Dungeons");
        for (byte sf = 0; sf < KILL_COUNT; sf++)
            player.getWorld().spawnEntity(mobLocation, type);
        }
  @EventHandler
  public void playerKilledEntity(EntityDeathEvent event) {
      Player player = event.getEntity().getKiller();
      if (player == null) return;
      Score kills = objKills.getScore(player);
      Location spawnLocation = new Location(player.getWorld(), -78, 64, 17);
      int points = switch (event.getEntity().getType()) {
         case SILVERFISH -> 10;
         case ZOMBIE -> 100;
         case SKELETON -> 500;
         case WITHER_SKELETON -> 1000;
         default -> 0;
      };
      if (points == 0) return;
      kills.setScore(kills.getScore()-1);
      objClick.getScore(player).setScore(objClick.getScore(player).getScore() + points);
      player.sendMessage("Вы получили §2§l+" + points);
      if (kills.getScore() <= 0) {
          player.teleport(spawnLocation);
          player.sendMessage("Возвращаем вас на спавн!");
          player.setBedSpawnLocation(spawnLocation);
          bar.removePlayer(player);
      } else {
          bar.setProgress(kills.getScore()/KILL_COUNT);
          bar.setTitle("§4§lОсталось убить мобов: " + kills.getScore());
      }
  }
    @EventHandler
    public void playerDead(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        Entity killer = event.getEntity().getKiller();
        if (killer instanceof LivingEntity) {
            LivingEntity livingKiller = (LivingEntity) killer;

            int point = switch (livingKiller.getType()) {
                case SILVERFISH -> 25;
                case ZOMBIE -> 150;
                case SKELETON -> 550;
                case WITHER_SKELETON -> 1250;
                default -> 0;
            };
            if (point == 0) return;
            objClick.getScore(player).setScore(objClick.getScore(player).getScore() - point);
            bar.removePlayer(player);
            player.sendMessage("Вы проиграли :( §2§l-" + point);
            player.teleport(new Location(player.getWorld(), -78, 64, 17));
            player.sendMessage("Возвращаем вас на спавн");
        }
    }
      private int getPrice(Material material) {
          return switch (material) {
              case WOODEN_SWORD -> WOODEN_SWORD_PRICE;
              case GOLDEN_SWORD -> GOLDEN_SWORD_PRICE;
              case STONE_SWORD -> STONE_SWORD_PRICE;
              case IRON_SWORD -> IRON_SWORD_PRICE;
              case DIAMOND_SWORD -> DIAMOND_SWORD_PRICE;
              case NETHERITE_SWORD -> NETHERITE_SWORD_PRICE;
              case WOODEN_AXE -> WOODEN_AXE_PRICE;
              case GOLDEN_AXE -> GOLDEN_AXE_PRICE;
              case STONE_AXE -> STONE_AXE_PRICE;
              case IRON_AXE -> IRON_AXE_PRICE;
              case DIAMOND_AXE -> DIAMOND_AXE_PRICE;
              case NETHERITE_AXE -> NETHERITE_AXE_PRICE;
              case TRIDENT -> TRIDENT_PRICE;
              case LEATHER_CHESTPLATE -> LEATHER_CHESTPLATE_PRICE;
              case CHAINMAIL_CHESTPLATE -> CHAINMAIL_CHESTPLATE_PRICE;
              default -> 0;
          };
      }
      @EventHandler
      public void playerLeaveServer(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        team_student.removePlayer(player);
        team_soldier.removePlayer(player);
      }
    public static ItemStack createMetaData(ItemStack item, String name, String lore) {
        name = "§r" + name;
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        if (lore != null) {
            List<String> list = new ArrayList<>();
            list.add(lore);
            itemMeta.setLore(list);
        }
        item.setItemMeta(itemMeta);
        return item;
    }
    public static boolean isChestplate(Material material) {
        switch (material) {
            case LEATHER_CHESTPLATE, CHAINMAIL_CHESTPLATE, IRON_CHESTPLATE, GOLDEN_CHESTPLATE, DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE -> {
                return true;
            }
        }
        return false;
    }
}