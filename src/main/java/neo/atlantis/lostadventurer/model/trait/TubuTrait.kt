package neo.atlantis.lostadventurer.model.trait

import neo.atlantis.lostadventurer.ext.getBooleanMetadata
import neo.atlantis.lostadventurer.ext.getLocationMetadata
import neo.atlantis.lostadventurer.ext.getStringMetadata
import neo.atlantis.lostadventurer.ext.playSound
import neo.atlantis.lostadventurer.ext.random
import neo.atlantis.lostadventurer.ext.setLocationMetadata
import neo.atlantis.lostadventurer.ext.setStringMetadata
import neo.atlantis.lostadventurer.ext.spawn
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import neo.atlantis.lostadventurer.metadata.NpcAttackFlagMetadata
import neo.atlantis.lostadventurer.model.range.RectRange
import net.citizensnpcs.api.trait.Trait
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.entity.Cat
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class TubuTrait(private val plugin: JavaPlugin) : Trait("tubu") {
    private val range = RectRange(10.0, 5.0, 10.0)
    private val npcAttackFlagMetadata = NpcAttackFlagMetadata(plugin)

    override fun run() {
        val entity = npc.entity ?: return
        val state: State? = State.values().firstOrNull { it.key == entity.getStringMetadata(MetadataKeys.STATE) }
        if (entity.isOnGround) {
            when (state) {
                State.IDLE -> {
                    val targetPlayer: Entity? = getTargetEntity(entity)
                    if (targetPlayer != null) {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.ATTACK.key)
                    } else {
                        val targetLocation = entity.getLocationMetadata(MetadataKeys.TARGET_LOCATION)
                        if (targetLocation == null) {
                            val randomLocation = entity.location.random(10.0, 5.0, 10.0)
                            entity.setLocationMetadata(plugin, MetadataKeys.TARGET_LOCATION, randomLocation)
                            npc.navigator.setTarget(randomLocation)
                        } else {
                            val rand = Random().nextInt(100)
                            npc.navigator.setTarget(targetLocation)
                            if (rand < 2 || entity.location.distance(targetLocation) < 2.2) {
                                entity.removeMetadata(MetadataKeys.TARGET_LOCATION, plugin)
                            }
                        }
                    }
                }
                State.ATTACK -> {
                    val targetPlayer: Entity? = getTargetEntity(entity)
                    if (targetPlayer != null) {
                        npc.navigator.setTarget(targetPlayer.location)
                        if (entity.location.distance(targetPlayer.location) < 2.2) {
                            attack(entity, targetPlayer)
                        }
                    } else {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IDLE.key)
                    }
                }
                State.RUNAWAY -> {
                    val tntEntity = range.getEntities(entity.location).filterIsInstance<TNTPrimed>().firstOrNull()
                    if (tntEntity == null) {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IDLE.key)
                    } else {
                        val vector = entity.location.clone().subtract(tntEntity.location).toVector().normalize()
                        npc.navigator.setTarget(entity.location.clone().add(vector.multiply(3)))
                    }
                }
                else -> {
                    entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IDLE.key)
                }
            }
        }
    }

    private fun attack(entity: Entity, targetEntity: Entity) {
        if (npcAttackFlagMetadata.getFlag(entity)) {
            return
        }
        val targetLocation = entity.location.clone().add(0.0, 1.0, 0.0).add(entity.location.direction)
        targetLocation.spawn<TNTPrimed> {

        }
        targetLocation.playSound(Sound.ENTITY_TNT_PRIMED, 1.0f, 0.83f)
        npcAttackFlagMetadata.avoidTwice(entity)
        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.RUNAWAY.key)
    }

    private fun getTargetEntity(entity: Entity): Entity? {
        return range.getEntities(entity.location)
                .filter { it is Player && it is Cat }
                .filterNot { it is Player && (it.gameMode == GameMode.SPECTATOR || it.gameMode == GameMode.CREATIVE) }
                .filterNot { it.getBooleanMetadata(MetadataKeys.IS_ENEMY) }
                .firstOrNull { it != entity }
    }

    private enum class State(val key: String) {
        IDLE("idle"),
        ATTACK("attack"),
        RUNAWAY("runaway");
    }
}