package ca.ligature.mcdisc;

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
      Chunk chunkAt = player.getWorld().getChunkAt(player.getLocation());
      for (BlockState state : chunkAt.getTileEntities()) {
        if (state.getType() == Material.CHEST) {
          new WorldGenerationEventListener(190).generateChestDisc(state.getX(), state.getY(), state.getZ());
        }
      }
      return true;
    }
    return false;
  }
}
