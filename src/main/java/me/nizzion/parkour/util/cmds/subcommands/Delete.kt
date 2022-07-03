package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.api.GriefPreventionAPI
import me.ryanhamshire.GriefPrevention.GriefPrevention
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import java.util.*

class Delete {

    fun displayAllResults(p: Player, results: MutableSet<String>){
        p.sendMessage(
            text()
                .append(newline())
                .append(text("Delete which?: ", NamedTextColor.DARK_AQUA))
                .build()
        )

        results.forEach { id ->
            val showCaseID = id.filter { it.isDigit() }
            Parkour.instance.logger.info("Claimid: $id")

            val claim = GriefPreventionAPI.getClaim(showCaseID.toLong())
            if(claim == null) {
                Parkour.instance.logger.info("No claim found with this ID")
                return
            }

            val loc = claim.lesserBoundaryCorner

            p.sendMessage(
                text()
                    .append(text("  Parkour at:", NamedTextColor.GRAY))
                    .append(text(" $showCaseID", NamedTextColor.AQUA))
                    .append(text(" [x: ${loc.x}, z:${loc.z}]", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, true))
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
                                .append(text("Delete parkour at: ", NamedTextColor.GRAY))
                                .append(text("[x: ${loc.x}, z:${loc.z}]", NamedTextColor.AQUA))
                                .build()
                        )
                    )
                    .append(newline())
                    .build()
            )
        }
    }

    fun delete(p: Player) {
        val claimID = ParkourConfig.cFile.getConfigurationSection("parkour.${p.uniqueId}")?.getKeys(false)

        if(claimID == null || claimID.size == 0){
            p.sendMessage(
                text()
                    .append(Helper.prefix)
                    .append(text(" No parkours set!", NamedTextColor.RED))
                    .build()
            )
            return
        }
        displayAllResults(p, claimID)
    }

    fun configDelete(uuid: UUID, claimID: String) {
        val id = checkIfNumeral(claimID)

        Parkour.instance.logger.info("raw claim id = $id")

        if(!checkIfClaimExists(uuid, id)) return

        ParkourConfig.cFile.set("parkour.$uuid.$id", null)
        ParkourConfig.save()
        SetStart.hasParkourStart.remove(uuid)
        SetFinish.hasParkourFinish.remove(uuid)
        locallogger(uuid," Successfully deleted Parkour on claim: ", id)
    }

    private fun checkIfNumeral(claimID: String): String {
        if(claimID.all { c: Char -> c.isDigit() }){
            return "claim_$claimID"
        }
        return claimID
    }

    private fun checkIfClaimExists(uuid: UUID, claimID: String): Boolean {
        Parkour.instance.logger.info("Checking claimid before change: $claimID")
        ParkourConfig.reload()
        val claimExists = ParkourConfig.cFile.getConfigurationSection("parkour.$uuid.$claimID")?.getKeys(false)

        if(claimExists == null || claimExists.size == 0 ){
            locallogger(uuid," Claim with this ID doesn't exist! try again! ", claimID)
            return false
        }
        return true
    }

    private fun locallogger (uuid: UUID, message: String, claimID: String){
        var id = claimID
        var dontChange = true
        claimID.forEach { c: Char ->
            if(c.isDigit()){
                dontChange = false
            }
        }
        if(!dontChange){
            id = claimID.filter { it.isDigit() }
        }
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