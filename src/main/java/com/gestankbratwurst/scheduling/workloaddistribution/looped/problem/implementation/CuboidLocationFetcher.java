package com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple utility class to fetch locations between two corners.
 */
public class CuboidLocationFetcher {

  public static List<Location> fetch(Location cornerA, Location cornerB) {
    Preconditions.checkArgument(cornerA.getWorld() == cornerB.getWorld() && cornerA.getWorld() != null);
    BoundingBox box = BoundingBox.of(cornerA.getBlock(), cornerB.getBlock());
    Vector max = box.getMax();
    Vector min = box.getMin();

    World world = cornerA.getWorld();
    List<Location> fetchedList = new ArrayList<>();

    for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
      for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
        for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
          Location location = new Location(world, x + 0.5, y + 0.5, z + 0.5);
          fetchedList.add(location);
        }
      }
    }

    return fetchedList;
  }

}
