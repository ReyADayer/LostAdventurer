package neo.atlantis.lostadventurer.model.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class DraculaTubuSpawnEgg : Item() {
    override val name = "ドラキュラつぶ貝"
    override val description = "右クリックでドラキュラつぶ貝をスポーンします"
    override val itemStack = ItemStack(Material.WITCH_SPAWN_EGG)
}