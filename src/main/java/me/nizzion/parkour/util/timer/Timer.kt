package me.nizzion.parkour.util.timer

import me.nizzion.parkour.Parkour
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.collections.HashMap

class Timer(var time: Int, var isRunning: Boolean, var isPaused: Boolean, var p: Player) {

    init {
        hasTimer[p.uniqueId] = this
        run()
    }


    fun sendActionBar () {
        val timerText = text()
            .append(text("$time"))

        if(isPaused) {
            timerText.append(text(" paused.", NamedTextColor.DARK_AQUA))
        }
        timerText.build()
        p.sendActionBar(timerText)
    }

    private fun run() {
        object : BukkitRunnable() {
            override fun run() {
                if (!isRunning) return
                sendActionBar()
                if(isPaused) return
                time++
            }
        }.runTaskTimerAsynchronously(Parkour.instance, 0, 20)
    }

    companion object {
        val hasTimer = HashMap<UUID, Timer>()
    }
}