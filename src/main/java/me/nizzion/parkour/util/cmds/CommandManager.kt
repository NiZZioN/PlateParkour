package me.nizzion.parkour.util.cmds

import me.nizzion.parkour.Parkour
import me.nizzion.parkour.util.Helper
import me.nizzion.parkour.util.cmds.subcommands.*
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
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
            Info().log(p)
            return
        }
        Parkour.instance.logger.info("You triggered the command: pk with: ${args[0]}")
        when (args[0]) {
            "set" -> {
                val errorMsg = text()
                    .append(Helper.prefix)
                    .append(text(" Set ", NamedTextColor.DARK_AQUA))
                    .append(text("only has ", NamedTextColor.RED))
                    .append(text("[ ", NamedTextColor.DARK_GRAY))
                    .append(text("start, finish", NamedTextColor.AQUA))
                    .append(text(" ]", NamedTextColor.DARK_GRAY))
                    .append(text(" as parameters.", NamedTextColor.RED))
                    .build()


                if (args.size < 2) {
                    p.sendMessage(errorMsg)
                    return
                }

                when (args[1]) {
                    "start"  -> SetStart.setValues(p)
                    "finish" -> SetFinish.setValues(p)
                    else     -> p.sendMessage(errorMsg)

                }
            }

            "delete" -> {
                val errorMsg = text()
                    .append(Helper.prefix)
                    .append(text(" Delete", NamedTextColor.DARK_AQUA))
                    .append(text(" has", NamedTextColor.RED))
                    .append(text(" [", NamedTextColor.DARK_GRAY))
                    .append(text(" start, finish", NamedTextColor.AQUA))
                    .append(text(" ]", NamedTextColor.DARK_GRAY))
                    .append(text(" and a", NamedTextColor.RED))
                    .append(text(" ClaimID", NamedTextColor.AQUA))
                    .append(text(" as parameters.", NamedTextColor.RED))
                    .build()

                if (args.size == 1){
                    Delete().delete(p)
                    p.sendMessage("")
                    return
                }

                if (args.size < 3) {
                    p.sendMessage(errorMsg)
                    return
                }

                val arr = arrayOf("start", "finish")

                if(args[1] !in arr){
                    p.sendMessage(errorMsg)
                    return
                }
                Delete().configDelete(p.uniqueId, args[1], args[2])
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
            Parkour.instance.logger.info("Command cannot be exectued via Console")
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