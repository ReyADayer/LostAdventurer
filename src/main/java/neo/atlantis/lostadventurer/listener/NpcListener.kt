package neo.atlantis.lostadventurer.listener

import neo.atlantis.lostadventurer.ext.isCitizensNPC
import neo.atlantis.lostadventurer.ext.setEntityMetadata
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class NpcListener(private val plugin: JavaPlugin) : Listener {
//    @EventHandler
//    fun onSpawn(event: EntitySpawnEvent) {
//        val rand = Random().nextInt(10000)
//        if (rand in 10..99) {
//            Tubu().create(event.location, plugin)
//            event.isCancelled = true
//        } else if (rand < 10) {
//            DraculaTubu().create(event.location, plugin)
//            event.isCancelled = true
//        }
//    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        if (player.isCitizensNPC()) {
            event.deathMessage = null
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val rand = Random().nextInt(100)
        if (rand < 20) {
            val entity = event.entity
            entity.setEntityMetadata(plugin, MetadataKeys.TARGET_ENTITY, event.damager)
        }
    }
}