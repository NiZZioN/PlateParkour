package me.nizzion.parkour.util

import me.nizzion.parkour.Parkour
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

object Helper {
    val prefix = text()
        .append(
            text("[", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, false)
        )
        .append(
            text(Parkour.instance.name, NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.BOLD, true)
        )
        .append(
            text("]", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, false)
        )
        .build()
}