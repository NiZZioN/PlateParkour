package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.Helper
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap
object SetStart {
    val hasParkourStart: HashMap<UUID, Boolean> = HashMap()
    fun setValues(p: Player){
        if(hasParkourStart.containsKey(p.uniqueId)){
            p.sendMessage(text()
                .append(Helper.prefix)
                .append(text(" "))
                .append(text("You already have a Parkour Start!", NamedTextColor.RED))
                .build()
            )

            Parkour.instance.logger.info("${p.name} already has a Parkour Start!")
        } else {
            p.inventory.addItem(ItemManager.parkourStart)
            p.sendMessage(
                text()
                    .append(Helper.prefix)
                    .append(text(" Step 1:", NamedTextColor.DARK_GRAY))
                    .append(newline())
                    .append(text("              Place the ", NamedTextColor.DARK_AQUA))
                    .append(ItemManager.parkourStart.displayName())
                    .append(text(" "))
                    .append(text("inside", NamedTextColor.AQUA)
                        .decoration(TextDecoration.UNDERLINED, true))
                    .append(text(" your ", NamedTextColor.DARK_AQUA))
                    .append(newline())
                    .append(text("              "))
                    .append(text("claim", NamedTextColor.AQUA)
                        .decoration(TextDecoration.UNDERLINED, true))
                    .append(text(" to mark the ", NamedTextColor.DARK_AQUA))
                    .append(text("start", NamedTextColor.AQUA)
                        .decoration(TextDecoration.UNDERLINED, true))
                    .append(text(" of the parkour!", NamedTextColor.DARK_AQUA))
                    .append(newline())
                    .build()
            )
            hasParkourStart[p.uniqueId] = true
        }
    }
}