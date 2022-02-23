package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.fixedcount;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkload;
import lombok.RequiredArgsConstructor;

/**
 * This workload will run "maxTicks" times and only executes
 * the internal runnable every "delay" ticks.
 * So FixedCountRunnable(5, 60, () -> System.out.println("Hi"))
 * will print out "Hi" 20 times while skipping every 4th compute call.
 * Comparable to BukkitScheduler#runTaskTimer()
 */
@RequiredArgsConstructor
public class FixedCountRunnable implements ScheduledWorkload {

  private final int delay;
  private final int maxTicks;
  private final Runnable runnable;
  private int ticksAlive = 0;

  @Override
  public void compute() {
    if (this.ticksAlive++ % this.delay == 0) {
      this.runnable.run();
    }
  }

  @Override
  public boolean shouldBeRescheduled() {
    return this.ticksAlive < this.maxTicks;
  }

}