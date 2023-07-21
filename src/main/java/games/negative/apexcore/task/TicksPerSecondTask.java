package games.negative.apexcore.task;

import org.bukkit.scheduler.BukkitRunnable;

public class TicksPerSecondTask extends BukkitRunnable {

    private int tickCount = 0;
    private long lastTick = System.nanoTime();
    private double tps = 20.0;

    @Override
    public void run() {
        long currentTime = System.nanoTime();
        long tickTime = currentTime - lastTick;
        this.lastTick = currentTime;

        double tickTimeSeconds = ((double) tickTime) / 1_000_000_000.0;
        double currentTPS = 1.0 / tickTimeSeconds;
        double weight = 0.95; // Choose a weight factor to smooth TPS over time (must be between 0 and 1)

        this.tps = (weight * this.tps) + ((1 - weight) * currentTPS);
        this.tickCount = (this.tickCount + 1) % 20; // Reset every second (20 ticks = 1 second)
    }

    public double getTicks() {
        return tps;
    }
}
