package neo.atlantis.lostadventurer.command

import neo.atlantis.lostadventurer.model.npc.DraculaTubu
import neo.atlantis.lostadventurer.model.npc.Gm10
import neo.atlantis.lostadventurer.model.npc.TekiTanuki
import neo.atlantis.lostadventurer.model.npc.TetuTanuki
import neo.atlantis.lostadventurer.model.npc.Tubu
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class SpawnCommand(private val plugin: JavaPlugin) : BaseCommand() {
    override fun onCommandByPlayer(player: Player, command: Command, label: String, args: CommandArgs): Boolean {
        when (args[0]) {
            "tubu" -> Tubu().create(player.location, plugin)
            "dtubu" -> DraculaTubu().create(player.location, plugin)
            "gm" -> Gm10().create(player.location, plugin)
            "tetu" -> TetuTanuki().create(player.location, plugin)
            "teki" -> TekiTanuki().create(player.location, plugin)
        }

        return true
    }

    override fun onCommandByOther(sender: CommandSender, command: Command, label: String, args: CommandArgs): Boolean {
        return false
    }
}