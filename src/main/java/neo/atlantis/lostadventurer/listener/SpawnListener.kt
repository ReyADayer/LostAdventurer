package neo.atlantis.lostadventurer.listener

import neo.atlantis.lostadventurer.ext.isCitizensNPC
import neo.atlantis.lostadventurer.model.npc.DraculaTubu
import neo.atlantis.lostadventurer.model.npc.Tubu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class SpawnListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onSpawn(event: EntitySpawnEvent) {
        val rand = Random().nextInt(10000)
        if (rand in 10..99) {
            Tubu().create(event.location, plugin)
            event.isCancelled = true
        } else if (rand < 10) {
            DraculaTubu().create(event.location, plugin)
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        if (player.isCitizensNPC()) {
            event.deathMessage = null
        }
    }
}