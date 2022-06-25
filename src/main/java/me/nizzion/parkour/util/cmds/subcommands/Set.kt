package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.Helper
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

class Set {

    companion object {
        val hasParkour: HashMap<UUID, Boolean> = HashMap()
        fun setValues(p: Player){
            if(hasParkour.containsKey(p.uniqueId)){
                p.sendMessage(text()
                    .append(Helper.prefix)
                    .append(text(" "))
                    .append(text("You already have a Parkour Start!", NamedTextColor.RED))
                    .build()
                )

                Parkour.instance.logger.info("${p.name} already has a Parkour Start!")
            } else {
                p.inventory.addItem(ItemManager.parkourStart)
                hasParkour[p.uniqueId] = true
            }
        }
    }
}