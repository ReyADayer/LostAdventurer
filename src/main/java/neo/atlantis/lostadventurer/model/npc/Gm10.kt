package neo.atlantis.lostadventurer.model.npc

import neo.atlantis.lostadventurer.ext.setBooleanMetadata
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import neo.atlantis.lostadventurer.model.trait.GmTrait
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.api.trait.trait.Equipment
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Gm10 : Npc() {
    override val npcName = "Gm10"
    override val skinUrl = "https://minesk.in/704698407"
    override val uuid = "4ae9ee56-4763-4099-9af1-6c26007b0cca"
    override val textureData = "ewogICJ0aW1lc3RhbXAiIDogMTU5OTQ2ODUxNTQxMywKICAicHJvZmlsZUlkIiA6ICI3MmNiMDYyMWU1MTA0MDdjOWRlMDA1OTRmNjAxNTIyZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNb3M5OTAiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgxY2QwNTZhMGU1MjE1YTkzZDczMWQwY2Q3NjE3ZDhlNWNkMzY4MDBiYzUzZWYwOTllNzFiYjMzZmI3NjdkZCIKICAgIH0KICB9Cn0="
    override val textureSignature = "vsDA9zuIuStBd966d7QiynAaV3Txnegbo4ERrQhAySuEsciyEXufTvIJ3qI1hNArvkAAvW59TZ6BBERTV57H/wyTwVLJiktwM381m7FJB13s8DZzN6Xej20W4meiM8wsOjXmTjGOmYbP4ra6R4W2/y42FBin7BavplzQBFKW5a/Xs+jkkd/oGknG7iZ/ygNTbxD2rJvTK3zsG5/qAs50Gd5IELjlJ+hMvp0m6Qf0tj5tSokgJwk4Lstn/96/VJorBbNLLsx3WE07HuK2Pk91msqi7EiydDJa61eeNRhTK17LwTgZxWq4ujNhE6XgZb0LX3wPUrxazQza31rGcl5/TJqi95IN5RMAXHq68yZhnDdstDNRDnb+Y9H7Reipb4i873kx1vNk6I8O2yjNsXQb7EV6RmhxVaM9Es7cIRNFO4LHmQwgpEPZ/6G6PtAbTvUG9lEunjNfxFGvqDUIyBriFh1k6OHlewnrcxxBxqyaKgVRK0V4cISBeMxDAdq2X10IbP/3S5K/9xla02j9Cir42tmF3/TDzgLi4FBheZOvUsdKkm4LURcB8v6SMa6TL5qkXPMQcFwaS6hDC6AqPDT5rdImIe6F2OUncuGIo+OqS12v4jXUrlvlR6pSnicnzYMCHpJYFj5Q8xqro13ElPFfLVF1rIrZAvMcnYbClCLVwgU="

    override fun onCreate(npc: NPC, entity: Entity, plugin: JavaPlugin) {
        npc.getTrait(Equipment::class.java).apply {
            set(Equipment.EquipmentSlot.HAND, ItemStack(Material.DIAMOND_SWORD))
        }
        npc.addTrait(GmTrait(plugin, 1.0f))

        entity.setBooleanMetadata(plugin, MetadataKeys.IS_ENEMY, false)
    }
}

