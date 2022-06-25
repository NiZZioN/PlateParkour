package me.nizzion.parkour

import me.nizzion.parkour.events.EventManager
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.cmds.CommandManager
import org.bukkit.plugin.java.JavaPlugin

class Parkour : JavaPlugin() {
    override fun onEnable() {
        instance = this
        config.options().copyDefaults()
        saveDefaultConfig()


        ParkourConfig.init()
        ParkourConfig.cFile.addDefault("parkour", "{}")
        ParkourConfig.cFile.options().copyDefaults(true)
        ParkourConfig.save()

        ParkourConfig.load()

        ItemManager.hasParkourStartOnLoad()
        ItemManager()
        EventManager().registerEvents()
        CommandManager().registerCommands()
    }

    override fun onDisable() {
    }

    companion object {
        lateinit var instance: Parkour
    }
}