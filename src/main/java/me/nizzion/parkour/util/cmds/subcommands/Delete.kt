package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.util.Helper
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.*

class Delete {
    fun delete(p: Player) {
        val startPos = ParkourConfig.cFile.getConfigurationSection("parkour.start.${p.uniqueId}")?.getKeys(false)

        if (startPos == null || startPos.size == 0) {
            p.sendMessage(
                text()
                    .append(Helper.prefix)
                    .append(text(" No parkours set!", NamedTextColor.RED))
                    .build()
            )
            return
        }

        startPos.forEach {id ->
            ParkourConfig.cFile.getConfigurationSection("parkour.start.${p.uniqueId}.$id")?.getKeys(false)?.forEach { value ->
                Parkour.instance.logger.info("Should be loc data: $value")
            }
        }

        p.sendMessage(text("Delete which?: ", NamedTextColor.DARK_AQUA))
        startPos.forEach { id ->
            val showCaseID = id.filter { it.isDigit() }
            Parkour.instance.logger.info("Claimid: $id")

            p.sendMessage(
                text()
                    .append(text("  Parkour at:", NamedTextColor.GRAY))
                    .append(text(" $showCaseID", NamedTextColor.AQUA))
                    .clickEvent(
                        ClickEvent.clickEvent(
                            ClickEvent.Action.RUN_COMMAND,
                            "/pk delete $id"
                        )
                    )
                    .hoverEvent(
                        HoverEvent.hoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            text()
                                .append(text("Delete parkour at ", NamedTextColor.GRAY))
                                .append(text(showCaseID, NamedTextColor.AQUA))
                                .build()
                        )
                    )
            )
        }
    }

    fun deleteStart(uuid: UUID, claimID: String) {
        val id = checkIfNumeral(claimID)
        if(!checkIfClaimExists(uuid, id)) return

        ParkourConfig.cFile.set("parkour.start.$uuid.$id", null)
        ParkourConfig.cFile.save(ParkourConfig.file)
        Set.hasParkour.remove(uuid)

        locallogger(uuid," Successfully deleted Parkour-start on claim: ", id)
    }

    private fun checkIfNumeral(claimID: String): String {
        if(claimID.all { c: Char -> c.isDigit() }){
            return "claim_$claimID"
        }
        return claimID
    }

    private fun checkIfClaimExists(uuid: UUID, claimID: String): Boolean {
        var id = claimID
        val claimExists = ParkourConfig.cFile.getConfigurationSection("parkour.start.$uuid.$id")?.getKeys(false)
        if(claimExists == null || claimExists.size == 0){
            id = claimID.filter { it.isDigit() }
            locallogger(uuid," Claim with this ID doesn't exist! try again! ", id)
            return false
        }
        return true
    }

    private fun locallogger (uuid: UUID, message: String, claimID: String){
        val id = claimID.filter { it.isDigit() }
        Parkour.instance.server.getPlayer(uuid)?.sendMessage(
            text()
                .append(Helper.prefix)
                .append(text(message, NamedTextColor.RED))
                .append(text(id, NamedTextColor.AQUA))
                .build()
        )

        Parkour.instance.logger.info("$message $id")
    }
}