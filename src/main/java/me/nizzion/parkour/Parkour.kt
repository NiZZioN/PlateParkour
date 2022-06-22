package me.nizzion.parkour

import me.nizzion.parkour.util.cmds.CommandManager
import org.bukkit.plugin.java.JavaPlugin

class Parkour : JavaPlugin() {
    override fun onEnable() {
        instance = this
        CommandManager().registerCommands()
    }

    override fun onDisable() {
    }

    companion object {
        lateinit var instance:Parkour
    }
}