package CS3910.practicals.week3;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ParticleSwarmOptimisation {
    private AntennaArray antennaArray;
    private int swarmSize;

    private double[] globalBestPosition;
    private double globalBestSSL;

    public ParticleSwarmOptimisation(AntennaArray antennaArray, int swarmSize) {
        this.antennaArray = antennaArray;
        this.swarmSize = swarmSize;
    }

    public double[] findBestSolution(int timeLimit) {
        globalBestPosition = new double[antennaArray.getAntennaeCount()];
        globalBestSSL = Double.MAX_VALUE;

        ArrayList<Particle> swarm = initialiseSwarm(swarmSize);

        for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeLimit); stop > System.nanoTime(); ) {
            for (Particle particle : swarm) {
                double particleBestSSL = particle.getPersonalBestSSL();
                if (particleBestSSL < globalBestSSL) {
                    globalBestPosition = particle.getPersonalBestPosition();
                    globalBestSSL = particleBestSSL;
                }
            }

            for (Particle particle : swarm) {
                particle.update(antennaArray, globalBestPosition);
            }
        }
        return globalBestPosition;
    }

    public ArrayList<Particle> initialiseSwarm(int swarmSize) {
        VectorCalculator vectorCalculator = VectorCalculator.getInstance();
        ArrayList<Particle> swarm = new ArrayList<>();
        for (int i = 0; i < swarmSize; i++) {
            double[] initialPosition = Main.generateValidDesign(antennaArray);
            // A second feasible position is generated at random. Initial velocity is set to half the difference between this second position and initial position
            double[] initialVelocity = vectorCalculator.divide(vectorCalculator.subtract(initialPosition,Main.generateValidDesign(antennaArray)),2);
            swarm.add(new Particle(initialVelocity, Main.generateValidDesign(antennaArray), globalBestSSL));
        }
        return swarm;
    }
}
