package me.nizzion.parkour.util.cmds

import me.nizzion.parkour.Parkour
import org.bukkit.entity.Player

class Delete {
    fun deleteStart (p: Player){
        Set.hasParkour.remove(p.uniqueId)
        Parkour.instance.logger.info("Successfully deleted Parkour-start for ${p.name}")
    }
}