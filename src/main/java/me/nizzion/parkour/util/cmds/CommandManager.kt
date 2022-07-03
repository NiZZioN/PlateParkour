package me.nizzion.parkour.util.cmds

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.util.cmds.subcommands.*
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

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
                    Info.setStart(p)
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
                        Delete().delete(p)
                        p.sendMessage("")
                        return
                    }
                    2 -> {
                        Info.setDeleteId(p, args[1])
                        return
                    }
                }

                when(args[1]){
                    "start" -> Delete().configDelete(p.uniqueId, args[1], args[2])
                    "finish" -> Delete().configDelete(p.uniqueId, args[1], args[2])
                    else -> {
                        Info.setDelete(p)
                        return
                    }
                }
            }

            "reload" -> Reload.reloadConfig()
            "tp"     -> {
                when (args[1]) {
                    "start"  -> Teleport().toStart(p)
                    "finish" -> Teleport().toFinish()
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