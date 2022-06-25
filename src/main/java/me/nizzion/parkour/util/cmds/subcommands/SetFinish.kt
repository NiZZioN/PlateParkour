package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.Helper
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

object SetFinish {
    val hasParkourFinish: HashMap<UUID, Boolean> = HashMap()

    fun setValues(p: Player){
        if(hasParkourFinish.containsKey(p.uniqueId)){
            p.sendMessage(
                Component.text()
                    .append(Helper.prefix)
                    .append(Component.text(" "))
                    .append(Component.text("You already have a Parkour Finish!", NamedTextColor.RED))
                    .build()
            )

            Parkour.instance.logger.info("${p.name} already has a Parkour Finish!")
        } else {
            p.inventory.addItem(ItemManager.parkourFinish)
            hasParkourFinish[p.uniqueId] = true
        }
    }
}