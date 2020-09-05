package neo.atlantis.lostadventurer.model.npc

import neo.atlantis.lostadventurer.ext.setBooleanMetadata
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import neo.atlantis.lostadventurer.model.trait.TubuTrait
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.api.trait.trait.Equipment
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class DraculaTubu : Npc() {
    override val npcName = "tubugai_893"
    override val skinUrl = "https://mineskin.org/968840135"

    override fun onCreate(npc: NPC, entity: Entity, plugin: JavaPlugin) {
        npc.getTrait(Equipment::class.java).apply {
            set(Equipment.EquipmentSlot.HAND, ItemStack(Material.TNT))
        }
        npc.addTrait(TubuTrait(plugin, 2.0f))
        entity.setBooleanMetadata(plugin, MetadataKeys.IS_ENEMY, true)
    }
}