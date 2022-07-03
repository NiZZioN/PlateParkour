package me.nizzion.parkour.util.cmds.subcommands

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.io.IOException

class Teleport {
    fun toStart(p: Player) {
        try {
            val startPos = ParkourConfig.cFile.getConfigurationSection("parkour.${p.uniqueId}.start")?.getKeys(false)
            startPos?.forEach { pos ->
                p.sendMessage("Teleporting to: $pos")
            }
            val example = text()
                .append(text("[", NamedTextColor.DARK_GRAY))
                .append(text("ur mom", NamedTextColor.GREEN))
                .append(text("]", NamedTextColor.DARK_GRAY))
                .build()

            p.sendMessage(example)

        } catch (e: IOException) {
            Parkour.instance.logger.warning("IoException: ${e.message}")
        }
    }

    fun toFinish() {

    }
}