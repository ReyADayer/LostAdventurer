package neo.atlantis.lostadventurer.model.skill

import io.reactivex.Observable
import neo.atlantis.lostadventurer.ext.convertToPolarCoordinates
import neo.atlantis.lostadventurer.ext.playSound
import neo.atlantis.lostadventurer.ext.spawnParticle
import neo.atlantis.lostadventurer.model.range.Range
import neo.atlantis.lostadventurer.model.range.RectRange
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

class FlameBurst(private val livingEntity: LivingEntity, private val plugin: JavaPlugin) : Skill(livingEntity, plugin) {

    private val radius = 2.0
    private val range = RectRange(0.5, 0.5, 0.5)

    override fun execute() {
        val location = livingEntity.location
        Observable.interval(10, TimeUnit.MILLISECONDS)
                .take(120)
                .doOnNext {
                    val angle = 2 * Math.PI * it / 30
                    val circleRadius = radius + it / 60.0
                    drawCircle(location, circleRadius, angle)
                    drawCircle(location, circleRadius, angle + Math.PI / 2)
                    drawCircle(location, circleRadius, angle + 2 * Math.PI / 2)
                    drawCircle(location, circleRadius, angle + 3 * Math.PI / 2)
                    drawAura(location, it)
                }.subscribe()
    }

    private fun effect(location: Location, range: Range) {
        object : BukkitRunnable() {
            override fun run() {
                range.getEntities(location).forEach {
                    if (it is LivingEntity) {
                        it.damage(3.0, livingEntity)
                        it.fireTicks = 60
                    }
                }
            }
        }.runTaskLater(plugin, 1)
    }

    private fun drawCircle(location: Location, radius: Double, angle: Double) {
        val currentLocation = location.convertToPolarCoordinates(radius, angle)
        currentLocation.spawnParticle(Particle.SPELL_INSTANT, 10)
    }

    private fun drawAura(location: Location, data: Long) {
        val radius = data / 10
        if (data == 30.toLong()) {
            location.playSound(Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 0.933f)
        }

        val count = 3
        for (i in 0..count) {
            for (j in 0..count) {
                val angle1 = 2.0 * Math.PI * Random().nextInt(360).toDouble() / 360.0
                val angle2 = 2.0 * Math.PI * Random().nextInt(360).toDouble() / 360.0
                val x = radius * sin(angle1) * cos(angle2)
                val y = radius * sin(angle1) * sin(angle2)
                val z = radius * cos(angle1)
                val currentLocation = location.clone().add(x, y, z)
                effect(currentLocation, range)
                if (data in 30..100) {
                    currentLocation.spawnParticle(Particle.FLAME, 1)
                }
            }
        }
    }
}