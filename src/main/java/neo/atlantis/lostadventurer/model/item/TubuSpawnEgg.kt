package neo.atlantis.lostadventurer.model.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TubuSpawnEgg : Item() {
    override val name = "つぶ貝"
    override val description  = "右クリックでつぶ貝をスポーンします"
    override val itemStack = ItemStack(Material.PHANTOM_SPAWN_EGG)
}