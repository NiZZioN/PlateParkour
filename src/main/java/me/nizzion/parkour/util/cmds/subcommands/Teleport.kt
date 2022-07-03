package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import org.bukkit.entity.Player
import java.io.IOException

object Teleport{
    fun toStart(p: Player, claimID: String) {
        try {
            ParkourConfig.reload()
            val startPos = ParkourConfig.cFile.getLocation("parkour.${p.uniqueId}.$claimID.start")
            if(startPos == null) {
                p.sendMessage("Start pos not found!")
                return
            }
            p.teleportAsync(startPos)
            p.sendMessage("You've been ported to the Parkour start!")
            return
        } catch (e: IOException) {
            Parkour.instance.logger.warning("IoException: ${e.message}")
        }
    }

    fun toFinish(p: Player, claimID: String) {
        try {
            ParkourConfig.reload()
            val finishPos = ParkourConfig.cFile.getLocation("parkour.${p.uniqueId}.$claimID.finish")
            if(finishPos == null) {
                p.sendMessage("Finish pos not found!")
                return
            }
            p.teleportAsync(finishPos)
            p.sendMessage("You've been ported to the Parkour finish!")
            return
        } catch (e: IOException) {
            Parkour.instance.logger.warning("IoException: ${e.message}")
        }
    }
}