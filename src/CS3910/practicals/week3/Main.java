package CS3910.practicals.week3;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();

//        double[] simpleSolution = main.generateValidDesign(antennaArray);
//        System.out.println("Random : " + simpleSolution[0] + " " + simpleSolution[1] + " " + simpleSolution[2]);
//        System.out.println("Peak SSL : " + antennaArray.evaluate(simpleSolution));

        // Lab sign off part 5
        int numberOfAntennae = 3;
        int angle = 90;
        int timeLimit = 3;

        AntennaArray antennaArray = new AntennaArray(numberOfAntennae, angle);
        ParticleSwarmOptimisation pso = new ParticleSwarmOptimisation(antennaArray, 20);
        double[] solution = pso.findBestSolution(timeLimit);

        System.out.println("Best solution :");
        for (double position : solution) {
            System.out.println(position);
        }
        System.out.println("SSL: " + antennaArray.evaluate(solution));
    }

    public static double[] generateValidDesign(AntennaArray antennaArray) {
        int antennae = antennaArray.getAntennaeCount();
        double[] design = new double[antennae];
        Random random = new Random();
        double[][] bounds = antennaArray.bounds();

        while (!antennaArray.is_valid(design)) {
            for (int i = 0; i < antennae - 1; i++) {
                double min = bounds[i][0];
                double range = bounds[i][1] - min;
                design[i] = min + (range * random.nextDouble());
            }
            design[antennae - 1] = antennae / 2.0;
        }

        return design;
    }
}
