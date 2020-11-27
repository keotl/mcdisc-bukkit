package ca.ligature.mcdisc;

import java.util.HashMap;
import java.util.Optional;

public class PlayingSoundStore {
  public static final PlayingSoundStore INSTANCE = new PlayingSoundStore();

  private HashMap<Position, String> jukeboxSounds = new HashMap<>();

  public Optional<String> currentSound(Position position) {
    if (jukeboxSounds.containsKey(position)) {
      return Optional.of(jukeboxSounds.get(position));
    } else {
      return Optional.empty();
    }
  }

  public void track(Position jukeboxBlock, String sound) {
    jukeboxSounds.put(jukeboxBlock, sound);
  }

  public void untrack(Position jukeboxBlock) {
    jukeboxSounds.remove(jukeboxBlock);
  }
}
