package ca.ligature.mcdisc;

import java.util.HashMap;
import java.util.Optional;

public class PlayingSoundStore {
  public static final PlayingSoundStore INSTANCE = new PlayingSoundStore();

  private HashMap<String, String> playerSounds = new HashMap<String, String>();

  public Optional<String> currentSound(String player) {
    if (playerSounds.containsKey(player)) {
      return Optional.of(playerSounds.get(player));
    } else {
      return Optional.empty();
    }
  }

  public void trackPlayerSound(String player, String sound) {
    playerSounds.put(player, sound);
  }

  public void untrack(String player) {
    playerSounds.remove(player);
  }
}
