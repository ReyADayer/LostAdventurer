package neo.atlantis.lostadventurer.model.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TetuTanukiSpawnEgg : Item() {
    override val name = "鉄狸（友好）"
    override val description = "右クリックで友好的な鉄狸をスポーンします"
    override val itemStack = ItemStack(Material.GLOWSTONE_DUST)
}