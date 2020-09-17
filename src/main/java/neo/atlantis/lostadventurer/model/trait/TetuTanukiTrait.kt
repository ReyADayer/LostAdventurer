package neo.atlantis.lostadventurer.model.trait

import neo.atlantis.lostadventurer.ext.getBooleanMetadata
import neo.atlantis.lostadventurer.ext.getEntityMetadata
import neo.atlantis.lostadventurer.ext.getLocationMetadata
import neo.atlantis.lostadventurer.ext.getStringMetadata
import neo.atlantis.lostadventurer.ext.random
import neo.atlantis.lostadventurer.ext.setEntityMetadata
import neo.atlantis.lostadventurer.ext.setLocationMetadata
import neo.atlantis.lostadventurer.ext.setStringMetadata
import neo.atlantis.lostadventurer.ext.spawn
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import neo.atlantis.lostadventurer.metadata.NpcAttackFlagMetadata
import neo.atlantis.lostadventurer.model.damage.Damage
import neo.atlantis.lostadventurer.model.range.RectRange
import neo.atlantis.lostadventurer.model.skill.FlameBurst
import net.citizensnpcs.api.trait.Trait
import net.citizensnpcs.api.trait.trait.Equipment
import net.citizensnpcs.util.PlayerAnimation
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Animals
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import org.bukkit.entity.ThrownPotion
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType
import java.util.*

class TetuTanukiTrait(private val plugin: JavaPlugin, private val isEnemy: Boolean = false) : Trait("tetu") {
    private val range = RectRange(10.0, 5.0, 10.0)
    private val npcAttackFlagMetadata = NpcAttackFlagMetadata(plugin)

    override fun run() {
        val entity = npc.entity as Player? ?: return
        run(entity)
    }

    private fun run(entity: Player) {
        val state: State? = State.values().firstOrNull { it.key == entity.getStringMetadata(MetadataKeys.STATE) }
        if (entity.isOnGround || entity.isSwimming) {
            when (state) {
                State.IDLE -> {
                    npc.getTrait(Equipment::class.java).apply {
                        set(Equipment.EquipmentSlot.HAND, ItemStack(Material.DIAMOND_SWORD).apply {
                            addEnchantment(Enchantment.DAMAGE_ALL, 1)
                        })
                        set(Equipment.EquipmentSlot.HELMET, null)
                        set(Equipment.EquipmentSlot.CHESTPLATE, null)
                        set(Equipment.EquipmentSlot.LEGGINGS, null)
                        set(Equipment.EquipmentSlot.BOOTS, null)
                    }
                    npc.navigator.localParameters.speedModifier(1.0f)
                    if (entity.health <= 8.0) {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.RUNAWAY.key)
                        return
                    }
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
                    npc.getTrait(Equipment::class.java).apply {
                        set(Equipment.EquipmentSlot.HAND, ItemStack(Material.DIAMOND_SWORD).apply {
                            addEnchantment(Enchantment.DAMAGE_ALL, 1)
                        })
                        set(Equipment.EquipmentSlot.HELMET, ItemStack(Material.DIAMOND_HELMET))
                        set(Equipment.EquipmentSlot.CHESTPLATE, ItemStack(Material.DIAMOND_CHESTPLATE))
                        set(Equipment.EquipmentSlot.LEGGINGS, ItemStack(Material.DIAMOND_LEGGINGS))
                        set(Equipment.EquipmentSlot.BOOTS, ItemStack(Material.DIAMOND_BOOTS))
                    }

                    npc.navigator.localParameters.speedModifier(1.0f)
                    if (entity.health <= 8.0) {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.RUNAWAY.key)
                        return
                    }
                    val currentTargetEntity = entity.getEntityMetadata(MetadataKeys.TARGET_ENTITY)
                    if (currentTargetEntity == null) {
                        val targetEntity: Entity? = getTargetEntity(entity)
                        if (targetEntity == null) {
                            entity.setStringMetadata(plugin, MetadataKeys.STATE, State.IDLE.key)
                        } else {
                            entity.setEntityMetadata(plugin, MetadataKeys.TARGET_ENTITY, targetEntity)
                        }
                    } else {
                        npc.navigator.setTarget(currentTargetEntity.location)
                        if (entity.location.distance(currentTargetEntity.location) < 2.2) {
                            attack(entity, currentTargetEntity)
                        }
                        if (currentTargetEntity.isDead) {
                            entity.removeMetadata(MetadataKeys.TARGET_ENTITY, plugin)
                        }
                        val rand = Random().nextInt(100)
                        if (rand < 2) {
                            entity.removeMetadata(MetadataKeys.TARGET_ENTITY, plugin)
                        }
                    }
                }
                State.RUNAWAY -> {
                    npc.navigator.localParameters.speedModifier(1.2f)
                    npc.getTrait(Equipment::class.java).apply {
                        val itemStack = getHealingPotion()
                        set(Equipment.EquipmentSlot.HAND, itemStack)
                    }
                    if (entity.health >= 18.0) {
                        entity.setStringMetadata(plugin, MetadataKeys.STATE, State.ATTACK.key)
                        return
                    }
                    val targetEntity: Entity? = getTargetEntity(entity)
                    if (targetEntity != null) {
                        val vector = entity.location.subtract(targetEntity.location).toVector().normalize()
                        entity.location.clone().add(vector.multiply(3))
                        npc.navigator.setTarget(targetEntity.location)
                    }
                    heal(entity)
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
            Damage(8.5).deal(entity, targetEntity)
            targetEntity.fireTicks = 100
            val rand = Random().nextInt(10)
            if (rand == 0) {
                FlameBurst(entity, plugin).execute()
            }
        }
        npcAttackFlagMetadata.avoidTwice(entity)
    }

    private fun heal(entity: Entity) {
        if (npcAttackFlagMetadata.getFlag(entity)) {
            return
        }
        val targetLocation = entity.location.clone().add(0.0, 1.0, 0.0).add(entity.location.direction)
        targetLocation.spawn<ThrownPotion> {
            it.item = getHealingPotion()
        }

        npcAttackFlagMetadata.avoidTwice(entity)
    }

    private fun getTargetEntity(entity: Entity): Entity? {
        return if (isEnemy) {
            range.getEntities(entity.location)
                    .filter { it is Player || it is Animals }
                    .filterNot { it is Player && (it.gameMode == GameMode.SPECTATOR || it.gameMode == GameMode.CREATIVE) }
                    .filterNot { it.getBooleanMetadata(MetadataKeys.IS_ENEMY) }
                    .firstOrNull { it != entity }
        } else {
            range.getEntities(entity.location)
                    .filter { it is Mob || it.getBooleanMetadata(MetadataKeys.IS_ENEMY) }
                    .filterNot { it is Player && (it.gameMode == GameMode.SPECTATOR || it.gameMode == GameMode.CREATIVE) }
                    .firstOrNull { it != entity }
        }
    }

    private fun getHealingPotion(): ItemStack {
        val itemStack = ItemStack(Material.SPLASH_POTION)
        val itemMeta = itemStack.itemMeta as PotionMeta
        itemMeta.basePotionData = PotionData(PotionType.INSTANT_HEAL, false, true)
        itemStack.itemMeta = itemMeta
        return itemStack
    }

    private enum class State(val key: String) {
        IDLE("idle"),
        ATTACK("attack"),
        RUNAWAY("runaway");
    }
}