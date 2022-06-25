package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.util.Helper
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

class Info {
    fun log(p: Player) {


        p.sendMessage(
            text()
                .append(
                    text("----------------=", NamedTextColor.DARK_GRAY)
                        .decoration(TextDecoration.STRIKETHROUGH, true),
                    text(" ")
                )
                .append(Helper.prefix)
                .append(
                    text(" "),
                    text("=----------------", NamedTextColor.DARK_GRAY)
                        .decoration(TextDecoration.STRIKETHROUGH, true)
                )
                .build()
        )

        p.sendMessage(text().append(text(" - Parkour", NamedTextColor.DARK_AQUA)).build())
        p.sendMessage(text().append(text(" Commands:", NamedTextColor.GRAY)).build())
        p.sendMessage(text().append(text(" /pk <subcommand>", NamedTextColor.DARK_AQUA)).build())
        p.sendMessage(
            text()
                .append(
                    text("------------------", NamedTextColor.DARK_GRAY)
                        .decoration(TextDecoration.STRIKETHROUGH, true)
                )
                .build()
        )
        p.sendMessage(
            text()
                .append(text(" - set", NamedTextColor.DARK_AQUA))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pk set"))
                .build()
        )
        p.sendMessage(
            text()
                .append(text(" - delete", NamedTextColor.DARK_AQUA))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pk delete"))
                .build()
        )
        p.sendMessage(
            text()
                .append(text(" - reload", NamedTextColor.DARK_AQUA))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pk reload"))
                .build()
        )
        p.sendMessage(
            text()
                .append(text(" - start", NamedTextColor.DARK_AQUA))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pk start"))
                .build()
        )
        p.sendMessage(
            text()
                .append(text(" - finish", NamedTextColor.DARK_AQUA))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pk finish"))
                .build()
        )
        p.sendMessage(
            text()
                .append(
                    text("---------------------------------------------", NamedTextColor.DARK_GRAY)
                        .decoration(TextDecoration.STRIKETHROUGH, true)
                )
                .build()
        )
    }
}