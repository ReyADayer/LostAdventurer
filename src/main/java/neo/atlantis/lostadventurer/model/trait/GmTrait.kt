package neo.atlantis.lostadventurer.model.trait

import neo.atlantis.lostadventurer.ext.getBooleanMetadata
import neo.atlantis.lostadventurer.ext.getLocationMetadata
import neo.atlantis.lostadventurer.ext.getStringMetadata
import neo.atlantis.lostadventurer.ext.random
import neo.atlantis.lostadventurer.ext.setLocationMetadata
import neo.atlantis.lostadventurer.ext.setStringMetadata
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import neo.atlantis.lostadventurer.metadata.NpcAttackFlagMetadata
import neo.atlantis.lostadventurer.model.range.RectRange
import net.citizensnpcs.api.trait.Trait
import net.citizensnpcs.util.PlayerAnimation
import org.bukkit.GameMode
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class GmTrait(
        private val plugin: JavaPlugin,
        private val speed: Float = 1.0f
) : Trait("gm") {
    private val range = RectRange(10.0, 5.0, 10.0)
    private val npcAttackFlagMetadata = NpcAttackFlagMetadata(plugin)

    override fun run() {
        val entity = npc.entity ?: return
        val state: State? = State.values().firstOrNull { it.key == entity.getStringMetadata(MetadataKeys.STATE) }
        if (entity.isOnGround) {
            when (state) {
                State.IDLE -> {
                    npc.navigator.localParameters.speedModifier(1.0f)
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
                    npc.navigator.localParameters.speedModifier(1.0f)
                    val targetEntity: Entity? = getTargetEntity(entity)
                    if (targetEntity != null) {
                        npc.navigator.setTarget(targetEntity.location)
                        if (entity.location.distance(targetEntity.location) < 2.2) {
                            attack(entity, targetEntity)
                        }
                    } else {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IDLE.key)
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
        if (targetEntity is LivingEntity && entity is Player) {
            PlayerAnimation.ARM_SWING.play(entity)
            targetEntity.damage(5.0)
        }
        npcAttackFlagMetadata.avoidTwice(entity)
    }

    private fun getTargetEntity(entity: Entity): Entity? {
        return range.getEntities(entity.location)
                .filter { it is Mob || it.getBooleanMetadata(MetadataKeys.IS_ENEMY) }
                .filterNot { it is Player && (it.gameMode == GameMode.SPECTATOR || it.gameMode == GameMode.CREATIVE) }
                .firstOrNull { it != entity }
    }

    private enum class State(val key: String) {
        IDLE("idle"),
        ATTACK("tnt"),
        RUNAWAY("runaway");
    }
}