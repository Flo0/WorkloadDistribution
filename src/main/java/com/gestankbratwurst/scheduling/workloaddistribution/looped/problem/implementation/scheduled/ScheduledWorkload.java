package com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled;

public interface ScheduledWorkload {

  void compute();

  default boolean shouldBeRescheduled() {
    return false;
  }

}
