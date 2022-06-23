package me.nizzion.parkour.events

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.events.types.ParkourStartPosition

class EventManager {
    fun registerEvents(){
        Parkour.instance.server.pluginManager.registerEvents(ParkourStartPosition(), Parkour.instance)
    }
}