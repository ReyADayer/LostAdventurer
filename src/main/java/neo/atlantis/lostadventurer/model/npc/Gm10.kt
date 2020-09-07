package neo.atlantis.lostadventurer.model.npc

import neo.atlantis.lostadventurer.ext.setBooleanMetadata
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import neo.atlantis.lostadventurer.model.trait.GmTrait
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.api.trait.trait.Equipment
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Gm10 : Npc() {
    override val npcName = "Gm10"
    override val skinUrl = "https://minesk.in/704698407"

    override fun onCreate(npc: NPC, entity: Entity, plugin: JavaPlugin) {
        npc.getTrait(Equipment::class.java).apply {
            set(Equipment.EquipmentSlot.HAND, ItemStack(Material.DIAMOND_SWORD))
        }
        npc.addTrait(GmTrait(plugin, 1.0f))

        entity.setBooleanMetadata(plugin, MetadataKeys.IS_ENEMY, false)
    }
}

