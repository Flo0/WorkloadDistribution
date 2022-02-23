package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.fixedcount;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkloadRunnable;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@AllArgsConstructor
public class ExampleFixedCountListener implements Listener {

  private final ScheduledWorkloadRunnable workloadRunnable;

  /**
   * This just prints out the material in the console 3 times after
   * 0.0s delay
   * 0.5s delay
   * 1.0s delay
   * Then it just gets discarded.
   */
  @EventHandler
  public void onDamage(BlockBreakEvent event) {
    Material material = event.getBlock().getType();
    FixedCountRunnable runnable = new FixedCountRunnable(10, 30, () -> System.out.println(material));
    this.workloadRunnable.addWorkload(runnable);
  }

}
