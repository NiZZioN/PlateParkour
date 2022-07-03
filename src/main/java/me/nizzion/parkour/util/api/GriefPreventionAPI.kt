package me.nizzion.parkour.util.api

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.ryanhamshire.GriefPrevention.Claim
import me.ryanhamshire.GriefPrevention.ClaimPermission
import me.ryanhamshire.GriefPrevention.GriefPrevention
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

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

    fun getClaimID(loc: Location): String? {
        if(!isOnClaim(loc)) {
            Parkour.instance.logger.warning("not on a claim!")
            return null
        }
        return "claim_${GriefPrevention.instance.dataStore.getClaimAt(loc, false, null).id}"
    }

    fun getClaim(claimID: Long): Claim? {
        return GriefPrevention.instance.dataStore.getClaim(claimID)
    }

    fun hasParkourStart(uuid: UUID, claimID: String): Boolean {
        return ParkourConfig.cFile.getConfigurationSection("parkour.$uuid.$claimID.start")?.getKeys(false) != null
    }

    fun hasParkourFinish(uuid: UUID, claimID: String): Boolean {
        return ParkourConfig.cFile.getConfigurationSection("parkour.$uuid.$claimID.finish")?.getKeys(false) != null
    }
}