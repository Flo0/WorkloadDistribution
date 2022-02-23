package com.gestankbratwurst.scheduling.workloaddistribution;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.LoopedProcessingPlugin;
import com.gestankbratwurst.scheduling.workloaddistribution.simple.LinearProcessingPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Host JavaPlugin. Look at each package to see implementations
 * of DummyPlugin which each represents its own plugin.
 * This class can safely be ignored.
 */
public final class WorkloadDistribution extends JavaPlugin {

  private final List<DummyPlugin> pluginList = new ArrayList<>();

  @Override
  public void onLoad() {
    this.pluginList.add(new LinearProcessingPlugin());
    this.pluginList.add(new LoopedProcessingPlugin());
  }

  @Override
  public void onEnable() {
    this.pluginList.forEach(plugin -> plugin.onEnable(this));
  }

  @Override
  public void onDisable() {
    this.pluginList.forEach(plugin -> plugin.onDisable(this));
  }

}
