package ca.ligature.mcdisc;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import sun.jvm.hotspot.ui.ThreadInfoPanel;

public class RespawnListener implements Listener {

  public static RespawnListener INSTANCE = new RespawnListener();
  private HashMap<String, Runnable> actions = new HashMap<>();


  public void doUponRespawn(String player, Runnable action) {
    actions.put(player, action);
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent event) {
    if (actions.containsKey(event.getPlayer().getName())) {
      new Thread(() -> {
        try {
          Thread.sleep(250);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Bukkit.getScheduler().callSyncMethod(McDiscPlugin.INSTANCE, () -> {
              actions.get(event.getPlayer().getName()).run();
              actions.remove(event.getPlayer().getName());
              return true;
            }
        );
      }).start();
    }
  }
}
