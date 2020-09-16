package neo.atlantis.lostadventurer.listener

import neo.atlantis.lostadventurer.model.item.DraculaTubuSpawnEgg
import neo.atlantis.lostadventurer.model.item.GmSpawnEgg
import neo.atlantis.lostadventurer.model.item.TekiTanukiSpawnEgg
import neo.atlantis.lostadventurer.model.item.TetuTanukiSpawnEgg
import neo.atlantis.lostadventurer.model.item.TubuSpawnEgg
import neo.atlantis.lostadventurer.model.npc.DraculaTubu
import neo.atlantis.lostadventurer.model.npc.Gm10
import neo.atlantis.lostadventurer.model.npc.TekiTanuki
import neo.atlantis.lostadventurer.model.npc.TetuTanuki
import neo.atlantis.lostadventurer.model.npc.Tubu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class ItemListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onClickSpawnEgg(event: PlayerInteractEvent) {
        val player = event.player
        if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {
            val itemStack: ItemStack = player.inventory.itemInMainHand
            when (itemStack.itemMeta) {
                TubuSpawnEgg().toItemStack().itemMeta -> {
                    Tubu().create(player.location, plugin)
                    itemStack.amount -= 1
                    event.isCancelled = true
                }
                DraculaTubuSpawnEgg().toItemStack().itemMeta -> {
                    DraculaTubu().create(player.location, plugin)
                    itemStack.amount -= 1
                    event.isCancelled = true
                }
                TetuTanukiSpawnEgg().toItemStack().itemMeta -> {
                    TetuTanuki().create(player.location, plugin)
                    itemStack.amount -= 1
                    event.isCancelled = true
                }
                TekiTanukiSpawnEgg().toItemStack().itemMeta -> {
                    TekiTanuki().create(player.location, plugin)
                    itemStack.amount -= 1
                    event.isCancelled = true
                }
                GmSpawnEgg().toItemStack().itemMeta -> {
                    Gm10().create(player.location, plugin)
                    itemStack.amount -= 1
                    event.isCancelled = true
                }
            }
        }
    }
}