package com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.distributed;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.UUID;

/**
 * An arbitrary implementation for Workload that changes
 * a single Block to a given Material.
 */
@AllArgsConstructor
public class PlacableBlock implements Workload {

  private final UUID worldID;
  private final int blockX;
  private final int blockY;
  private final int blockZ;
  private final Material material;

  @Override
  public void compute() {
    World world = Bukkit.getWorld(this.worldID);
    Preconditions.checkState(world != null);
    world.getBlockAt(this.blockX, this.blockY, this.blockZ).setType(this.material);
  }

}