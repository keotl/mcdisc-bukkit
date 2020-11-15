package ca.ligature.mcdisc;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.bukkit.plugin.java.JavaPlugin;

public class McDiscPlugin extends JavaPlugin {

  public static int NUMBER_OF_DISCS = 190;
  public static final TemporalAmount QUOTA_RESET_TIME = Duration.ofHours(2);
//  public static final TemporalAmount QUOTA_RESET_TIME = Duration.ofSeconds(30);
  public static McDiscPlugin INSTANCE;

  @Override
  public void onDisable() {
    super.onDisable();
    getLogger().warning("McDisc! unloaded mcdisc plugin");
  }

  @Override
  public void onEnable() {
    super.onEnable();
    INSTANCE = this;
    getServer().getPluginManager().registerEvents(new JukeboxEventListener(), this);
    getServer().getPluginManager().registerEvents(new WorldGenerationEventListener(NUMBER_OF_DISCS), this);
    getServer().getPluginManager().registerEvents(new ActionLootingListener(), this);
    getServer().getPluginManager().registerEvents(RespawnListener.INSTANCE, this);
    getLogger().warning("McDisc! loaded mcdisc plugin");
    getCommand("customdisc").setExecutor(new CreateRecordCommand());
  }
}
