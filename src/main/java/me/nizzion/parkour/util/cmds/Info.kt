package me.nizzion.parkour.util.cmds

import org.bukkit.ChatColor
import org.bukkit.entity.Player

class Info {
    fun log (p: Player){
        val aqua = ChatColor.DARK_AQUA
        val st = ChatColor.STRIKETHROUGH
        val gray = ChatColor.DARK_GRAY
        val lgray = ChatColor.GRAY
        val bold = ChatColor.BOLD
        p.sendMessage("$gray$st----------------=$gray[$aqua$bold Parkour $gray]$st=----------------")
        p.sendMessage("$aqua - Parkour:")
        p.sendMessage("$lgray Commands: ")
        p.sendMessage("$aqua /pk <subcommand>")
        p.sendMessage("$lgray SubCommands: ")
        p.sendMessage("$gray$st--------------------")
        p.sendMessage("$aqua - set")
        p.sendMessage("$aqua - start")
        p.sendMessage("$aqua - finish")
        p.sendMessage("$gray$st---------------------------------------------")
    }
}