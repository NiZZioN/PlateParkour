package me.nizzion.parkour.util.api

import me.nizzion.parkour.Parkour
import me.ryanhamshire.GriefPrevention.Claim
import me.ryanhamshire.GriefPrevention.ClaimPermission
import me.ryanhamshire.GriefPrevention.GriefPrevention
import org.bukkit.Location
import org.bukkit.entity.Player

object GriefPreventionAPI {
    fun isClaimOwner(p: Player, loc: Location): Boolean {
        val claim = GriefPrevention.instance.dataStore.getClaimAt(loc, false, null)
        if (isOnClaim(loc) && claim.ownerID == p.uniqueId) {
            return true
        }
        return false
    }

    fun hasBuildPermissions(p: Player, loc: Location): Boolean {
        val claim = GriefPrevention.instance.dataStore.getClaimAt(loc, false, null)
        if (isOnClaim(loc) && claim.hasExplicitPermission(p.uniqueId, ClaimPermission.Build)) {
            return true
        }
        return false
    }

    fun isOnClaim(loc: Location): Boolean {
        val claim = GriefPrevention.instance.dataStore.getClaimAt(loc, false, null)
        if (claim != null) {
            return true
        }
        return false
    }

    fun getClaimID(loc: Location): String {
        return "claim_${GriefPrevention.instance.dataStore.getClaimAt(loc, false, null).id}"
    }

    fun getClaim(claimID: Long): Claim? {
        return GriefPrevention.instance.dataStore.getClaim(claimID)
    }

}