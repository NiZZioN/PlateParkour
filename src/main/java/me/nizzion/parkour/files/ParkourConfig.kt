package me.nizzion.parkour.files

import me.nizzion.parkour.Parkour
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.util.UUID

object ParkourConfig {
    lateinit var cFile: FileConfiguration
    lateinit var file: File

    fun init() {
        file = File(Bukkit.getServer().pluginManager.getPlugin("Parkour")?.dataFolder, "parkour_cfg.yml")
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                Parkour.instance.logger.warning(e.message)
            }
        }
        cFile = YamlConfiguration.loadConfiguration(file)
    }

    fun save() {
        try {
            cFile.save(file)
        } catch (e: IOException) {
            Parkour.instance.logger.warning("Couldn't save file")
        }
    }

    fun getStart(uuid: UUID, claimID: String): Location? {
        return cFile.getLocation("parkour.$uuid.$claimID.start")
    }

    fun getFinish(uuid: UUID, claimID: String): Location? {
        return cFile.getLocation("parkour.$uuid.$claimID.finish")
    }

    fun reload() {
        cFile = YamlConfiguration.loadConfiguration(file)
    }

    fun load() {
//        val test = cFile.getConfigurationSection("parkour")?.getKeys(false)
//        test?.forEach {
//            Parkour.instance.logger.info("This is ${cFile.getConfigurationSection("parkour.$it")?.getKeys(true)}")
//        }
    }
}