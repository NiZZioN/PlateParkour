package me.nizzion.parkour.events.types

import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.api.GriefPreventionAPI
import me.nizzion.parkour.util.cmds.subcommands.Set
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
        if (e.item!!.itemMeta?.equals(ItemManager.parkourStart.itemMeta) == false) {
            return
        }

        if(GriefPreventionAPI.isOnClaim(e.player, e.player.location)) {
            if (!GriefPreventionAPI.isClaimOwner(e.player, e.player.location)) {
                e.isCancelled = true
                return
            }
            e.player.sendMessage("You placed the start!")
            Set.hasParkour.remove(e.player.uniqueId)
            return
        }
        e.player.sendMessage("${ItemManager.parkourStart.itemMeta?.displayName} can only be placed inside your claim")
        e.isCancelled = true
    }
}