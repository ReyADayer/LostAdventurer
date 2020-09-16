package neo.atlantis.lostadventurer.command

import neo.atlantis.lostadventurer.model.item.DraculaTubuSpawnEgg
import neo.atlantis.lostadventurer.model.item.GmSpawnEgg
import neo.atlantis.lostadventurer.model.item.TekiTanukiSpawnEgg
import neo.atlantis.lostadventurer.model.item.TetuTanukiSpawnEgg
import neo.atlantis.lostadventurer.model.item.TubuSpawnEgg
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ItemCommand : BaseCommand() {
    override fun onCommandByPlayer(player: Player, command: Command, label: String, args: CommandArgs): Boolean {
        player.inventory.run {
            addItem(TubuSpawnEgg().toItemStack(64))
            addItem(DraculaTubuSpawnEgg().toItemStack(64))
            addItem(TetuTanukiSpawnEgg().toItemStack(64))
            addItem(TekiTanukiSpawnEgg().toItemStack(64))
            addItem(GmSpawnEgg().toItemStack(64))
        }

        return true
    }

    override fun onCommandByOther(sender: CommandSender, command: Command, label: String, args: CommandArgs): Boolean {
        return false
    }
}