package neo.atlantis.lostadventurer.model.npc

import neo.atlantis.lostadventurer.ext.setBooleanMetadata
import neo.atlantis.lostadventurer.metadata.MetadataKeys
import neo.atlantis.lostadventurer.model.trait.TetuTanukiTrait
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.api.trait.trait.Equipment
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class TetuTanuki : Npc() {
    override val npcName = "TETU_tanuki"
    override val skinUrl = "https://minesk.in/1825286090"
    override val uuid = "239e6ae1-05ab-4476-b834-0eac76767583"
    override val textureData = "ewogICJ0aW1lc3RhbXAiIDogMTYwMDA4OTQyMTExOCwKICAicHJvZmlsZUlkIiA6ICIxNzhmMTJkYWMzNTQ0ZjRhYjExNzkyZDc1MDkzY2JmYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaWxlbnRkZXRydWN0aW9uIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzcxNjcwM2FkZGZjMDBmOTUwZDBjNTQxNmIxMWQzMjI3NmQwYTk5YzhkNzlmMDNiZTc0OTA0NzljNjRmOGZhODgiCiAgICB9CiAgfQp9"
    override val textureSignature = "LRHPC+Ljrk17LT74YXW73aG3UOV0Tnbxp93yiQRJKjTQE17vLXt4f0jSo57rZ+LyYhXegwf+K2QCEp1sM1/neypo+WabTsGFDGPirsO8t80hzg4lu3eT+71OHqymiv6WCx8o3jVs0iA7doLejHE8cmTqxyXtm8SfrlQ5dhVtBoZFDd5clri/CyWqK0N68je8cumeRyKboPgAtg5+Wwd0w1hxhxs8f7pxGouZnRk1yrBZd+0kFDC/20WnWICSy1Wk2qfjgs4Vehc7iEHIFD0OojDNnePrAaKz1b10kIIwPaplsr5z/g8qBVKKFqqTkNw9KleFTG21ricgNpbG2J18gTCKmmr55GgOGFfsy0iIg5f8hgHgfepd8dbDoR7jWJQwY8AAkUnXyR3/lVnTmD3nZ+R6UplU//aNeC3KHtsJG/iNwns8LuJrZ3xlgIOfdN8EeVdiyFC2cgVX5wXCRISHjWQW44t6jtu10fzcgOc7dj/zDbegwEhpiN7E3Ls0+SAL0BLhhnnIt+XPcRvrKtTciY+l0v8qfATxsNLDmxEZmo5z+aFi/lxd+yGha9jab/Ua2aoIVpUBPdoYH7aDuaIJdebwS+ARIIxkwwBTNjl9m73DYo+b8+fgNoSzsuinbgE4ErMHkNfLtZuXJqbnkqSfKJCBTwp5iskXaGtJipm2xm8="

    override fun onCreate(npc: NPC, entity: Entity, plugin: JavaPlugin) {
        npc.getTrait(Equipment::class.java).apply {
            set(Equipment.EquipmentSlot.HAND, ItemStack(Material.DIAMOND_SWORD).apply {
                addEnchantment(Enchantment.DAMAGE_ALL, 1)
            })
        }
        npc.addTrait(TetuTanukiTrait(plugin))
        entity.setBooleanMetadata(plugin, MetadataKeys.IS_ENEMY, false)
    }
}