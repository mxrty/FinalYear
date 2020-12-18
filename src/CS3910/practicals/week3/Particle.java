package CS3910.practicals.week3;

public class Particle {
    private static final double PHI = 1.1193;
    private static final double ETA = 0.721;
    private double[] currentVelocity;
    private double[] currentPosition;
    private double[] personalBestPosition;
    private double personalBestSSL;
    private final VectorCalculator vectorCalculator;

    /*
    TODO: rename velocityArrayDesign,currentArrayDesign,currentArrayDesignSSL,personalBestSSL
     */

    public Particle(double[] initialVelocity, double[] initialPosition, double currentSSL) {
        currentVelocity = initialVelocity;
        currentPosition = initialPosition;
        personalBestPosition = currentPosition;
        personalBestSSL = currentSSL;

        vectorCalculator = VectorCalculator.getInstance();
    }

    public void update(AntennaArray antennaArray, double[] globalBestPosition) {
        currentPosition = VectorCalculator.sum(currentPosition, currentVelocity);
        // Inertia = eta * velocity
        double[] inertia = VectorCalculator.multiply(currentVelocity, ETA);

        // Cognitive Attraction = phi * uniformRandomVectorOne * (personalBestPosition - position)
        double[] cognitiveAttraction = VectorCalculator.multiply(
                VectorCalculator.multiply(antennaArray.getUniformRandomVector(),
                        VectorCalculator.subtract(personalBestPosition, currentPosition)), PHI);

        // Social Attraction = phi * uniformRandomVectorTwo * (globalBestPosition - position)
        double[] socialAttraction = VectorCalculator.multiply(
                VectorCalculator.multiply(antennaArray.getUniformRandomVector(),
                        VectorCalculator.subtract(globalBestPosition, currentPosition)), PHI);

        // velocity = inertia + cognitiveAttraction + socialAttraction
        currentVelocity = VectorCalculator.sum(inertia, cognitiveAttraction, socialAttraction);

        // Unsure if section is needed
        if (antennaArray.is_valid(currentPosition)) {
            double currentSSL = antennaArray.evaluate(currentPosition);
            if (currentSSL < personalBestSSL) {
                personalBestPosition = currentPosition;
                personalBestSSL = currentSSL;
            }
        }
    }

    public double[] getPersonalBestPosition() {
        return personalBestPosition;
    }

    public double getPersonalBestSSL() {
        return personalBestSSL;
    }
}
