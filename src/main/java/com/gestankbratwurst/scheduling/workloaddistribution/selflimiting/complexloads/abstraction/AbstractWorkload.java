package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.complexloads.abstraction;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkload;

/**
 * This is a bit of a stretch of the whole concept and can safely be ignored.
 * Just a demonstration on how volatile you could implement workloads.
 */
public abstract class AbstractWorkload<T> implements ScheduledWorkload {

  private boolean invalidElement;

  @Override
  public void compute() {
    T element = this.getElement();
    if (this.isInvalid(element)) {
      this.invalidElement = true;
      return;
    }
    this.apply(element);
  }

  @Override
  public boolean shouldBeRescheduled() {
    return !this.invalidElement;
  }

  protected abstract T getElement();

  protected abstract boolean isInvalid(T element);

  protected abstract void apply(T element);

}
