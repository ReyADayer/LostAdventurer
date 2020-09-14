package neo.atlantis.lostadventurer.model.npc

import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.trait.SkinTrait
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin

abstract class Npc {
    abstract val npcName: String
    abstract val skinUrl: String

    abstract val uuid: String
    abstract val textureData: String
    abstract val textureSignature: String

    abstract fun onCreate(npc: NPC, entity: Entity, plugin: JavaPlugin)

    fun create(location: Location, plugin: JavaPlugin): Entity {
        val npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "dummy").apply {
            isProtected = false
            val skinTrait: SkinTrait = getTrait(SkinTrait::class.java)
            skinTrait.setFetchDefaultSkin(false)
            skinTrait.setShouldUpdateSkins(false)
            skinTrait.setSkinPersistent(uuid, textureSignature, textureData)
            name = npcName
        }
        npc.spawn(location)
        val entity = npc.entity
        onCreate(npc, entity, plugin)
        return entity
    }

    private fun setSkin(npc: NPC, url: String, plugin: JavaPlugin) {
        val server = plugin.server
        server.dispatchCommand(server.consoleSender, "npc select ${npc.id}")
        server.dispatchCommand(server.consoleSender, "npc skin --url $url")
    }
}