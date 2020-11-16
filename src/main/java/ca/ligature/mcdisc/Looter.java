package ca.ligature.mcdisc;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;

public class Looter {
  private static final String COMMAND_TEMPLATE = "tellraw @a [\"\",{\"text\":\"The gods smile upon \",\"color\":\"gray\"},{\"text\":\"@@PLAYER@@\",\"color\":\"red\"},{\"text\":\" for their \",\"color\":\"gray\"},{\"text\":\"@@ACTION@@\",\"color\":\"red\"},{\"text\":\"!\",\"color\":\"gray\"}]";
  public static final Looter INSTANCE = new Looter();

  public void rollLoot(String player, int denominator, String messageAction) {
    double x = ThreadLocalRandom.current().nextDouble();
    if (x <= 1.0 / denominator && LootingQuotaManager.INSTANCE.canReceive(player)) {
      LootingQuotaManager.INSTANCE.trackLoot(player);
      giveAndBroadcast(player, messageAction);
    }
  }

  private void giveAndBroadcast(String player, String messageAction) {
    String disc = "minecraft:music_disc_far{CustomModelData:@@MODEL@@, display: {Name: \"{\\\"translate\\\": \\\"item.record.item.mcdisc.@@NUMBER@@.desc\\\"}\"}}";
    String discCommand = disc.replace("@@MODEL@@", Integer.toString(ThreadLocalRandom.current().nextInt(1, 14 + 1)))
        .replace("@@NUMBER@@", Integer.toString(ThreadLocalRandom.current().nextInt(1, McDiscPlugin.NUMBER_OF_DISCS + 1)));

    Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        COMMAND_TEMPLATE
            .replace("@@PLAYER@@", player)
            .replace("@@ACTION@@", messageAction)
    );
    Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        "playsound minecraft:entity.lightning_bolt.thunder master @a 0 0 0 1 1 1"
    );
    Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        "give @@PLAYER@@ @@ITEM@@"
            .replace("@@PLAYER@@", player)
            .replace("@@ITEM@@", discCommand)
    );
  }
}
