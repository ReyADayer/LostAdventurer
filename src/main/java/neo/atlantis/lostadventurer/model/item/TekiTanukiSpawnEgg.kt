package neo.atlantis.lostadventurer.model.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TekiTanukiSpawnEgg : Item() {
    override val name = "鉄狸（敵対）"
    override val description = "右クリックで敵対的な鉄狸をスポーンします"
    override val itemStack = ItemStack(Material.GLOWSTONE_DUST)
}