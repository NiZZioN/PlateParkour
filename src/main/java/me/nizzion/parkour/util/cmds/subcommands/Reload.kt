package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig

object Reload {
    fun reloadConfig(){
        ParkourConfig.reload()
        Parkour.instance.logger.info("You successfully reloaded ${ParkourConfig.cFile.name}")
    }
}