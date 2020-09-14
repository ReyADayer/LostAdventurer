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
    override val uuid = "f415bb1e-6bb1-4c81-b751-0408abc26b45"
    override val textureData = "ewogICJ0aW1lc3RhbXAiIDogMTU5OTI5OTgzMTY3NCwKICAicHJvZmlsZUlkIiA6ICI3NTE0NDQ4MTkxZTY0NTQ2OGM5NzM5YTZlMzk1N2JlYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGFua3NNb2phbmciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTdhOGUzNmM5OTBiZmQ5OWQzNjBjYjUyZDllYmJjNmUwMGJlOWQ5ZjQ1NWE2ODM5NDUyNzIwODZiYjEzNjQzNCIKICAgIH0KICB9Cn0="
    override val textureSignature = "kXAMj/Z39MeTsXcxhdiSPVRttDz352K6Qzr03Sjrjo1ptrXqhiFRovpm2F83PeZQg9GxIK9bpuN20x9IJ1esnVsBAwythnNKfBRLVVJPgj/fgHRm6t4uJMvlK27C4o/WPtoGnbnhkyAngFv1bca4rgjfORiljy5ukglQjrl8Du8efubp2snIJEFPCU2x0EGngJA7QEAsjtsl2cXcOh4eSkB8/q7rtujhqq/+kELZ0a77j2AJ6ap3r3Ruyoy+GCJ+nCtoaXtPZ+ggRFQqCDlRAQRQSh48P5x/vJ7Vjgj6aCiYgW5XESphnr5bZmIlISTp94bKdQla0VIU87yIRMFOyQGM80EmHb8f9wTfewiZs7Zjsp09EXOqWnFgCzlfkelsNdZ+mqzCSzI9+CWq84q0fYO+9IP9lUWaNR3GMw3c0ULXxKz2HlYfZSoeSlhpKO3esnzFtYyfoK6tCnlUarhUcTXmnrIbT8XUQogFdCFvzi9yqFUvR6waCxM2EJBZ8yg7+I7lLfHhyvm4/M9OMBejM38EnPIUvhFa/IkaJqX52K0Cd0ax6oHr3pscN+H09Qp6NA0v7NPLalJjiFRE7Rbj9+z3gJLPzY0yHDILmastaMtE8Kl0vgm5lgxO28INB9ov3mym0q+rxourvCq0uEKNEJ4ivtMLyAyS0AY1+KnyzVo="

    override fun onCreate(npc: NPC, entity: Entity, plugin: JavaPlugin) {
        npc.getTrait(Equipment::class.java).apply {
            set(Equipment.EquipmentSlot.HAND, ItemStack(Material.TNT))
        }
        npc.addTrait(TubuTrait(plugin, 2.0f))
        entity.setBooleanMetadata(plugin, MetadataKeys.IS_ENEMY, true)
    }
}