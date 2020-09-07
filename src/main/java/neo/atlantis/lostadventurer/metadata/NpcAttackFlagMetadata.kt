package neo.atlantis.lostadventurer.metadata

import neo.atlantis.lostadventurer.ext.getBooleanMetadata
import neo.atlantis.lostadventurer.ext.setBooleanMetadata
import org.bukkit.entity.Entity
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class NpcAttackFlagMetadata(private val plugin: JavaPlugin) {
    fun avoidTwice(entity: Entity) {
        setFlag(entity, true)
        object : BukkitRunnable() {
            override fun run() {
                setFlag(entity, false)
            }
        }.runTaskLater(plugin, 20)
    }

    fun setFlag(entity: Entity, flag: Boolean) {
        entity.setBooleanMetadata(plugin, MetadataKeys.NPC_ATTACK_FLAG_METADATA, flag)
    }

    fun getFlag(entity: Entity): Boolean {
        return entity.getBooleanMetadata(MetadataKeys.NPC_ATTACK_FLAG_METADATA)
    }
}