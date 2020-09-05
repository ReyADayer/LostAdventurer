package neo.atlantis.lostadventurer.model.item

import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

abstract class Item {
    abstract val name: String
    abstract val description: String
    abstract val itemStack: ItemStack

    fun toItemStack(): ItemStack {
        val resultItemStack = itemStack
        val itemMeta = resultItemStack.itemMeta
        itemMeta?.setDisplayName(name)
        itemMeta?.lore = listOf(description)
        itemMeta?.isUnbreakable = true
        itemMeta?.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS)
        resultItemStack.itemMeta = itemMeta
        return resultItemStack
    }

    fun toItemStack(amount: Int): ItemStack {
        val itemStack = toItemStack()
        itemStack.amount = amount
        return itemStack
    }
}