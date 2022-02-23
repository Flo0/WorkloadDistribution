package com.gestankbratwurst.scheduling.workloaddistribution.looped;

import co.aikar.commands.BukkitCommandManager;
import com.gestankbratwurst.scheduling.workloaddistribution.DummyPlugin;
import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkloadRunnable;
import com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.distributed.WorkloadRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * This is how your JavaPlugin class could look like.
 * You have a ScheduledWorkloadRunnable singleton to which you
 * pass your ScheduledWorkloads. The command is for you to test
 * out the two implementations and can be ignored. The
 * WorkloadRunnable is only used for demonstrating purposes and
 * is part of the simple example package.
 */
public class LoopedProcessingPlugin implements DummyPlugin {

  private final WorkloadRunnable workloadRunnable = new WorkloadRunnable();
  private final ScheduledWorkloadRunnable scheduledWorkloadRunnable = new ScheduledWorkloadRunnable();

  @Override
  public void onEnable(JavaPlugin host) {
    Bukkit.getScheduler().runTaskTimer(host, this.workloadRunnable, 1, 1);
    Bukkit.getScheduler().runTaskTimer(host, this.scheduledWorkloadRunnable, 1, 1);
    this.registerCommand(host);
  }

  @Override
  public void onDisable(JavaPlugin host) {

  }

  private void registerCommand(JavaPlugin host) {
    BukkitCommandManager commandManager = new BukkitCommandManager(host);
    List<String> particles = Arrays.stream(Particle.values()).map(Enum::toString).toList();
    commandManager.getCommandCompletions().registerStaticCompletion("Particle", particles);
    commandManager.registerCommand(new ParticlesCommand(this.scheduledWorkloadRunnable, this.workloadRunnable));
  }

}
