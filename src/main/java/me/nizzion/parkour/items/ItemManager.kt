package me.nizzion.parkour.items

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.cmds.subcommands.Set
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemManager {
    init {
        parkourStart
    }
    companion object {
        val parkourStart: ItemStack
            get() {
                val item = ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, 1)
                val meta = item.itemMeta

                meta?.setDisplayName("${Helper.prefix} Parkour Start")
                val lore = ArrayList<String>()
                lore.add("This is the start point of your Parkour!")
                lore.add("Place it inside your claim to mark the start!")
                meta?.lore = lore
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