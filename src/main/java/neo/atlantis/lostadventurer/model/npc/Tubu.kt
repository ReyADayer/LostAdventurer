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

class Tubu : Npc() {
    override val npcName = "tubugai_893"
    override val skinUrl = "https://minesk.in/1249698035"
    override val uuid = "bc901f05-59ca-4479-969a-8ff54c937a56"
    override val textureData = "ewogICJ0aW1lc3RhbXAiIDogMTU5OTI3Njk0MTQ2NCwKICAicHJvZmlsZUlkIiA6ICJmNjE1NzFmMjY1NzY0YWI5YmUxODcyMjZjMTEyYWEwYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJGZWxpeF9NYW5nZW5zZW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWY4NzAyNDM2MDZhNTI2NGMzYjNkMDY2MWI0ZjhiMjgwNzIxNzA3YjJjNTJjZDc1ZjM2NTE0MTg0ZmNmMzA3YyIKICAgIH0KICB9Cn0="
    override val textureSignature = "s9S4uEYHqYIFx08owkCWbLsTysU17HHzgDsYQ+xaY+GV8cqgy6WnFz7JR+gsFEWk1pPUE6r2gbza5pjEK1sOrU4U3j3aHRWZcO5nhDte/1w9CzEhkVmiWB/7z818B6kg8pDXVfxCV7uLC/yisOoII1QVayHYdRt40uqoSa2cHYqXkiRHLbVIgCKsQZh54IwnYL7+kp6xALHl+KRrgdpCuFIPzUsHdOtnCa7CO5hpjiNeMmt4JNK5u0aZAHBWrNx2/QQsb5exS4WLryOQN2+UT/W2vsl9wiYJ/gpjVRn5LMhXMaU02mWHQ1/NVDrP5hg0AaVyXCURu1bUQp7kcsTBSDKhjV7oM2DCF9pY/736z8dsylgJqeDU7vMvG0Mn/s3wbNaymjdn0vmhwFtnew5ui5KnhpWaidwKUmABKzA3obOPgHKhUseu9INuNY11duYxOGIcaXY6fSKrPFezzHKIFpQhMgetfg/ooDAaAJADs5LQwKjt+VAjqPrLwKY+i/UhnmpHtvL1epG0zomjAeOPBFs/YMExM/dwdzUTo25Qy2I2XHUa0MbVW01yGLmI5nIZ8u+R09+yCrzb/51B8DQlWk6MqHIzSbuwexuUeVV4dlCGEeAj1T9ryNi8n4xGeC3MJmbDG1dlUiDBESa11rcbvNwuY/wh/3zwiO5od3nueBU="

    override fun onCreate(npc: NPC, entity: Entity, plugin: JavaPlugin) {
        npc.getTrait(Equipment::class.java).apply {
            set(Equipment.EquipmentSlot.HAND, ItemStack(Material.TNT))
        }
        npc.addTrait(TubuTrait(plugin))
        entity.setBooleanMetadata(plugin, MetadataKeys.IS_ENEMY, true)
    }
}