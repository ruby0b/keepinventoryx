package ruby0b.keepinventoryx;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Predicate;

class InventoryBackup {
    private ItemStack[] storage;
    private ItemStack[] armor;
    private ItemStack offHand;
    private ItemStack[] extraContents;

    @Contract(pure = true)
    InventoryBackup(ItemStack[] storage, ItemStack[] armor,
                    ItemStack offHand, ItemStack[] extraContents) {
        this.storage = storage;
        this.armor = armor;
        this.offHand = offHand;
        this.extraContents = extraContents;
    }

    InventoryBackup(@NotNull Player player) {
        PlayerInventory inv = player.getInventory();
        this.storage = inv.getStorageContents();
        this.armor = inv.getArmorContents();
        this.offHand = inv.getItemInOffHand();
        this.extraContents = inv.getExtraContents();
    }

    void giveTo(@NotNull Player player) {
        PlayerInventory inv = player.getInventory();
        inv.setStorageContents(storage);
        inv.setArmorContents(armor);
        inv.setItemInOffHand(offHand);
        inv.setExtraContents(extraContents);
    }

    void filter(Predicate<ItemStack> predicate) {
        storage = Arrays.stream(storage).filter(predicate).toArray(ItemStack[]::new);
        armor = Arrays.stream(armor).filter(predicate).toArray(ItemStack[]::new);
        offHand = predicate.test(offHand) ? offHand : null;
        extraContents = Arrays.stream(extraContents).filter(predicate).toArray(ItemStack[]::new);
    }

    void filterNamed() {
        filter(InventoryBackup::hasDisplayName);
    }

    @Contract("null -> false")
    static boolean hasDisplayName(ItemStack i) {
        if (i != null) {
            if (i.hasItemMeta()) {
                ItemMeta imeta = i.getItemMeta();
                if (imeta != null) {
                    return imeta.hasDisplayName();
                }
            }
        }
        return false;
    }

}
