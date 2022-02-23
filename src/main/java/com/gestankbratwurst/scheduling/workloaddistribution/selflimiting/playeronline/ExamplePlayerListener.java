package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.playeronline;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkloadRunnable;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@AllArgsConstructor
public class ExamplePlayerListener implements Listener {

  private final ScheduledWorkloadRunnable workloadRunnable;

  /**
   * This will literally spam the player every single tick with Hi.
   * The good part is that we don't need to remove the Player from
   * the ScheduledWorkloadRunnable as the PlayerWorkload is self controlled
   * and will just not be rescheduled if the Player is offline.
   */
  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    PlayerWorkload workload = new PlayerWorkload(event.getPlayer().getUniqueId(), player -> player.sendMessage("Hi"));
    this.workloadRunnable.addWorkload(workload);
  }

}
