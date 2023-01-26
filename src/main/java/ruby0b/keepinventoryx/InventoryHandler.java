package ruby0b.keepinventoryx;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

class InventoryHandler {
	private static InventoryHandler instance = new InventoryHandler();
	private HashMap<Player, InventoryBackup> inventories;

	private InventoryHandler() {
		inventories = new HashMap<>();
	}

	@Contract(pure = true)
	static InventoryHandler getInstance() {
		return instance;
	}

    void saveInventory(@NotNull Player player) {
		inventories.put(player, new InventoryBackup(player));
	}

	void saveRenamedInventory(@NotNull Player player) {
        InventoryBackup savedInv = new InventoryBackup(player);
        savedInv.filterNamed();
        inventories.put(player, savedInv);
	}

	void restoreBackup(Player player) {
		inventories.get(player).giveTo(player);
        inventories.remove(player);
	}

	boolean hasBackup(Player player) {
		return inventories.containsKey(player);
	}
}