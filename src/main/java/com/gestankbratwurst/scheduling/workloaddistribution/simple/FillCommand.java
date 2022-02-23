package com.gestankbratwurst.scheduling.workloaddistribution.simple;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Values;
import com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.distributed.DistributedFiller;
import com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.distributed.WorkloadRunnable;
import com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.primitive.FullVolumeFiller;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.WeakHashMap;

/**
 * Example command. Don't worry about it.
 * Simply used, so you can test out the different implementations.
 */
@CommandAlias("fill")
@RequiredArgsConstructor
public class FillCommand extends BaseCommand {

  private final WeakHashMap<Player, LocationPair> selectedLocationsMap = new WeakHashMap<>();
  private final WorkloadRunnable workloadRunnable;

  @Subcommand("posA")
  public void onPosA(Player sender) {
    this.selectedLocationsMap.computeIfAbsent(sender, key -> new LocationPair()).setLocA(sender.getLocation());
    sender.sendMessage("§eLocation A was set.");
  }

  @Subcommand("posB")
  public void onPosB(Player sender) {
    this.selectedLocationsMap.computeIfAbsent(sender, key -> new LocationPair()).setLocB(sender.getLocation());
    sender.sendMessage("§eLocation A was set.");
  }

  @Subcommand("instant")
  @CommandCompletion("@Material")
  public void onInstantFill(Player sender, @Values("@Material") Material material) {
    LocationPair locationPair = this.selectedLocationsMap.computeIfAbsent(sender, key -> new LocationPair());
    if (locationPair.isInvalid()) {
      sender.sendMessage("§cYou need to select two locations which have to be in the same world first.");
      return;
    }
    new FullVolumeFiller().fill(locationPair.locA, locationPair.locB, material);
  }

  @Subcommand("distributed")
  @CommandCompletion("@Material")
  public void onDistributedFill(Player sender, @Values("@Material") Material material) {
    LocationPair locationPair = this.selectedLocationsMap.computeIfAbsent(sender, key -> new LocationPair());
    if (locationPair.isInvalid()) {
      sender.sendMessage("§cYou need to select two locations which have to be in the same world first.");
      return;
    }
    new DistributedFiller(this.workloadRunnable).fill(locationPair.locA, locationPair.locB, material);
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
