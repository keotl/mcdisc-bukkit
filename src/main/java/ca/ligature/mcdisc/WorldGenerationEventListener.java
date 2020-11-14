package ca.ligature.mcdisc;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

public class WorldGenerationEventListener implements Listener {
  private int numberOfDiscs;

  public WorldGenerationEventListener(int numberOfDiscs) {
    this.numberOfDiscs = numberOfDiscs;
  }

  @EventHandler
  public void onGeneration(ChunkPopulateEvent e) {
    BlockState[] tileEntities = e.getChunk().getTileEntities();
    for (BlockState state : tileEntities) {
      if (state.getType() == Material.CHEST) {
//        System.out.println("mcdisc: chest at:");
//        System.out.println(state.getX() + "," + state.getY() + "," + state.getZ());
        generateChestDisc(state.getX(), state.getY(), state.getZ());
      }
    }
    for (Entity entity : e.getChunk().getEntities()) {
      if (entity.getType() == EntityType.MINECART_CHEST) {
        Location location = entity.getLocation();
//        System.out.println("foobar!!! chest minecart at:");
//        System.out.println(location.getBlockX() + "," + location.getY() + "," + location.getZ());
        generateMinecartDisc(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      }
    }
  }

  public void generateChestDisc(int x, int y, int z) {
    String disc = "minecraft:music_disc_far{CustomModelData:@@MODEL@@, display: {Name: \"{\\\"translate\\\": \\\"item.record.item.mcdisc.@@NUMBER@@.desc\\\"}\"}}";
    new Thread(() -> {
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Bukkit.getScheduler().callSyncMethod(McDiscPlugin.INSTANCE, () ->
          Bukkit.dispatchCommand(
              Bukkit.getConsoleSender(),
              "replaceitem block " + x + " " + y + " " + z + " container.1 " +
                  disc.replace("@@MODEL@@", Integer.toString(ThreadLocalRandom.current().nextInt(1, 14 + 1)))
                      .replace("@@NUMBER@@", Integer.toString(ThreadLocalRandom.current().nextInt(1, numberOfDiscs + 1)))
          )
      );
    }).start();
  }
  public void generateMinecartDisc(int x, int y, int z) {
    String disc = "minecraft:music_disc_far{CustomModelData:@@MODEL@@, display: {Name: \"{\\\"translate\\\": \\\"item.record.item.mcdisc.@@NUMBER@@.desc\\\"}\"}}";
    new Thread(() -> {
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Bukkit.getScheduler().callSyncMethod(McDiscPlugin.INSTANCE, () ->
          Bukkit.dispatchCommand(
              Bukkit.getConsoleSender(),
              "replaceitem entity @e[x=" + x + ",y=" + y + ",z=" + z + ",dx=1,dy=1,dz=1,type=minecraft:chest_minecart] container.1 " +
                  disc.replace("@@MODEL@@", Integer.toString(ThreadLocalRandom.current().nextInt(1, 14 + 1)))
                      .replace("@@NUMBER@@", Integer.toString(ThreadLocalRandom.current().nextInt(1, numberOfDiscs + 1)))
          )
      );
    }).start();
  }
}
