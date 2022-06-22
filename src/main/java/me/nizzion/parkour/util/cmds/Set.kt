package me.nizzion.parkour.util.cmds

import me.nizzion.parkour.Parkour
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

class Set(
    private var xPos : Double,
    private var yPos : Double,
    private var zPos : Double,
    private var yaw  : Float,
    private var pitch: Float
    ) {

    companion object {
        val hasParkour: HashMap<UUID, Set> = HashMap()

        fun setValues(p: Player){
            if(hasParkour.containsKey(p.uniqueId)){
                Parkour.instance.logger.info("Player ${p.name} already has a parkour set.")
                Parkour.instance.logger.info(
                    "${p.name} Parkour:" +
                            " x ${hasParkour[p.uniqueId]?.xPos}"  +
                            " y ${hasParkour[p.uniqueId]?.yPos}"  +
                            " z ${hasParkour[p.uniqueId]?.zPos} " +
                            " yaw ${hasParkour[p.uniqueId]?.yaw}" +
                            " pitch ${hasParkour[p.uniqueId]?.pitch}"
                )
            } else {
                hasParkour[p.uniqueId] = Set(p.location.x,
                    p.location.y,
                    p.location.z,
                    p.location.yaw,
                    p.location.pitch
                )
                Parkour.instance.logger.info(
                    "You set:" +
                            " x ${hasParkour[p.uniqueId]?.xPos}"  +
                            " y ${hasParkour[p.uniqueId]?.yPos}"  +
                            " z ${hasParkour[p.uniqueId]?.zPos}"  +
                            " yaw ${hasParkour[p.uniqueId]?.yaw}" +
                            " pitch ${hasParkour[p.uniqueId]?.pitch}"
                )
                return
            }
        }
    }
}