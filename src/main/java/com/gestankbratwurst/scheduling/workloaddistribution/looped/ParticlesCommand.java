package com.gestankbratwurst.scheduling.workloaddistribution.looped;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Values;
import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.CuboidLocationFetcher;
import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.distributed.ParticleSpawnPoint;
import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledParticleSpawnPoint;
import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkloadRunnable;
import com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.distributed.WorkloadRunnable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

import java.util.WeakHashMap;

/**
 * Example command. Don't worry about it.
 * Simply used, so you can test out the different implementations.
 */
@CommandAlias("fillparticles")
@RequiredArgsConstructor
public class ParticlesCommand extends BaseCommand {


  private final WeakHashMap<Player, LocationPair> selectedLocationsMap = new WeakHashMap<>();
  private final ScheduledWorkloadRunnable scheduledWorkloadRunnable;
  private final WorkloadRunnable workloadRunnable;

  @Subcommand("posA")
  public void onPosA(Player sender) {
    sender.getWorld().spawn(sender.getLocation(), Slime.class, slime -> {
      slime.setCustomName("Bob");
      slime.setCustomNameVisible(true);
      slime.setInvisible(true);
    });
    this.selectedLocationsMap.computeIfAbsent(sender, key -> new LocationPair()).setLocA(sender.getLocation());
    sender.sendMessage("§eLocation A was set.");
  }

  @Subcommand("posB")
  public void onPosB(Player sender) {
    this.selectedLocationsMap.computeIfAbsent(sender, key -> new LocationPair()).setLocB(sender.getLocation());
    sender.sendMessage("§eLocation A was set.");
  }

  @Subcommand("once")
  @CommandCompletion("@Particle")
  public void onInstantFill(Player sender, @Values("@Particle") Particle particle) {
    LocationPair locationPair = this.selectedLocationsMap.computeIfAbsent(sender, key -> new LocationPair());
    if (locationPair.isInvalid()) {
      sender.sendMessage("§cYou need to select two locations which have to be in the same world first.");
      return;
    }
    CuboidLocationFetcher.fetch(locationPair.locA, locationPair.locB).forEach(location -> {
      ParticleSpawnPoint particleSpawnPoint = new ParticleSpawnPoint(location, particle, 1, 0.01);
      this.workloadRunnable.addWorkload(particleSpawnPoint);
    });
  }

  @Subcommand("repeated")
  @CommandCompletion("@Particle")
  public void onDistributedFill(Player sender, @Values("@Particle") Particle particle) {
    LocationPair locationPair = this.selectedLocationsMap.computeIfAbsent(sender, key -> new LocationPair());
    if (locationPair.isInvalid()) {
      sender.sendMessage("§cYou need to select two locations which have to be in the same world first.");
      return;
    }
    CuboidLocationFetcher.fetch(locationPair.locA, locationPair.locB).forEach(location -> {
      ScheduledParticleSpawnPoint particleSpawnPoint = new ScheduledParticleSpawnPoint(location, particle, 1, 0.01);
      this.scheduledWorkloadRunnable.addWorkload(particleSpawnPoint);
    });
    sender.sendMessage("§cCaution! This wont stop until the server restarts.");
  }

  @Data
  private static class LocationPair {
    private Location locA;
    private Location locB;

    boolean isInvalid() {
      return this.locA == null || this.locB == null || this.locA.getWorld() != this.locB.getWorld();
    }
  }

}
