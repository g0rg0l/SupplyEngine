package self.engine;

import self.simulation.Simulation;

public class Engine {
    public void run(Simulation simulation) {
        Thread gameLoop = new Thread(() -> {
            final int FRAMES_PER_SECOND = 144;
            final long TIME_BETWEEN_UPDATES = 1000000000 / FRAMES_PER_SECOND;
            final int MAX_UPDATES_BETWEEN_RENDER = 1;

            long lastUpdateTime = System.nanoTime();
            long currTime = System.currentTimeMillis();

            while (simulation.isRunning()) {
                long now = System.nanoTime();
                long elapsedTime = System.currentTimeMillis() - currTime;
                currTime += elapsedTime;

                /* Updating (can be more than 1 times if that needed) */
                int updateCount = 0;
                while (now - lastUpdateTime >= TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BETWEEN_RENDER) {
                    float dt = (float) elapsedTime / 1000;
                    simulation.update(dt);
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                }

                /* Waiting for needed time to this frame */
                if (now - lastUpdateTime >= TIME_BETWEEN_UPDATES) {
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }

                /* Rendering */
                simulation.repaint();

                /* Yielding thread while possible */
                long lastRenderTime = now;
                while (now - lastRenderTime < TIME_BETWEEN_UPDATES && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                    Thread.yield();
                    now = System.nanoTime();
                }
            }
        });

        gameLoop.start();
    }
}