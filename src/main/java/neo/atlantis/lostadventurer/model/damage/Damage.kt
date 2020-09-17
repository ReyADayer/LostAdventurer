package neo.atlantis.lostadventurer.model.damage

import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity

class Damage(private val value: Double) {
    fun deal(entity: LivingEntity, targetEntity: LivingEntity) {
        val armor = targetEntity.getAttribute(Attribute.GENERIC_ARMOR)?.value ?: 0.0
        val toughness = targetEntity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS)?.value ?: 0.0
        val damage: Double = value * (1 - Math.min(20.0, Math.max(armor / 5, armor - value / (2 + toughness / 4))) / 25)
        targetEntity.damage(damage, entity)
    }
}