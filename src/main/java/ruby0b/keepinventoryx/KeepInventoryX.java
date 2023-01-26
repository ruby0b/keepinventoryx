package ruby0b.keepinventoryx;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class KeepInventoryX extends JavaPlugin implements Listener {
	
	private final boolean hasMethod = hasMethod("setKeepInventory");
	private InventoryHandler invHandler = InventoryHandler.getInstance();

	private boolean hasMethod(String string) {
		boolean hasMethod = false;
		Method[] methods = PlayerDeathEvent.class.getMethods();
		for (Method m : methods) {
			if (m.getName().equals(string)) {
				hasMethod = true;
				break;
			}
		}
		return hasMethod;
	}

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("KeepInventoryX enabled!");
	}

	@EventHandler
	public void onEntityDeath(@NotNull PlayerDeathEvent event) {
		Player player = event.getEntity();

		if (player.hasPermission("keepinventoryx.keep")) {
			if (!hasMethod) {
				invHandler.saveInventory(player);
				event.getDrops().clear();
			} else {
				event.setKeepInventory(true);
				event.getDrops().clear();
			}
		} else if (player.hasPermission("keepinventoryx.keeprenamed")) {
			invHandler.saveRenamedInventory(player);
			event.getDrops().removeIf(InventoryBackup::hasDisplayName);
		}

		if (player.hasPermission("keepinventoryx.keepxp")) {
			event.setKeepLevel(true);
			event.setDroppedExp(0);
		}
	}

	@EventHandler
	public void onPlayerRespawn(@NotNull PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		if ((player.hasPermission("keepinventoryx.keep")
				|| player.hasPermission("keepinventoryx.keeprenamed"))
			&& invHandler.hasBackup(player) ) {
				invHandler.restoreBackup(player);
		}
	}
}