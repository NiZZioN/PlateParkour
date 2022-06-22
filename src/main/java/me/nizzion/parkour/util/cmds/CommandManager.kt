package me.nizzion.parkour.util.cmds

import me.nizzion.parkour.Parkour
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandManager: CommandExecutor {
    private val commandList: MutableList<MutableList<String>> = mutableListOf()

    fun registerCommands(){
        Parkour.instance.getCommand("parkour")?.setExecutor(this)
    }

    fun addTabComplete() {
        TODO("Not yet implemented")
    }

    private fun createCommand(args: Array<out String>, p: Player) {
        if(args.isEmpty()){
            Info().log(p)
            return
        }
        when(args.size){
            1 -> {
                Parkour.instance.logger.info("You triggered the command: pk with: ${args[0]}")
                when(args[0]){
                    "set"    -> Set.setValues(p)
                    "delete" -> Delete().deleteStart(p)
                }
            }
            2    -> Parkour.instance.logger.info("You triggered the command: pk with: ${args[0]}, ${args[1]}")
            3    -> Parkour.instance.logger.info("You triggered the command: pk with: ${args[0]}, ${args[1]}, ${args[2]}")
            else -> Parkour.instance.logger.info("More arguments than needed.")
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
        if(sender !is Player){
            Parkour.instance.logger.info("Command cannot be exectued via Console")
            return true
        }
        val commandWithSubCommand: MutableList<String> = mutableListOf()
        commandWithSubCommand.add("pk")
        if(args.isNotEmpty()){
            args.forEach { commandWithSubCommand.add(it) }
            commandList.add(commandWithSubCommand)
            createCommand(args, sender)
        }
        if(args.isEmpty()){
            commandList.add(commandWithSubCommand)
            createCommand(args, sender)
        }
        return true
    }

}