package me.nizzion.parkour.items

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.cmds.subcommands.SetFinish
import me.nizzion.parkour.util.cmds.subcommands.SetStart
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemManager {
    init {
        parkourStart
        parkourFinish
    }

    companion object {
        val parkourFinish: ItemStack
            get() {
                val item = ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, 1)
                val meta = item.itemMeta ?: return item
                val displayName = text()
                    .append(Helper.prefix)
                    .append(text(" Finish", NamedTextColor.AQUA)
                        .decoration(TextDecoration.BOLD, true))
                    .decoration(TextDecoration.ITALIC, false)
                    .build()

                meta.displayName(displayName)

                val loreList = mutableListOf<Component>()

                loreList.add(
                    text("This is the end point of your Parkour!", NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false)
                )
                loreList.add(
                    text("Place it inside your claim to mark the finish!", NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false)
                )

                meta.lore(loreList)
                meta.addEnchant(Enchantment.LUCK, 1, false)
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                item.itemMeta = meta
                return item
            }


        val parkourStart: ItemStack
            get() {
                val item = ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, 1)
                val meta = item.itemMeta ?: return item

                val displayName = text()
                    .append(Helper.prefix)
                    .append(text(" Start", NamedTextColor.AQUA)
                        .decoration(TextDecoration.BOLD, true))
                    .decoration(TextDecoration.ITALIC, false)
                    .build()

                meta.displayName(displayName)

                val loreList = mutableListOf<Component>()

                loreList.add(
                    text("This is the start point of your Parkour!", NamedTextColor.GOLD)
                    .decoration(TextDecoration.ITALIC, false)
                )
                loreList.add(
                    text("Place it inside your claim to mark the start!", NamedTextColor.GOLD)
                    .decoration(TextDecoration.ITALIC, false)
                )

                meta.lore(loreList)
                meta.addEnchant(Enchantment.LUCK, 1, false)
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                item.itemMeta = meta
                return item
            }

        private fun hasParkourStartOnLoad() {
            Parkour.instance.server.onlinePlayers.forEach { p ->
                p.inventory.forEach { item ->
                    if (item != null && item.itemMeta?.equals(parkourStart.itemMeta) == true) {
                        SetStart.hasParkourStart[p.uniqueId] = true
                    }
                }
            }
        }

        private fun hasParkourFinishOnLoad() {
            Parkour.instance.server.onlinePlayers.forEach { p ->
                p.inventory.forEach { item ->
                    if (item != null && item.itemMeta?.equals(parkourFinish.itemMeta) == true) {
                        SetFinish.hasParkourFinish[p.uniqueId] = true
                    }
                }
            }
        }

        fun onLoad() {
            hasParkourFinishOnLoad()
            hasParkourStartOnLoad()
        }
    }
}