package com.gestankbratwurst.scheduling.workloaddistribution.simple;

import co.aikar.commands.BukkitCommandManager;
import com.gestankbratwurst.scheduling.workloaddistribution.DummyPlugin;
import com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.distributed.WorkloadRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * This is how your JavaPlugin class could look like.
 * You have a WorkloadRunnable singleton to which you
 * pass your workloads. The command is for you to test
 * out the two implementations and can be ignored.
 */
public class LinearProcessingPlugin implements DummyPlugin {

  private final WorkloadRunnable workloadRunnable = new WorkloadRunnable();

  @Override
  public void onEnable(JavaPlugin host) {
    Bukkit.getScheduler().runTaskTimer(host, this.workloadRunnable, 1, 1);
    this.registerCommand(host);
  }

  @Override
  public void onDisable(JavaPlugin host) {

  }

  private void registerCommand(JavaPlugin host) {
    BukkitCommandManager commandManager = new BukkitCommandManager(host);
    List<String> materials = Arrays.stream(Material.values()).filter(Material::isBlock).map(Enum::toString).toList();
    commandManager.getCommandCompletions().registerStaticCompletion("Material", materials);
    commandManager.registerCommand(new FillCommand(this.workloadRunnable));
  }

}
