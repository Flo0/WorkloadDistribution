package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.chunkloaded;

import com.gestankbratwurst.scheduling.workloaddistribution.looped.problem.implementation.scheduled.ScheduledWorkloadRunnable;
import lombok.AllArgsConstructor;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

/**
 * As soon as a chunk is loaded, every BlockState in it is
 * ticked every second.
 */
@AllArgsConstructor
public class ExampleChunkListener implements Listener {

  private final ScheduledWorkloadRunnable workloadRunnable;

  @EventHandler
  public void onChunkLoad(ChunkLoadEvent event) {
    ChunkWorkload workload = new ChunkWorkload(event.getChunk(), this::tickChunk);
    this.workloadRunnable.addWorkload(workload);
  }

  private void tickChunk(Chunk chunk) {
    for (BlockState blockState : chunk.getTileEntities()) {
      this.checkBlockState(blockState);
    }
  }

  private void checkBlockState(BlockState blockState) {
    // Compute something with each BlockState
  }

}
