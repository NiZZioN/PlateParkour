package me.nizzion.parkour.events.types

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.api.GriefPreventionAPI
import me.nizzion.parkour.util.cmds.subcommands.Set
import me.ryanhamshire.GriefPrevention.GriefPrevention
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class ParkourStartPosition : Listener {

    @EventHandler
    fun onRightClick(e: PlayerInteractEvent) {
        if(e.action != Action.RIGHT_CLICK_BLOCK) {
            return
        }
        if(e.item!!.itemMeta?.equals(ItemManager.parkourStart.itemMeta) == false) {
            return
        }
        if(e.player.gameMode != GameMode.SURVIVAL){
            e.player.sendMessage("${Helper.prefix} Can't place parkour's in gamemode: ${e.player.gameMode}")
            Parkour.instance.logger.info("Can't place parkour's in gamemode: ${e.player.gameMode}")
            e.isCancelled = true
            return
        }

        if(GriefPreventionAPI.isOnClaim(e.player, e.clickedBlock!!.location)) {
            if (!GriefPreventionAPI.isClaimOwner(e.player, e.clickedBlock!!.location)) {
                e.isCancelled = true
                return
            }
            if(!GriefPreventionAPI.hasBuildPermissions(e.player, e.clickedBlock!!.location)){
                e.isCancelled = true
                return
            }
            val claimID = GriefPrevention.instance.dataStore.getClaimAt(e.clickedBlock!!.location, false, null).id
            ParkourConfig.cFile.set("parkour.start.$claimID", e.clickedBlock!!.location.add(0.5, 1.0, 0.5))
            ParkourConfig.save()

            e.player.sendMessage("You placed the start!")
            Set.hasParkour.remove(e.player.uniqueId)
            return
        }
        e.player.sendMessage("${ItemManager.parkourStart.itemMeta?.displayName()} can only be placed inside your claim")
        e.isCancelled = true
    }
}