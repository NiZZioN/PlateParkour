package me.nizzion.parkour.util.api

import me.nizzion.parkour.Parkour
import me.ryanhamshire.GriefPrevention.ClaimPermission
import me.ryanhamshire.GriefPrevention.GriefPrevention
import org.bukkit.Location
import org.bukkit.entity.Player

object GriefPreventionAPI {
    fun isClaimOwner(p: Player, loc: Location): Boolean {
        val claim = GriefPrevention.instance.dataStore.getClaimAt(loc, false, null)
        if (isOnClaim(p, loc) && claim.ownerID == p.uniqueId) {
            Parkour.instance.logger.info("${p.name} is the owner!")
            return true
        }
        return false
    }

    fun hasBuildPermissions(p: Player, loc: Location): Boolean {
        val claim = GriefPrevention.instance.dataStore.getClaimAt(loc, false, null)
        if (isOnClaim(p, loc) && claim.hasExplicitPermission(p.uniqueId, ClaimPermission.Build)) {
            Parkour.instance.logger.info("${p.name} has build Perms!")
            return true
        }
        return false
    }

    fun isOnClaim(p: Player, loc: Location): Boolean {
        val claim = GriefPrevention.instance.dataStore.getClaimAt(loc, false, null)
        if (claim != null) {
            Parkour.instance.logger.info("${p.name} is on a claim!")
            return true
        }
        return false
    }
}