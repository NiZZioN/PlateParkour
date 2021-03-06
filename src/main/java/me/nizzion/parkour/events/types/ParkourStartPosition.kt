package me.nizzion.parkour.events.types

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.api.GriefPreventionAPI
import me.nizzion.parkour.util.cmds.subcommands.SetFinish
import me.nizzion.parkour.util.cmds.subcommands.SetStart
import me.ryanhamshire.GriefPrevention.GriefPrevention
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

class ParkourStartPosition : Listener {
    @EventHandler
    fun onRightClick(e: BlockPlaceEvent) {
        if(!e.player.inventory.itemInMainHand.itemMeta.equals(ItemManager.parkourStart.itemMeta)) return

        if (e.player.gameMode != GameMode.SURVIVAL) {
            e.player.sendMessage(
                text()
                    .append(Helper.prefix)
                    .append(text(" Can't place parkour's in gamemode: ", NamedTextColor.RED))
                    .append(text(e.player.gameMode.toString(), NamedTextColor.DARK_AQUA))
                    .build()
            )
            Parkour.instance.logger.info("Can't place parkour's in gamemode: ${e.player.gameMode}")
            e.isCancelled = true
            return
        }

        if (!GriefPreventionAPI.isOnClaim(e.blockPlaced.location)) {
            e.player.sendMessage(
                ItemManager.parkourStart.itemMeta?.displayName()
                    ?.let {
                        text()
                            .append(it)
                            .append(text(" can only be placed inside a claim!", NamedTextColor.RED))
                            .build()
                    }!!
            )
            e.isCancelled = true
            return
        }

        if (!GriefPreventionAPI.hasBuildPermissions(e.player, e.blockPlaced.location)) return

        val claimID = GriefPreventionAPI.getClaimID(e.blockPlaced.location) ?: return

        ParkourConfig.cFile.set(
            "parkour.${e.player.uniqueId}.$claimID.start",
            e.blockPlaced.location.add(0.5, 1.0, 0.5)
        )
        ParkourConfig.save()

        e.player.sendMessage(
            text()
                .append(Helper.prefix)
                .append(text(" You placed the ", NamedTextColor.GRAY))
                .append(text("start", NamedTextColor.AQUA))
                .append(text("!", NamedTextColor.GRAY))
                .append(newline())
                .build()
        )
        SetFinish.setValues(e.player)
        SetStart.hasParkourStart.remove(e.player.uniqueId)
    }
}