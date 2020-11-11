package CS3910.practicals.week3;

public class Particle {
    private double[] currentVelocity;
    private double[] currentPosition;
    private double[] personalBestPosition;
    private double personalBestSSL;

    private static final double PHI = 1.1193;
    private static final double ETA = 0.721;

    private VectorCalculator vectorCalculator;

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
        currentPosition = vectorCalculator.sum(currentPosition,currentVelocity);
        // Inertia = eta * velocity
        double[] inertia = vectorCalculator.multiply(currentVelocity, ETA);

        // Cognitive Attraction = phi * uniformRandomVectorOne * (personalBestPosition - position)
        double[] cognitiveAttraction = vectorCalculator.multiply(
                vectorCalculator.multiply(antennaArray.getUniformRandomVector(),
                        vectorCalculator.subtract(personalBestPosition, currentPosition)), PHI);

        // Social Attraction = phi * uniformRandomVectorTwo * (globalBestPosition - position)
        double[] socialAttraction = vectorCalculator.multiply(
                vectorCalculator.multiply(antennaArray.getUniformRandomVector(),
                        vectorCalculator.subtract(globalBestPosition, currentPosition)), PHI);

        // velocity = inertia + cognitiveAttraction + socialAttraction
        currentVelocity = vectorCalculator.sum(inertia,cognitiveAttraction,socialAttraction);

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
