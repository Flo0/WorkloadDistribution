package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.complexloads.abstraction;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkloadRunnable;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * When a player breaks a REDSTONE_ORE block -> add 0.1 HP to him each tick
 * for 20 ticks or until he is full health.
 */
@AllArgsConstructor
public class ExampleAbstractListener implements Listener {

  private final ScheduledWorkloadRunnable workloadRunnable;

  @EventHandler(ignoreCancelled = true)
  public void onDamage(BlockBreakEvent event) {
    if (event.getBlock().getType() != Material.REDSTONE_ORE) {
      return;
    }
    PlayerHealWorkload workload = new PlayerHealWorkload(event.getPlayer().getUniqueId(), 0.1, 20);
    this.workloadRunnable.addWorkload(workload);
  }

}
