package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.complexloads.composition;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkload;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This is a bit of a stretch of the whole concept and can safely be ignored.
 * Just a demonstration on how volatile you could implement workloads.
 */
@RequiredArgsConstructor
public class CompositionWorkload<T> implements ScheduledWorkload {

  private final Supplier<T> valueSupplier;
  private final Predicate<T> breakupCondition;
  private final Consumer<T> valueConsumer;
  private boolean conditionFailed;

  @Override
  public void compute() {
    T element = this.valueSupplier.get();
    if (this.breakupCondition.test(element)) {
      this.conditionFailed = true;
      return;
    }
    this.valueConsumer.accept(element);
  }

  @Override
  public boolean shouldBeRescheduled() {
    return !this.conditionFailed;
  }
}
