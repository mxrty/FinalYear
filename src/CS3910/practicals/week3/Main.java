package CS3910.practicals.week3;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        AntennaArray antennaArray = new AntennaArray(3, 90);
        double[] simpleSolution = main.generateValidDesign(antennaArray, 3);
        System.out.println("random : " + simpleSolution[0] + " " + simpleSolution[1] + " " + simpleSolution[2]);
        System.out.println("Peak SSL : " + antennaArray.evaluate(simpleSolution));
    }

    public double[] generateValidDesign(AntennaArray antennaArray, int antennae) {
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
