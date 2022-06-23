package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.items.ItemManager
import me.ryanhamshire.GriefPrevention.DataStore
import me.ryanhamshire.GriefPrevention.GriefPrevention
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import javax.xml.crypto.Data
import kotlin.collections.HashMap

class Set {

    companion object {
        val hasParkour: HashMap<UUID, Boolean> = HashMap()
        fun setValues(p: Player){
            if(hasParkour.containsKey(p.uniqueId)){
                Parkour.instance.logger.info("${p.name} already has a wand!")
            } else {
                p.inventory.addItem(ItemManager.parkourStart)
                hasParkour[p.uniqueId] = true
            }
        }
    }
}