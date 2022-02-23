package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.playeronline;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkload;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;

import java.util.UUID;

/**
 * This runnable will apply an action on a Player as long as he is online.
 */
@RequiredArgsConstructor
public class PlayerWorkload implements ScheduledWorkload {

  private final UUID playerID;
  private final Consumer<Player> action;
  private boolean playerOffline;

  @Override
  public void compute() {
    Player player = Bukkit.getPlayer(this.playerID);
    if (player == null) {
      this.playerOffline = true;
      return;
    }
    this.action.accept(player);
  }

  @Override
  public boolean shouldBeRescheduled() {
    return !this.playerOffline;
  }

}
