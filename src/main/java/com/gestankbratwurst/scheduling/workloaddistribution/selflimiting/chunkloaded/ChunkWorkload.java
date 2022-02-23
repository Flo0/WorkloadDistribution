package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.chunkloaded;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkload;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Recommendation: Look at FixedCountRunnable and PlayerWorkload first.
 * <p>
 * This workload runs an action every 20 compute calls (about once a second)
 * and stops itself as soon as the chunk or the world is unloaded.
 */
public class ChunkWorkload implements ScheduledWorkload {

  private final UUID worldID;
  private final int x;
  private final int z;
  private final Consumer<Chunk> action;
  private boolean chunkUnLoaded;
  private int ticksAlive;

  public ChunkWorkload(Chunk chunk, Consumer<Chunk> action) {
    this.worldID = chunk.getWorld().getUID();
    this.x = chunk.getX();
    this.z = chunk.getZ();
    this.action = action;
  }

  @Override
  public void compute() {
    World world = Bukkit.getWorld(this.worldID);
    if (world == null) {
      this.chunkUnLoaded = true;
      return;
    }
    if (world.isChunkLoaded(this.x, this.z)) {
      this.chunkUnLoaded = true;
      return;
    }
    if (this.ticksAlive++ % 20 == 0) {
      return;
    }
    this.action.accept(world.getChunkAt(this.x, this.z));
  }

  @Override
  public boolean shouldBeRescheduled() {
    return this.chunkUnLoaded;
  }
}
