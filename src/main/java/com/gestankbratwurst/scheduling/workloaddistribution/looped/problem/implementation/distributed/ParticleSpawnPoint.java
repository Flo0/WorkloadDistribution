package com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.distributed;

import com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.distributed.Workload;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.concurrent.ThreadLocalRandom;

public class ParticleSpawnPoint implements Workload {

  private final Location location;
  private final Particle particle;
  private final int amount;
  private final double randomOffset;
  private final ThreadLocalRandom random;

  public ParticleSpawnPoint(Location location, Particle particle, int amount, double offset) {
    this.location = location;
    this.particle = particle;
    this.amount = amount;
    this.randomOffset = offset;
    this.random = ThreadLocalRandom.current();
  }

  @Override
  public void compute() {
    double randX = this.random.nextDouble(-this.randomOffset, this.randomOffset);
    double randY = this.random.nextDouble(-this.randomOffset, this.randomOffset);
    double randZ = this.random.nextDouble(-this.randomOffset, this.randomOffset);
    World world = this.location.getWorld();
    if (world == null) {
      return;
    }
    world.spawnParticle(this.particle, this.location, this.amount, randX, randY, randZ);
  }

}
