package com.gestankbratwurst.scheduling.workloaddistribution.additions;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkload;

import java.util.LinkedList;

/**
 * Read this only after understanding the "looped" package.
 * <p>
 * This is a more sophisticated version of the ScheduledWorkloadRunnable.
 * The trick here is to call removeIf on a LinkedList. Usually the runtime
 * complexity of remove while iterating for Lists is O(n) which is quite bad.
 * Here the LinkedList shines with O(1) scaling.
 * <p>
 * Each ScheduledWorkload will be ticked once every tick and removed if shouldBeRescheduled() returns false.
 * <p>
 * Benefits: Really easy to implement. Very good performance.
 * Downside: There is no time limiting factor anymore. Every workload gets ticked once every tick.
 * <p>
 * This runnable should be used for tasks that don't need any time limiting.
 * Like sending every player on the server his action bar every 20 ticks.
 * Or doing other tasks that have an internal tick counter and only run every N calls of
 * compute.
 */
public class AdeptScheduledWorkloadRunnable implements Runnable {

  private final LinkedList<ScheduledWorkload> workloads = new LinkedList<>();

  public void addWorkload(ScheduledWorkload workload) {
    this.workloads.add(workload);
  }

  @Override
  public void run() {
    this.workloads.removeIf(this::runAndCheck);
  }

  private boolean runAndCheck(ScheduledWorkload workload) {
    workload.compute();
    return workload.shouldBeRescheduled();
  }

}
