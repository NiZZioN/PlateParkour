package me.nizzion.parkour.events.types

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.api.GriefPreventionAPI
import me.nizzion.parkour.util.cmds.subcommands.SetStart
import me.ryanhamshire.GriefPrevention.GriefPrevention
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class ParkourFinishPosition : Listener {
    @EventHandler
    fun onRightClick(e: PlayerInteractEvent) {
        if (!e.isBlockInHand) return
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        if (e.clickedBlock?.isBuildable == false) return
        if (!e.hasBlock() || e.player.inventory.itemInMainHand != ItemManager.parkourFinish) return

        if (e.player.gameMode != GameMode.SURVIVAL) {
            e.player.sendMessage(
                text()
                    .append(Helper.prefix)
                    .append(text(" Can't place parkour's in gamemode: ", NamedTextColor.DARK_GRAY))
                    .append(text(e.player.gameMode.toString(), NamedTextColor.AQUA))
                    .build()
            )
            Parkour.instance.logger.info("Can't place parkour's in gamemode: ${e.player.gameMode}")
            e.isCancelled = true
            return
        }

        if (!GriefPreventionAPI.isOnClaim(e.player, e.clickedBlock!!.location)) {
            e.player.sendMessage(
                ItemManager.parkourFinish.itemMeta?.displayName()
                    ?.let {
                        text()
                            .append(it)
                            .append(text(" can only be placed inside your claim!", NamedTextColor.RED))
                            .build()
                    }!!
            )
            e.isCancelled = true
            return
        }

        if (!GriefPreventionAPI.hasBuildPermissions(e.player, e.clickedBlock!!.location)) return

        val claimID = GriefPrevention.instance
            .dataStore
            .getClaimAt(e.clickedBlock!!.location, false, null)
            .id
            .toString()

        ParkourConfig.cFile.set(
            "parkour.finish.${e.player.uniqueId}.claim_$claimID",
            e.clickedBlock!!.location.add(0.5, 1.0, 0.5).serialize()
        )
        ParkourConfig.save()

        e.player.sendMessage(text()
            .append(Helper.prefix)
            .append(text( " You placed the ", NamedTextColor.GRAY))
            .append(text("finish", NamedTextColor.AQUA))
            .append(text("!", NamedTextColor.GRAY))
            .build()
        )
        SetStart.hasParkourStart.remove(e.player.uniqueId)
    }
}