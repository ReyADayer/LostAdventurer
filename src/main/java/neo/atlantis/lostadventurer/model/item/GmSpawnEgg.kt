package neo.atlantis.lostadventurer.model.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class GmSpawnEgg : Item() {
    override val name = "Gm10"
    override val description = "右クリックでGm10をスポーンします"
    override val itemStack = ItemStack(Material.REDSTONE)
}