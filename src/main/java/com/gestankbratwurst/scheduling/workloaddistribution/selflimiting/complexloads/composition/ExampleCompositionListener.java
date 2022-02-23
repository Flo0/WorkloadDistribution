package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.complexloads.composition;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkloadRunnable;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This will play flame particles on an entities Location when it gets damaged
 * until it is dead.
 */
@AllArgsConstructor
public class ExampleCompositionListener implements Listener {

  private final ScheduledWorkloadRunnable workloadRunnable;

  @EventHandler
  public void onDamage(EntityDamageEvent event) {
    UUID entityID = event.getEntity().getUniqueId();
    Supplier<Entity> supplier = () -> Bukkit.getEntity(entityID);
    Predicate<Entity> condition = this::isInvalidEntity;
    Consumer<Entity> action = this::tickEntity;
    CompositionWorkload<Entity> entityRunnable = new CompositionWorkload<>(supplier, condition, action);
    this.workloadRunnable.addWorkload(entityRunnable);
  }


  private boolean isInvalidEntity(Entity entity) {
    return entity == null || entity.isDead();
  }

  private void tickEntity(Entity entity) {
    Location location = entity.getLocation();
    location.getWorld().spawnParticle(Particle.FLAME, location, 1);
  }


}
