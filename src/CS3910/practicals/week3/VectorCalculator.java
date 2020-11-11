package CS3910.practicals.week3;

public final class VectorCalculator {

    private static VectorCalculator INSTANCE;

    public static VectorCalculator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VectorCalculator();
        }

        return INSTANCE;
    }

    public static double[] multiply(double[] vector1, double var1) {
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] * var1;
        }

        return result;
    }

    public static double[] multiply(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] * vector2[i];
        }

        return result;
    }

    public static double[] divide(double[] vector1, double var1) {
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] / var1;
        }

        return result;
    }

    public static double[] sum(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] + vector2[i];
        }

        return result;
    }

    public static double[] sum(double[] vector1, double[] vector2, double[] vector3) {
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] + vector2[i] + vector3[i];
        }

        return result;
    }

    public static double[] subtract(double[] minuend, double[] subtrahend) {
        double[] result = new double[minuend.length];
        for (int i = 0; i < minuend.length; i++) {
            result[i] = minuend[i] - subtrahend[i];
        }

        return result;
    }
}
