package ca.ligature.mcdisc;

import org.bukkit.plugin.java.JavaPlugin;

public class McDiscPlugin extends JavaPlugin {

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
    getServer().getPluginManager().registerEvents(new WorldGenerationEventListener(190), this);
    getLogger().warning("McDisc! loaded mcdisc plugin");
    getCommand("customdisc").setExecutor(new CreateRecordCommand());
  }
}
