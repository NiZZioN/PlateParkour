package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.items.ItemManager
import me.nizzion.parkour.util.Helper
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

object Info {
    val seperator = text()
        .append(
            text("------------------", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, true)
        )
        .build()

    val bottomLine = text()
        .append(
            text("---------------------------------------------", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, true)
        )
        .build()

    val title = text()
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

    fun log(p: Player) {
        p.sendMessage(title)

        p.sendMessage(text().append(text(" - Parkour", NamedTextColor.DARK_AQUA)).build())
        p.sendMessage(text().append(text(" Commands:", NamedTextColor.GRAY)).build())
        p.sendMessage(text().append(text(" /pk <subcommand>", NamedTextColor.DARK_AQUA)).build())
        p.sendMessage(seperator)
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
        p.sendMessage(bottomLine)
    }


    fun setStart(p: Player) {
        p.sendMessage(title)
        p.sendMessage(text().append(text(" - Parkour", NamedTextColor.DARK_AQUA)).build())
        p.sendMessage(text().append(text(" Command:", NamedTextColor.GRAY)).build())
        p.sendMessage(
            text()
                .append(text(" /pk set ", NamedTextColor.DARK_AQUA))
                .append(text("<", NamedTextColor.DARK_GRAY))
                .append(
                    text("start", NamedTextColor.AQUA)
                        .hoverEvent(
                            HoverEvent.hoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                text("Get the ", NamedTextColor.DARK_AQUA)
                                    .append(text("Parkour-Start!", NamedTextColor.AQUA))
                            )
                        )
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pk set start"))
                )
                .append(text(" | ", NamedTextColor.DARK_GRAY))
                .append(
                    text("finish", NamedTextColor.AQUA)
                        .hoverEvent(
                            HoverEvent.hoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                text("Get the ", NamedTextColor.DARK_AQUA)
                                    .append(text("Parkour-Finish!", NamedTextColor.AQUA))
                            )
                        )
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pk set finish"))
                )
                .append(text(">", NamedTextColor.DARK_GRAY))
                .build()
        )
        p.sendMessage(bottomLine)
    }

    fun setDeleteId(p: Player) {
        p.sendMessage(title)
        p.sendMessage(text().append(text(" - Parkour", NamedTextColor.DARK_AQUA)).build())
        p.sendMessage(text().append(text(" Command:", NamedTextColor.GRAY)).build())
        p.sendMessage(
            text()
                .append(text(" /pk delete ", NamedTextColor.DARK_AQUA))
                .append(
                    text("<id>", NamedTextColor.DARK_AQUA)
                        .hoverEvent(
                            HoverEvent.hoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                text("ID of the Claim", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, true)
                            )
                        )
                )
                .build()
        )
        p.sendMessage(bottomLine)
    }

    fun firstStep(p: Player) {
        p.sendMessage(
            text()
                .append(Helper.prefix)
                .append(text(" Step 1:", NamedTextColor.DARK_GRAY))
                .append(Component.newline())
                .append(text("              Place the ", NamedTextColor.DARK_AQUA))
                .append(ItemManager.parkourStart.displayName())
                .append(text(" "))
                .append(
                    text("inside", NamedTextColor.AQUA)
                        .decoration(TextDecoration.UNDERLINED, true)
                )
                .append(text(" your ", NamedTextColor.DARK_AQUA))
                .append(Component.newline())
                .append(text("              "))
                .append(
                    text("claim", NamedTextColor.AQUA)
                        .decoration(TextDecoration.UNDERLINED, true)
                )
                .append(text(" to mark the ", NamedTextColor.DARK_AQUA))
                .append(
                    text("start", NamedTextColor.AQUA)
                        .decoration(TextDecoration.UNDERLINED, true)
                )
                .append(text(" of the parkour!", NamedTextColor.DARK_AQUA))
                .append(Component.newline())
                .build()
        )
    }
}