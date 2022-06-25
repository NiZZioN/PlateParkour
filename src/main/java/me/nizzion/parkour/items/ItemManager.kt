package me.nizzion.parkour.items

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.cmds.subcommands.Set
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import javax.naming.Name

class ItemManager {
    init {
        parkourStart
    }
    companion object {
        val parkourStart: ItemStack
            get() {
                val item = ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, 1)
                val meta = item.itemMeta

                val displayName = text()
                    .append(Helper.prefix)
                    .append(text(" Parkour Start", NamedTextColor.DARK_AQUA))
                    .decoration(TextDecoration.ITALIC, false)
                    .build()

                meta?.displayName(displayName)

                val loreList = mutableListOf<Component>()
                loreList.add(text()
                    .append(
                        text("This is the start point of your Parkour!", NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false))
                    .build())
                loreList.add(text()
                    .append(
                        text("Place it inside your claim to mark the start!", NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false))
                    .build())
                meta.lore(loreList)
                meta?.addEnchant(Enchantment.LUCK, 1, false)
                meta?.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                item.itemMeta = meta
                return item
            }

        fun hasParkourStartOnLoad(){
            Parkour.instance.server.onlinePlayers.forEach { p ->
                p.inventory.forEach {item ->
                    if(item != null && item.itemMeta?.equals(parkourStart.itemMeta) == true){
                        Set.hasParkour[p.uniqueId] = true
                    }
                }
            }
        }
    }
}