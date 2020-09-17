package neo.atlantis.lostadventurer.model.npc

import neo.atlantis.lostadventurer.ext.setBooleanMetadata
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import neo.atlantis.lostadventurer.model.trait.NegiTrait
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.api.trait.trait.Equipment
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.Banner
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.plugin.java.JavaPlugin

class Negi556 : Npc() {
    override val npcName = "negi556"
    override val skinUrl = "https://minesk.in/120598440"
    override val uuid = "0bd0e8d0-38bd-44f4-a596-61a463a6c2fe"
    override val textureData = "ewogICJ0aW1lc3RhbXAiIDogMTYwMDM0MzY2OTQzMCwKICAicHJvZmlsZUlkIiA6ICJmZDYwZjM2ZjU4NjE0ZjEyYjNjZDQ3YzJkODU1Mjk5YSIsCiAgInByb2ZpbGVOYW1lIiA6ICJSZWFkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2VmOTU4NTc2YzM4Y2MxNDUwY2I3OWM1MjhhOGNlOWZkY2ZlYzdkY2U3OGY1NzdhNmMyMzgzN2RiMWRjMTdiZDIiCiAgICB9CiAgfQp9"
    override val textureSignature = "QZHopr+F/SG0DY1MalsSMMikap/jk+2olgwN/ywWuMGBvqWs9n5KNTnAJaypDgk+nG5tzWFlckwoZJg/Nm3ntRU9+rf0x4wGt6K0owVslYxXU/5cFbbkMrdf1Lrr3OHVUPFSiA6tOPl6MjHh3+0/0Inr5NtiL7lajNgeLCIgw9Pv09CqlrBlbUbg5lTW985We75LOjhGTJ1xP4Zj/YFkDNQysskzjhzGQOYkEpRpqoSY+U54tzjttGGlKeXvXQ2yGc4e5b9kUzg3ngz+8eiv42NWtWb0kVS0ASbzad8vgs51JXuH5sC9Fy2Ee1p1FoFhFQmOVB+YtlcILdGjQiCDgP7HZZNaXGT/p7KCzIPQh2UD9OmXOWyJBAkpDZ6Ek7q8hOENBUJv/iakXX7r78wlrxdn8dUxsZeOK067hky5lJGfBHsWAooFZIYWZez8pvRrv7IvHFX+6XegEiGjZfgQhzgKF/1GKnpRNlVnjVkIql5seBwTw4808iqn3OYbmWnluen19tt8bg+UvBZ2gN5HqvcWF527Q/Elwu/irLGW4+kjNaQggyROy2acjGJ8VIbXbgjuw80hZrWZgcVqVcCBWnnmz+jQfo+WMXJVqH3Qta0X00Ty2MtYvPjkET6wGIFG+PmVPZroaUgAHemVXvd2JDL0bNcJlMMCgR3WD7fkvYc="

    private val patterns = listOf(
            Pattern(DyeColor.BLACK, PatternType.BORDER),
            Pattern(DyeColor.LIME, PatternType.STRIPE_TOP),
            Pattern(DyeColor.LIME, PatternType.HALF_HORIZONTAL_MIRROR),
            Pattern(DyeColor.YELLOW, PatternType.STRIPE_MIDDLE)
    )

    override fun onCreate(npc: NPC, entity: Entity, plugin: JavaPlugin) {
        npc.getTrait(Equipment::class.java).apply {
            set(Equipment.EquipmentSlot.HAND, getBanner())
            set(Equipment.EquipmentSlot.OFF_HAND, getShield())
        }
        npc.addTrait(NegiTrait(plugin, 1.3f))

        entity.setBooleanMetadata(plugin, MetadataKeys.IS_ENEMY, true)
    }

    private fun getBanner(): ItemStack {
        val itemStack = ItemStack(Material.LIME_BANNER)
        val itemMeta = itemStack.itemMeta as BannerMeta
        patterns.forEach {
            itemMeta.addPattern(it)
        }
        itemStack.itemMeta = itemMeta
        return itemStack
    }

    private fun getShield(): ItemStack {
        val itemStack = ItemStack(Material.SHIELD)
        val blockStateMeta = itemStack.itemMeta as BlockStateMeta
        val banner = blockStateMeta.blockState as Banner
        banner.baseColor = DyeColor.LIME
        banner.patterns = patterns
        banner.update()
        blockStateMeta.blockState = banner
        itemStack.itemMeta = blockStateMeta
        return itemStack
    }
}