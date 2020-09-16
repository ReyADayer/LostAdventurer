package neo.atlantis.lostadventurer

import neo.atlantis.lostadventurer.command.ItemCommand
import neo.atlantis.lostadventurer.command.SpawnCommand
import neo.atlantis.lostadventurer.ext.initCommand
import neo.atlantis.lostadventurer.ext.registerListener
import neo.atlantis.lostadventurer.listener.ItemListener
import neo.atlantis.lostadventurer.listener.NpcListener
import org.bukkit.permissions.PermissionDefault
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.command.Command
import org.bukkit.plugin.java.annotation.command.Commands
import org.bukkit.plugin.java.annotation.dependency.Dependency
import org.bukkit.plugin.java.annotation.dependency.DependsOn
import org.bukkit.plugin.java.annotation.permission.Permission
import org.bukkit.plugin.java.annotation.permission.Permissions
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.author.Author

@Plugin(name = "LostAdventurer", version = "1.0-SNAPSHOT")
@Author("ReyADayer")
@ApiVersion(ApiVersion.Target.v1_15)
@DependsOn(
        Dependency("Citizens")
)
@Permissions(
        Permission(
                name = PluginPermissions.ADMIN,
                desc = "Gives access to LostAdventurer admin commands",
                defaultValue = PermissionDefault.OP
        )
)
@Commands(
        Command(
                name = PluginCommands.SPAWN,
                permission = PluginPermissions.ADMIN,
                desc = "spawn command",
                usage = "/<command>"
        ),
        Command(
                name = PluginCommands.ITEM,
                permission = PluginPermissions.ADMIN,
                desc = "item command",
                usage = "/<command>"
        )
)
class LostAdventurer : JavaPlugin() {
    override fun onEnable() {
        initCommand(PluginCommands.SPAWN, SpawnCommand(this))
        initCommand(PluginCommands.ITEM, ItemCommand())

        registerListener(ItemListener(this))
        registerListener(NpcListener(this))
    }

    override fun onDisable() {
    }
}