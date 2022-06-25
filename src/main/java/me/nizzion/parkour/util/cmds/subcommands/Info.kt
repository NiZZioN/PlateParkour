package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.util.Helper
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class Info {
    fun log (p: Player){
        p.sendMessage(text()
            .append(
                text("----------------= ", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, true))
            .append(Helper.prefix)
            .append(
                text(" =----------------", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, true))
            .append(text(" - Parkour", NamedTextColor.DARK_AQUA))
            .append(text(" Commands:", NamedTextColor.GRAY))
            .append(text(" /pk <subcommand>", NamedTextColor.DARK_AQUA))
            .append(
                text("-----------------", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, true))
            .append(text(" - set", NamedTextColor.DARK_AQUA))
            .append(text(" - delete", NamedTextColor.DARK_AQUA))
            .append(text(" - reload", NamedTextColor.DARK_AQUA))
            .append(text(" - start", NamedTextColor.DARK_AQUA))
            .append(text(" - finish", NamedTextColor.DARK_AQUA))
            .append(
                text("---------------------------------------------", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, true))
            .build())
    }
}