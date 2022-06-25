package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.util.Helper
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.*

class Delete {

    fun displayAllResults(p: Player, path: String, results: MutableSet<String>){
        val displayPath = path.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        p.sendMessage(
            text()
                .append(newline())
                .append(text("$displayPath ", NamedTextColor.AQUA))
                .append(text("Delete which?: ", NamedTextColor.DARK_AQUA))
                .build()
        )

        results.forEach { id ->
            val showCaseID = id.filter { it.isDigit() }
            Parkour.instance.logger.info("Claimid: $id")

            p.sendMessage(
                text()
                    .append(text("  Parkour at:", NamedTextColor.GRAY))
                    .append(text(" $showCaseID", NamedTextColor.AQUA))
                    .clickEvent(
                        ClickEvent.clickEvent(
                            ClickEvent.Action.RUN_COMMAND,
                            "/pk delete $path $id"
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

    fun delete(p: Player) {
        val startPos = ParkourConfig.cFile.getConfigurationSection("parkour.start.${p.uniqueId}")?.getKeys(false)
        val finishPos = ParkourConfig.cFile.getConfigurationSection("parkour.finish.${p.uniqueId}")?.getKeys(false)

        if(startPos == null || startPos.size == 0){
            if(finishPos == null || finishPos.size == 0){
                p.sendMessage(
                    text()
                        .append(Helper.prefix)
                        .append(text(" No parkours set!", NamedTextColor.RED))
                        .build()
                )
                return
            }
            displayAllResults(p, "finish", finishPos)
            return
        }

        displayAllResults(p, "start", startPos)

        if(finishPos != null && finishPos.size != 0){
            displayAllResults(p, "finish", finishPos)
        }
    }

    fun configDelete(uuid: UUID, path: String, claimID: String) {
        val id = checkIfNumeral(claimID)

        Parkour.instance.logger.info("raw claim id = $id")

        if(!checkIfClaimExists(uuid, id, path)) return

        ParkourConfig.cFile.set("parkour.$path.$uuid.$id", null)
        ParkourConfig.cFile.save(ParkourConfig.file)
        when(path){
            "start" -> SetStart.hasParkourStart.remove(uuid)
            "finish" -> SetFinish.hasParkourFinish.remove(uuid)
        }
        locallogger(uuid," Successfully deleted Parkour-$path on claim: ", id)
    }

    private fun checkIfNumeral(claimID: String): String {
        if(claimID.all { c: Char -> c.isDigit() }){
            return "claim_$claimID"
        }
        return claimID
    }

    private fun checkIfClaimExists(uuid: UUID, claimID: String, path: String): Boolean {
        Parkour.instance.logger.info("Checking claimid before change: $claimID")
        ParkourConfig.reload()
        val claimExists = ParkourConfig.cFile.getConfigurationSection("parkour.$path.$uuid.$claimID")?.getKeys(false)

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