package me.nizzion.parkour.util.cmds

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.files.ParkourConfig
import me.nizzion.parkour.util.api.GriefPreventionAPI
import me.nizzion.parkour.util.cmds.subcommands.*
import me.nizzion.parkour.util.timer.Timer
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class CommandManager : CommandExecutor {
    private val commandList: MutableList<MutableList<String>> = mutableListOf()

    fun registerCommands() {
        Parkour.instance.getCommand("parkour")?.setExecutor(this)
    }

    fun addTabComplete() {
        TODO("Not yet implemented")
    }

    private fun createCommand(args: Array<out String>, p: Player) {
        if (args.isEmpty()) {
            Info.log(p)
            return
        }
        Parkour.instance.logger.info("You triggered the command: pk with: ${args[0]}")
        when (args[0]) {
            "set" -> {
                if (args.size == 1) {
                    SetStart.setValues(p)
                    return
                }

                when (args[1]) {
                    "start"  -> SetStart.setValues(p)
                    "finish" -> SetFinish.setValues(p)
                    else     -> Info.setStart(p)
                }
                return
            }

            "delete" -> {
                when(args.size) {
                    1 -> {
                        Info.setDeleteId(p)
                        Delete().delete(p)
                        return
                    }
                    2 -> {
                        Delete().configDelete(p.uniqueId, args[1])
                        return
                    }
                }
                return
            }

            "reload" -> Reload.reloadConfig()
            "tp"     -> {
                when (args[1]) {
                    "start"  -> Teleport.toStart(p, GriefPreventionAPI.getClaimID(p.location)!!)
                    "finish" -> Teleport.toFinish(p, GriefPreventionAPI.getClaimID(p.location)!!)
                }
            }
            "teleport" -> {
                when (args[1]) {
                    "start"  -> Teleport.toStart(p, GriefPreventionAPI.getClaimID(p.location)!!)
                    "finish" -> Teleport.toFinish(p, GriefPreventionAPI.getClaimID(p.location)!!)
                }
            }
            "timer" -> {
                // TODO: Move Timer logic out of CommandManager and just execute methods
                //  instead of having the timer logic in here,
                //  adjust timer display and error messages accordingly, add miliseconds to timer and keep timer in hashmap for eachplayer
                //  (partially done already)

                val timer = Timer.hasTimer[p.uniqueId]
                if(timer == null) {
                    Timer(0, false, p)
                    return
                }

                if(args.size == 1){
                    p.sendMessage("Do /pk timer <resume | time | reset | pause>")
                    return
                }

                when(args[1]) {
                    "resume" -> {
                        if(timer.isRunning) {
                            p.sendMessage("Timer is already running...")
                            return
                        }
                        timer.isRunning = true
                        p.sendMessage("Timer started.")
                        return
                    }
                    "pause" -> {
                        if(!timer.isRunning) {
                            p.sendMessage("Already paused.")
                        }
                        timer.isRunning = false
                        return
                    }
                    "time" -> {
                        if(args.size != 3){
                            p.sendMessage("Usage /pk timer time <number>")
                            return
                        }
                        try {
                            timer.time = args[2].toInt()
                            p.sendMessage("Time was set to: ${args[2]}")
                        } catch (e: NumberFormatException){
                            p.sendMessage(text("Time was not a number.", NamedTextColor.RED))
                            Parkour.instance.logger.warning(e.message)
                        }
                    }
                    "reset" -> Timer.hasTimer.remove(p.uniqueId)
                    else -> p.sendMessage("Do /pk timer <resume | time | reset | pause>")
                }
            }
            "rotation" -> {
//                TODO:
//                 Make it so that the player gets promoted to tp
//                 after doing /pk rotation start for example,
//                 then has to type confirm or click it in chat to confirm the rotation for the starting/finish position

                val claimID = GriefPreventionAPI.getClaimID(p.location) ?: return
                val start = ParkourConfig.getStart(p.uniqueId, claimID) ?: return
                val finish = ParkourConfig.getFinish(p.uniqueId, claimID) ?: return
                when(args[1]){
                    "start"  -> {
                        start.yaw = p.location.yaw
                        start.pitch = p.location.pitch
                        ParkourConfig.reload()
                        ParkourConfig.cFile.set("parkour.${p.uniqueId}.$claimID.start", start)
                        ParkourConfig.save()
                        return
                    }
                    "finish" -> {
                        finish.yaw = p.location.yaw
                        finish.pitch = p.location.pitch
                        ParkourConfig.reload()
                        ParkourConfig.cFile.set("parkour.${p.uniqueId}.$claimID.finish", finish)
                        ParkourConfig.save()
                        return
                    }
                }
            }
        }
        return
    }

    fun addTabCompleteForSubCommand() {
    }

    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) {
            Parkour.instance.logger.info("Command cannot be exectued via Console!")
            return true
        }
        val commandWithSubCommand: MutableList<String> = mutableListOf()
        commandWithSubCommand.add("pk")
        if (args.isNotEmpty()) {
            args.forEach { commandWithSubCommand.add(it) }
        }
        commandList.add(commandWithSubCommand)
        createCommand(args, sender)
        return true
    }

}