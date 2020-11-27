package ca.ligature.mcdisc;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JukeboxEventListener implements Listener {
  private static final Material CUSTOM_DISC_ID = Material.MUSIC_DISC_FAR;
  private SoundPlayer soundPlayer = new SoundPlayer();

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (event.getBlock().getType() == Material.JUKEBOX) {
      Position position = new Position(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
      PlayingSoundStore.INSTANCE.untrack(position);
    }
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    try {
      Block clickedBlock = event.getClickedBlock();
      if (clickedBlock != null && clickedBlock.getType() == Material.JUKEBOX) {
        Position position = new Position(event.getClickedBlock().getX(),
            event.getClickedBlock().getY(),
            event.getClickedBlock().getZ());
        PlayingSoundStore.INSTANCE.currentSound(position).ifPresent(sound -> {
          soundPlayer.stopSound(sound);
          PlayingSoundStore.INSTANCE.untrack(position);
        });
        ItemStack item = event.getItem();
        Jukebox jukebox = (Jukebox) clickedBlock.getState();
        if (item != null && item.getType() == CUSTOM_DISC_ID && !jukebox.isPlaying()) {
          ItemMeta itemMeta = item.getItemMeta();
          if (itemMeta != null) {
            String displayName = itemMeta.getDisplayName();
            if (IsCustomDiscDisplayName(displayName)) {
              PlayingSoundStore.INSTANCE.track(position, ConvertDisplayNameToSoundId(displayName));
              soundPlayer.playSound(ConvertDisplayNameToSoundId(displayName), clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ());
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean IsCustomDiscDisplayName(String displayNameKey) {
    return displayNameKey.contains("mcdisc.");
  }

  private String ConvertDisplayNameToSoundId(String displayNameKey) {
    String number = displayNameKey.replaceAll("\\D+", "");
    return "mcdisc:sound.mcdisc." + number;
  }
}
