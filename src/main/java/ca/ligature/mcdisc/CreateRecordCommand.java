package ca.ligature.mcdisc;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreateRecordCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    Player player = (Player)commandSender;
    Chunk chunkAt = player.getWorld().getChunkAt(player.getLocation());
    for (BlockState state: chunkAt.getTileEntities()) {
      if (state.getType() == Material.CHEST) {
        System.out.println("foobar!!! chest at:");
        System.out.println(state.getX() + "," + state.getY() + "," + state.getZ());

        new WorldGenerationEventListener(190).generateChestDisc(state.getX(), state.getY(), state.getZ());
//        Chest chest = (Chest) state.getBlock().getState();
//        chest.getInventory().addItem(createRandomRecord());
//        chest.update(true);
      }
    }
    return true;
  }
  public ItemStack createRandomRecord() {
    System.out.println("foobar!!! added a disc!!");
    int model = ThreadLocalRandom.current().nextInt(1, 14 + 1);
    String discKey = "item.record.item.mcdisc." +
        ThreadLocalRandom.current().nextInt(1, 190 + 1)
        + ".desc";
//    ItemStack disc =ItemStack.deserialize( new Gson().fromJson(template.replace("@@MODEL@@", Integer.toString(model))
//        .replace("@@DISPLAY_NAME@@",discKey ), HashMap.class));
    ItemStack disc = new ItemStack(Material.MUSIC_DISC_FAR);
    ItemMeta meta = disc.getItemMeta();
    meta.setLocalizedName(discKey);
    meta.setCustomModelData(model);

    meta.setDisplayName(
        discKey
    );
    meta.setLore(Collections.singletonList(discKey));
    disc.setItemMeta(meta);
    return disc;
  }

  private static final String template = "{\n" +
      "    \"meta\": {\n" +
      "        \"version\": 2580,\n" +
      "        \"persistentDataContainer\": {\n" +
      "            \"adapterContext\": {\n" +
      "                \"registry\": {\n" +
      "                    \"adapters\": {\n" +
      "                    },\n" +
      "                    \"CREATE_ADAPTER\": {\n" +
      "                    }\n" +
      "                }\n" +
      "            },\n" +
      "            \"registry\": {\n" +
      "                \"adapters\": {\n" +
      "                },\n" +
      "                \"CREATE_ADAPTER\": {\n" +
      "                }\n" +
      "            },\n" +
      "            \"customDataTags\": {\n" +
      "            }\n" +
      "        },\n" +
      "        \"unhandledTags\": {\n" +
      "        },\n" +
      "        \"damage\": 0,\n" +
      "        \"unbreakable\": false,\n" +
      "        \"hideFlag\": 0,\n" +
      "        \"repairCost\": 0,\n" +
      "        \"attributeModifiers\": {\n" +
      "        },\n" +
      "        \"customModelData\": @@MODEL@@,\n" +
      "        \"displayName\": {\n" +
      "            \"f\": {\n" +
      "            },\n" +
      "            \"d\": {\n" +
      "            },\n" +
      "            \"siblings\": [],\n" +
      "            \"j\": [],\n" +
      "            \"args\": [],\n" +
      "            \"key\": \"@@DISPLAY_NAME@@\"\n" +
      "        }\n" +
      "    },\n" +
      "    \"type\": \"MUSIC_DISC_FAR\",\n" +
      "    \"v\": 2580\n" +
      "}\n";
}
