package ca.ligature.mcdisc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Jukebox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValueAdapter;

public class JukeboxEventListener implements Listener {
  private static final Material CUSTOM_DISC_ID = Material.MUSIC_DISC_FAR;
  private SoundPlayer soundPlayer = new SoundPlayer();

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    try {
      Block clickedBlock = event.getClickedBlock();
      if (clickedBlock != null && clickedBlock.getType() == Material.JUKEBOX) {

        PlayingSoundStore.INSTANCE.currentSound(event.getPlayer().getName()).ifPresent(sound -> {
          soundPlayer.stopSound(sound);
          PlayingSoundStore.INSTANCE.untrack(event.getPlayer().getName());
//          event.setCancelled(true);
        });

        ItemStack item = event.getItem();
        if (item != null && item.getType() == CUSTOM_DISC_ID) {
          ItemMeta itemMeta = item.getItemMeta();
          if (itemMeta != null) {
            String displayName = itemMeta.getDisplayName();
            if (IsCustomDiscDisplayName(displayName)) {
              PlayingSoundStore.INSTANCE.trackPlayerSound(event.getPlayer().getName(), ConvertDisplayNameToSoundId(displayName));
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
