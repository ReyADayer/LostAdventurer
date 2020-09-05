package neo.atlantis.lostadventurer.model.trait

import neo.atlantis.lostadventurer.ext.getBooleanMetadata
import neo.atlantis.lostadventurer.ext.getLocationMetadata
import neo.atlantis.lostadventurer.ext.getStringMetadata
import neo.atlantis.lostadventurer.ext.playSound
import neo.atlantis.lostadventurer.ext.random
import neo.atlantis.lostadventurer.ext.setBooleanMetadata
import neo.atlantis.lostadventurer.ext.setLocationMetadata
import neo.atlantis.lostadventurer.ext.setStringMetadata
import neo.atlantis.lostadventurer.ext.spawn
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import neo.atlantis.lostadventurer.metadata.NpcAttackFlagMetadata
import neo.atlantis.lostadventurer.model.range.RectRange
import net.citizensnpcs.api.trait.Trait
import net.citizensnpcs.api.trait.trait.Equipment
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.Cat
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class TubuTrait(private val plugin: JavaPlugin, private val speed: Float = 1.0f) : Trait("tubu") {
    private val range = RectRange(10.0, 5.0, 10.0)
    private val npcAttackFlagMetadata = NpcAttackFlagMetadata(plugin)

    override fun run() {
        val entity = npc.entity ?: return
        val state: State? = State.values().firstOrNull { it.key == entity.getStringMetadata(MetadataKeys.STATE) }
        if (entity.isOnGround) {
            when (state) {
                State.IDLE -> {
                    npc.navigator.localParameters.speedModifier(1.0f)
                    npc.getTrait(Equipment::class.java).apply {
                        set(Equipment.EquipmentSlot.HAND, ItemStack(Material.FLINT_AND_STEEL))
                    }
                    if (isHouse(entity.location)) {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IGNITE.key)
                    }
                    val targetPlayer: Entity? = getTargetEntity(entity)
                    if (targetPlayer != null) {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.TNT.key)
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
                State.TNT -> {
                    npc.navigator.localParameters.speedModifier(speed)
                    npc.getTrait(Equipment::class.java).apply {
                        set(Equipment.EquipmentSlot.HAND, ItemStack(Material.TNT))
                    }
                    entity.location.block.blockData
                    val targetPlayer: Entity? = getTargetEntity(entity)
                    if (targetPlayer != null) {
                        npc.navigator.setTarget(targetPlayer.location)
                        if (entity.location.distance(targetPlayer.location) < 2.2) {
                            if (isHouse(entity.location) && !entity.getBooleanMetadata(MetadataKeys.IGNITE_COOL_TIME)) {
                                entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IGNITE.key)
                            } else {
                                attack(entity, targetPlayer)
                            }
                        }
                    } else {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IDLE.key)
                    }
                }
                State.IGNITE -> {
                    npc.navigator.localParameters.speedModifier(1.0f)
                    npc.getTrait(Equipment::class.java).apply {
                        set(Equipment.EquipmentSlot.HAND, ItemStack(Material.FLINT_AND_STEEL))
                    }
                    val targetLocation = entity.location.random(3.0, 3.0, 3.0)
                    if (targetLocation.block.type.isBurnable) {
                        npc.navigator.setTarget(targetLocation)
                        ignite(targetLocation)
                        entity.setBooleanMetadata(plugin, MetadataKeys.IGNITE_COOL_TIME, true)
                        object : BukkitRunnable() {
                            override fun run() {
                                entity.setBooleanMetadata(plugin, MetadataKeys.IGNITE_COOL_TIME, false)
                            }
                        }.runTaskLaterAsynchronously(plugin, 1000)
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.RUNAWAY_IGNITE.key)
                    }
                }
                State.RUNAWAY_TNT -> {
                    npc.navigator.localParameters.speedModifier(speed)
                    npc.getTrait(Equipment::class.java).apply {
                        set(Equipment.EquipmentSlot.HAND, ItemStack(Material.BREAD))
                    }
                    val tntEntity = range.getEntities(entity.location).filterIsInstance<TNTPrimed>().firstOrNull()
                    if (tntEntity == null) {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IDLE.key)
                    } else {
                        val vector = entity.location.clone().subtract(tntEntity.location).toVector().normalize()
                        npc.navigator.setTarget(entity.location.clone().add(vector.multiply(3)))
                    }
                }
                State.RUNAWAY_IGNITE -> {
                    npc.navigator.localParameters.speedModifier(speed)
                    npc.getTrait(Equipment::class.java).apply {
                        set(Equipment.EquipmentSlot.HAND, ItemStack(Material.BREAD))
                    }
                    val fireLocation = getFireLocation(entity.location)
                    if (fireLocation == null) {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IDLE.key)
                    } else {
                        val vector = entity.location.clone().subtract(fireLocation).toVector().normalize()
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
        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.RUNAWAY_TNT.key)
    }

    private fun getTargetEntity(entity: Entity): Entity? {
        return range.getEntities(entity.location)
                .filter { it is Player || it is Cat }
                .filterNot { it is Player && (it.gameMode == GameMode.SPECTATOR || it.gameMode == GameMode.CREATIVE) }
                .filterNot { it.getBooleanMetadata(MetadataKeys.IS_ENEMY) }
                .firstOrNull { it != entity }
    }

    private fun ignite(targetLocation: Location) {
        BlockFace.values().forEach {
            val block = targetLocation.block.getRelative(it)
            if (block.type == Material.AIR) {
                block.type = Material.FIRE
            }
        }
    }

    private fun isHouse(location: Location): Boolean {
        var score = 0
        for (i in -5..5) {
            for (j in -5..5) {
                for (k in -5..5) {
                    val block = location.block.getRelative(i, j, k)
                    if (block.type.isBurnable) {
                        score += 1
                    }
                }
            }
        }
        return score >= 100
    }

    private fun getFireLocation(location: Location): Location? {
        for (i in -5..5) {
            for (j in -5..5) {
                for (k in -5..5) {
                    val block = location.block.getRelative(i, j, k)
                    if (block.type == Material.FIRE) {
                        return block.location
                    }
                }
            }
        }
        return null
    }

    private enum class State(val key: String) {
        IDLE("idle"),
        TNT("tnt"),
        IGNITE("ignite"),
        RUNAWAY_TNT("runawayTnt"),
        RUNAWAY_IGNITE("runawayIgnite");
    }
}