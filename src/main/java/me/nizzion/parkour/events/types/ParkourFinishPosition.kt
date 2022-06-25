package me.nizzion.parkour.events.types

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.api.GriefPreventionAPI
<<<<<<< HEAD
import me.nizzion.parkour.util.cmds.subcommands.SetFinish
=======
>>>>>>> origin/master
import me.nizzion.parkour.util.cmds.subcommands.SetStart
import me.ryanhamshire.GriefPrevention.GriefPrevention
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
<<<<<<< HEAD
import org.bukkit.event.block.BlockPlaceEvent
=======
>>>>>>> origin/master
import org.bukkit.event.player.PlayerInteractEvent

class ParkourFinishPosition : Listener {
    @EventHandler
<<<<<<< HEAD
    fun onRightClick(e: BlockPlaceEvent) {
        if(!e.player.inventory.itemInMainHand.itemMeta.equals(ItemManager.parkourFinish.itemMeta)) return
=======
    fun onRightClick(e: PlayerInteractEvent) {
        if (!e.isBlockInHand) return
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        if (e.clickedBlock?.isBuildable == false) return
        if (!e.hasBlock() || e.player.inventory.itemInMainHand != ItemManager.parkourFinish) return
>>>>>>> origin/master

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

<<<<<<< HEAD
        if (!GriefPreventionAPI.isOnClaim(e.player, e.blockPlaced.location)) {
=======
        if (!GriefPreventionAPI.isOnClaim(e.player, e.clickedBlock!!.location)) {
>>>>>>> origin/master
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

<<<<<<< HEAD
        if (!GriefPreventionAPI.hasBuildPermissions(e.player, e.blockPlaced.location)) return

        val claimID = GriefPrevention.instance
            .dataStore
            .getClaimAt(e.blockPlaced.location, false, null)
=======
        if (!GriefPreventionAPI.hasBuildPermissions(e.player, e.clickedBlock!!.location)) return

        val claimID = GriefPrevention.instance
            .dataStore
            .getClaimAt(e.clickedBlock!!.location, false, null)
>>>>>>> origin/master
            .id
            .toString()

        ParkourConfig.cFile.set(
            "parkour.finish.${e.player.uniqueId}.claim_$claimID",
<<<<<<< HEAD
            e.blockPlaced.location.add(0.5, 1.0, 0.5).serialize()
=======
            e.clickedBlock!!.location.add(0.5, 1.0, 0.5).serialize()
>>>>>>> origin/master
        )
        ParkourConfig.save()

        e.player.sendMessage(text()
            .append(Helper.prefix)
            .append(text( " You placed the ", NamedTextColor.GRAY))
            .append(text("finish", NamedTextColor.AQUA))
            .append(text("!", NamedTextColor.GRAY))
            .build()
        )
<<<<<<< HEAD
        SetFinish.hasParkourFinish.remove(e.player.uniqueId)
=======
        SetStart.hasParkourStart.remove(e.player.uniqueId)
>>>>>>> origin/master
    }
}