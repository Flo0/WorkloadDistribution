package com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.distributed;

import com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.description.VolumeFiller;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/**
 * This implementation does not change a Block instantly.
 * It defines a Workload (PlacableBlock in this case) and simply passes it
 * to the WorkloadRunnable. We then forget about it because the runnable
 * will decide when there is enough CPU time left for this Workload to be
 * computed.
 */
@AllArgsConstructor
public class DistributedFiller implements VolumeFiller {

  private final WorkloadRunnable workloadRunnable;

  @Override
  public void fill(Location cornerA, Location cornerB, Material material) {
    Preconditions.checkArgument(cornerA.getWorld() == cornerB.getWorld() && cornerA.getWorld() != null);
    BoundingBox box = BoundingBox.of(cornerA.getBlock(), cornerB.getBlock());
    Vector max = box.getMax();
    Vector min = box.getMin();

    World world = cornerA.getWorld();

    for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
      for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
        for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
          PlacableBlock placableBlock = new PlacableBlock(world.getUID(), x, y, z, material);
          this.workloadRunnable.addWorkload(placableBlock);
        }
      }
    }
  }

}
