# KeepInventoryX
A Minecraft Spigot plugin that allows you to customize what players keep when they die.
The Plugin is configured through the following permissions:
* `keepinventoryx.keep` - keep all items (does not include xp)
* `keepinventoryx.keeprenamed` - only keep renamed items
* `keepinventoryx.keepxp` - keep all experience points (does not include any items)

So, to get the behavior of the built-in `/gamerule keepInventory true`,
you would need the permissions `keepinventoryx.keep` and `keepinventoryx.keepxp`.
