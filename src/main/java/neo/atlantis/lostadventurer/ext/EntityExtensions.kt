package neo.atlantis.lostadventurer.ext

import org.bukkit.entity.Entity

fun Entity.isCitizensNPC(): Boolean = this.hasMetadata("NPC")
