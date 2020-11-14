package ca.ligature.mcdisc;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public class McDiscPlugin extends JavaPlugin {

  public static McDiscPlugin INSTANCE;

  @Override
  public void onDisable() {
    super.onDisable();
    getLogger().warning("foobar! unloaded mcdisc plugin");
  }

  @Override
  public void onEnable() {
    super.onEnable();
    INSTANCE = this;
    getServer().getPluginManager().registerEvents(new JukeboxEventListener(), this);
    getLogger().warning("foobar! loaded mcdisc plugin");
  }
}
