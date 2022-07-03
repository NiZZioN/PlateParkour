package me.nizzion.parkour.events.types

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.api.GriefPreventionAPI
import me.nizzion.parkour.util.api.GriefPreventionAPI.hasParkourStart
import me.nizzion.parkour.util.cmds.subcommands.SetFinish
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class ParkourFinishPosition : Listener {
    @EventHandler
    fun onRightClick(e: BlockPlaceEvent) {
        if (!e.player.inventory.itemInMainHand.itemMeta.equals(ItemManager.parkourFinish.itemMeta)) return

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

        Parkour.instance.logger.info("Current claim id = $claimID")
        Parkour.instance.logger.info("path: ${"parkour.${e.player.uniqueId}.$claimID.start"}")
        ParkourConfig.reload()

        if (ParkourConfig.getStart(e.player.uniqueId, claimID) == null) {
            e.player.sendMessage(
                text()
                    .append(Helper.prefix)
                    .append(text(" No Start Set for this claim! do ", NamedTextColor.RED))
                    .append(
                        text("/pk set", NamedTextColor.AQUA)
                            .hoverEvent(
                                HoverEvent.hoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    text("Do /pk set!", NamedTextColor.DARK_AQUA)
                                )
                            )
                            .clickEvent(
                                ClickEvent.clickEvent(
                                    ClickEvent.Action.RUN_COMMAND,
                                    "/pk set"
                                )
                            )
                    )
                    .append(text(" first!", NamedTextColor.RED))
            )
            Parkour.instance.logger.info("No start set!")
            e.isCancelled = true
            return
        }

        ParkourConfig.cFile.set(
            "parkour.${e.player.uniqueId}.$claimID.finish",
            e.blockPlaced.location.add(0.5, 1.0, 0.5)
        )
        ParkourConfig.save()

        e.player.sendMessage(
            text()
                .append(Helper.prefix)
                .append(text(" You placed the ", NamedTextColor.GRAY))
                .append(text("finish", NamedTextColor.AQUA))
                .append(text("!", NamedTextColor.GRAY))
                .append(newline())
                .build()
        )

        SetFinish.hasParkourFinish.remove(e.player.uniqueId)
    }
}