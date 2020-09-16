package neo.atlantis.lostadventurer.metadata

import neo.atlantis.lostadventurer.ext.getBooleanMetadata
import neo.atlantis.lostadventurer.ext.setBooleanMetadata
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class PlayerFlagMetadata(val plugin: JavaPlugin) {
    fun avoidTwice(player: Player) {
        setFlag(player, true)
        object : BukkitRunnable() {
            override fun run() {
                setFlag(player, false)
            }
        }.runTaskLater(plugin, 10)
    }

    fun setFlag(player: Player, flag: Boolean) {
        player.setBooleanMetadata(plugin, MetadataKeys.PLAYER_INTERACT_EVENT, flag)
    }

    fun getFlag(player: Player): Boolean {
        return player.getBooleanMetadata(MetadataKeys.PLAYER_INTERACT_EVENT)
    }
}