package ca.ligature.mcdisc;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;

public class LootingQuotaManager {
  public static LootingQuotaManager INSTANCE = new LootingQuotaManager();

  private HashMap<String, LocalDateTime> lastLooted = new HashMap<>();

  public boolean canReceive(String player) {
    if (!lastLooted.containsKey(player)) {
      return true;
    }
    return lastLooted.get(player).plus(McDiscPlugin.QUOTA_RESET_TIME).isBefore(LocalDateTime.now(Clock.systemUTC())
    );
  }

  public void trackLoot(String player) {
    lastLooted.put(player, LocalDateTime.now(Clock.systemUTC()));
  }
}
