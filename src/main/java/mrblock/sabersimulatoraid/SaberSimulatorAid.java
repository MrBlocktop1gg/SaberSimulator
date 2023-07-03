package mrblock.sabersimulatoraid;

import mrblock.sabersimulatoraid.dungeons.PlayerClickInventory;
import mrblock.sabersimulatoraid.listener.CancelledListener;
import mrblock.sabersimulatoraid.listener.PlayerAddPointClick;
import mrblock.sabersimulatoraid.listener.PlayerJoinServer;
import mrblock.sabersimulatoraid.npc.ShopClasses;
import mrblock.sabersimulatoraid.npc.ShopSaber;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SaberSimulatorAid extends JavaPlugin {

    @Override
    public void onEnable() {

        //Register Listeners
        new CancelledListener(this);
        new PlayerJoinServer(this);
        new ShopSaber(this);
        new PlayerClickInventory(this);
        new PlayerAddPointClick(this);

        //Register command
        getCommand("Npc_shop").setExecutor(new ShopSaber(this));
        getCommand("Npc_classes").setExecutor(new ShopClasses(this));

    }

    @Override
    public void onDisable() {
    }

    /**
     * <b>Метод создания предмета</b>
     * @param item - <i>Предмет</i>
     * @param name - <i>Название</i>
     * @param lore - <i>Описание</i>
     * @return Предмет item с названием name и описанием lore
     */
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
}
