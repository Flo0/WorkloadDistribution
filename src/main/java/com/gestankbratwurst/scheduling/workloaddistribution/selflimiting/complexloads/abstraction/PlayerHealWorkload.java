package com.gestankbratwurst.scheduling.workloaddistribution.selflimiting.complexloads.abstraction;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This heals a player "times" times every tick for "healAmount"
 */
@RequiredArgsConstructor
public class PlayerHealWorkload extends AbstractWorkload<Player> {

  private final UUID playerID;
  private final double healAmount;
  private final int times;
  private int ticksAlive;

  @Override
  protected Player getElement() {
    return Bukkit.getPlayer(this.playerID);
  }

  @Override
  protected boolean isInvalid(Player element) {
    if (this.ticksAlive++ == this.times) {
      return true;
    }
    return element == null || element.getHealth() == element.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
  }

  @Override
  protected void apply(Player element) {
    double current = element.getHealth();
    double max = element.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
    element.setHealth(Math.min(current + this.healAmount, max));
  }

}
