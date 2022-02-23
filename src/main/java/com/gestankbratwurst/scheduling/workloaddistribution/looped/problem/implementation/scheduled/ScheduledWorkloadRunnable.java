package com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This is the basic implementation of a WorkloadRunnable.
 * It processes as many Workloads every tick as the given
 * field MAX_MILLIS_PER_TICK allows.
 */
public class ScheduledWorkloadRunnable implements Runnable {

  private static final double MAX_MILLIS_PER_TICK = 2.5;
  private static final int MAX_NANOS_PER_TICK = (int) (MAX_MILLIS_PER_TICK * 1E6);

  private final Deque<ScheduledWorkload> workloadDeque = new ArrayDeque<>();

  public void addWorkload(ScheduledWorkload workload) {
    this.workloadDeque.add(workload);
  }

  @Override
  public void run() {
    long stopTime = System.nanoTime() + MAX_NANOS_PER_TICK;

    ScheduledWorkload lastElement = this.workloadDeque.peekLast();
    ScheduledWorkload nextLoad = null;

    // Compute all loads until the time is run out or the queue is empty, or we did one full cycle
    // The lastElement is here, so we don't cycle through the queue several times
    while (System.nanoTime() <= stopTime && !this.workloadDeque.isEmpty() && nextLoad != lastElement) {
      nextLoad = this.workloadDeque.poll();
      nextLoad.compute();
      if (nextLoad.shouldBeRescheduled()) {
        this.addWorkload(nextLoad);
      }
    }
  }

}
