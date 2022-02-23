package com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.implementations.primitive;

import com.gestankbratwurst.scheduling.workloaddistribution.simple.problem.description.VolumeFiller;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/**
 * This implementation will instantly fill a volume with blocks in a single tick.
 */
public class FullVolumeFiller implements VolumeFiller {

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
          world.setType(x, y, z, material);
        }
      }
    }
  }

}
