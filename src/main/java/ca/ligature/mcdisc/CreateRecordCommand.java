package ca.ligature.mcdisc;

import java.util.Arrays;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateRecordCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    if (commandSender instanceof Player) {

      Player player = (Player) commandSender;
      if (!player.getName().equals("kento2300")) {
        return false;
      }
      Looter.INSTANCE.rollLoot(Arrays.stream(strings).findFirst().orElse(player.getName()), 1, "administrator privileges");
    }
    return false;
  }
}
