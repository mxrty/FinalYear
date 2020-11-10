package CS3910.practicals.week3;

public class Particle {
    private double velocity;
    private double position;
    private double personalBestPosition;

    public Particle() {
        velocity = 0;
        position = 0;
        personalBestPosition = position;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
        if (position > personalBestPosition) {
            personalBestPosition = position;
        }
    }

    public double getPersonalBestPosition() {
        return personalBestPosition;
    }
}
