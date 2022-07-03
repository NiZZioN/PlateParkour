package me.nizzion.parkour.util.timer

import me.nizzion.parkour.Parkour
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.collections.HashMap

class Timer(var time: Int, var isRunning: Boolean, var p: Player) {

    init {
        hasTimer[p.uniqueId] = this
        run()
    }


    fun sendActionBar () {
        val timerText = text()
            .append(text("$time"))

        if(!isRunning) {
            timerText.append(text(" paused.", NamedTextColor.DARK_AQUA))
            return
        }
        timerText.build()
        p.sendActionBar(timerText)
    }

    private fun run() {
        object : BukkitRunnable() {
            override fun run() {
                sendActionBar()
                if (!isRunning) {
                    return
                }
                time++
            }
        }.runTaskTimerAsynchronously(Parkour.instance, 20, 20)
    }

    companion object {
        val hasTimer = HashMap<UUID, Timer>()
    }
}