package ca.ligature.mcdisc;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.stream.Stream;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;

public class ActionLootingListener implements Listener {
  private HashMap<String, LocalDateTime> lastDamageEntityTime = new HashMap<>();
  private static final TemporalAmount damageRollQuota = Duration.ofSeconds(2);

  @EventHandler
  public void onEntityKilled(EntityDamageByEntityEvent event) {
    if (event.getDamager().getType() == EntityType.PLAYER) {
      Player player = (Player) event.getDamager();
      if (Stream.of(EntityType.GHAST, EntityType.BLAZE, EntityType.ZOMBIE, EntityType.ZOGLIN, EntityType.SPIDER,
          EntityType.CAVE_SPIDER,
          EntityType.SKELETON, EntityType.PIGLIN,
          EntityType.ENDER_DRAGON,
          EntityType.ENDERMAN,
          EntityType.SLIME,
          EntityType.MAGMA_CUBE,
          EntityType.WITHER_SKELETON, EntityType.CREEPER,
          EntityType.ZOMBIE_VILLAGER).anyMatch(x -> event.getEntityType() == x)) {
        if (lastDamageEntityTime.getOrDefault(player.getName(), LocalDateTime.MIN).plus(damageRollQuota).isBefore(LocalDateTime.now(Clock.systemUTC()))) {
          Looter.INSTANCE.rollLoot(player.getName(), 40, "mighty abilities");
          lastDamageEntityTime.put(player.getName(), LocalDateTime.now(Clock.systemUTC()));
        }
      }
    }
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    Block block = event.getBlock();
    Player player = event.getPlayer();
    if (block.getType() == Material.DIAMOND_ORE || block.getType() == Material.REDSTONE_ORE || block.getType() == Material.LAPIS_ORE) {
      if (event.getExpToDrop() > 0) {
        Looter.INSTANCE.rollLoot(player.getName(), 100, "wonderful luck");
      }
    }
    if (block.getType() == Material.WHEAT || block.getType() == Material.CARROTS ||
        block.getType() == Material.BEETROOTS || block.getType() == Material.POTATOES ||
        block.getType() == Material.MELON || block.getType() == Material.PUMPKIN) {
      Looter.INSTANCE.rollLoot(player.getName(), 500, "bountiful harvest");
    }
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    String message = event.getDeathMessage();
    int probability = 10;

    if (Stream.of("pricked", "pummeled", "shot by", "drown", "blew up", "blown up", "fell", "flame", "ground too hard",
        "burn", "fire", "suffoca", "squished too much", "squashed", "bang").anyMatch(message::contains)) {
      RespawnListener.INSTANCE.doUponRespawn(event.getEntity().getName(),
          () -> Looter.INSTANCE.rollLoot(event.getEntity().getName(), probability, "pitiful death")
      );
    }

    if (Stream.of("slain", "fireball", "wither", "magic").anyMatch(message::contains)) {
      RespawnListener.INSTANCE.doUponRespawn(event.getEntity().getName(), () ->
          Looter.INSTANCE.rollLoot(event.getEntity().getName(), probability, "honourable death"));
    }
  }

  @EventHandler
  public void onSmeltExtract(FurnaceExtractEvent event) {
    if (event.getExpToDrop() > 1 && Stream.of(Material.IRON_INGOT, Material.GOLD_INGOT, Material.NETHER_BRICK, Material.BRICK).anyMatch(x -> event.getItemType() == x)) {
      Looter.INSTANCE.rollLoot(event.getPlayer().getName(), 100 - event.getItemAmount(), "expert craftsmanship");
    }
    if (Stream.of(Material.COOKED_RABBIT,
        Material.COOKED_BEEF,
        Material.COOKED_CHICKEN,
        Material.COOKED_COD,
        Material.COOKED_MUTTON,
        Material.COOKED_PORKCHOP,
        Material.COOKED_SALMON,
        Material.BAKED_POTATO
    ).anyMatch(x -> x == event.getItemType())) {
      Looter.INSTANCE.rollLoot(event.getPlayer().getName(), 150 - event.getItemAmount(), "delicious cooking");
    }
  }

  @EventHandler
  public void onEnchant(EnchantItemEvent event) {
    if (event.getExpLevelCost() == 3) {
      Looter.INSTANCE.rollLoot(event.getEnchanter().getName(), 40, "splendid spellcraft");
    }
  }
}
