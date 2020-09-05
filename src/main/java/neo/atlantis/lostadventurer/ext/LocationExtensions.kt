package neo.atlantis.lostadventurer.ext

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.FallingBlock
import java.util.*

fun Location.spawnEntity(entityType: EntityType): Entity? = world?.spawnEntity(this, entityType)

fun Location.spawnFallingBlock(material: Material): FallingBlock? {
    return world?.spawnFallingBlock(this, material.createBlockData())
}

inline fun <reified T : Entity> Location.spawn(noinline function: (entity: T) -> Unit): T? = world?.spawn<T>(this, T::class.java, function)

fun Location.spawnParticle(particle: Particle, count: Int) {
    this.world?.spawnParticle(particle, this, count, 0.0, 0.0, 0.0, 0.0)
}

fun Location.spawnParticle(particle: Particle, count: Int, dustOptions: Particle.DustOptions) {
    this.world?.spawnParticle(particle, this, count, dustOptions)
}

fun Location.playSound(sound: Sound, volume: Float, pitch: Float) {
    this.world?.playSound(this, sound, volume, pitch)
}

fun Location.random(x: Double, y: Double, z: Double): Location {
    return clone().add((Random().nextDouble() - 0.5) * x, (Random().nextDouble() - 0.5) * y, (Random().nextDouble() - 0.5) * z)
}