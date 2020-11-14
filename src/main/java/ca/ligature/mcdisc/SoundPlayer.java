package ca.ligature.mcdisc;

import org.bukkit.Bukkit;

public class SoundPlayer {

  public void playSound(String soundId, float x, float y, float z) {
    Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        "playsound " + soundId + " block @a " + x + " " + y + " " + z
    );

    new Thread(() -> {
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Bukkit.getScheduler().callSyncMethod(McDiscPlugin.INSTANCE, () ->
          Bukkit.dispatchCommand(
              Bukkit.getConsoleSender(),
              "stopsound @a record minecraft:music_disc.far"
          )
      );
    }).start();
  }

  public void stopSound(String soundId) {
    Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        "stopsound @a block " + soundId
    );
  }
}
