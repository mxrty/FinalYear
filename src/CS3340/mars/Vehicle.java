package CS3340.mars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

class Vehicle extends Entity {
    public boolean carryingSample;

    public Vehicle(Location l) {
        super(l);
        this.carryingSample = false;
    }

    public void act(Field f, Mothership m, ArrayList<Rock> rocksCollected) {
        actCollaborative(f, m, rocksCollected);
        //actSimple(f, m, rocksCollected);
    }

    /*
    if carrying a sample and at the base then drop sample (1)
    if carrying a sample and not at the base then travel up gradient (2)
    if detect a sample then pick sample (3)
    if true then move randomly (4)
    if carrying a sample and not at the base then drop two crumbs and travel up gradient (5)
    if sense crumbs then pick up one crumb and travel down gradient (6)

    subsumption:
    (1) ≺ (5) ≺ (3) ≺ (6) ≺ (4)
     */
    public void actCollaborative(Field f, Mothership m, ArrayList<Rock> rocksCollected) {
        // if carrying a sample and at the base then drop sample (1)
        if (carryingSample && atBase(f)) {
            dropSample();
        }
        // if carrying a sample and not at the base then drop two crumbs and travel up gradient (5)
        else if (carryingSample && !atBase(f)) {
            dropCrumbs(f, 2);
            travelUpGradient(f, m);
        }
        // if detect a sample then pick sample (3)
        else if (detectSample(f)) {
            pickSample(f, rocksCollected);
        }
        // if sense crumbs then pick up one crumb and travel down gradient (6)
        else if (senseCrumbs(f)) {
            pickCrumb(f);
            travelDownGradient(f, m);
        }
        //if true then move randomly (4)
        else {
            moveRandomly(f);
        }
    }

    /*
    if carrying a sample and at the base then drop sample (1)
    if carrying a sample and not at the base then travel up gradient (2)
    if detect a sample then pick sample (3)
    if true then move randomly (4)

    subsumption:
    (1) ≺ (2) ≺ (3) ≺ (4)
     */
    public void actSimple(Field f, Mothership m, ArrayList<Rock> rocksCollected) {
        // if carrying a sample and at the base then drop sample (1)
        if (carryingSample && atBase(f)) {
            dropSample();
        }
        // if carrying a sample and not at the base then travel up gradient (2)
        else if (carryingSample && !atBase(f)) {
            travelUpGradient(f, m);
        }
        // if detect a sample then pick sample (3)
        else if (detectSample(f)) {
            pickSample(f, rocksCollected);
        }
        // if true then move randomly (4)
        else {
            moveRandomly(f);
        }
    }

    private boolean atBase(Field field) {
        return field.isNeighbourTo(this.getLocation(), Mothership.class);
    }

    private boolean detectSample(Field field) {
        return field.isNeighbourTo(this.getLocation(), Rock.class);
    }

    private boolean senseCrumbs(Field field) {
        return field.getCrumbQuantityAt(this.getLocation()) > 0;
    }

    private void dropSample() {
        carryingSample = false;
    }

    private void pickSample(Field field, ArrayList<Rock> rocksCollected) {
        Location sampleLocation = field.getNeighbour(this.getLocation(), Rock.class);
        rocksCollected.add((Rock) field.getObjectAt(sampleLocation));
        field.clearLocation(sampleLocation);
        carryingSample = true;
    }

    private void travelUpGradient(Field field, Mothership mothership) {
        ArrayList<Location> possibleMoves = field.getAllfreeAdjacentLocations(this.getLocation());
        if (!possibleMoves.isEmpty()) {
            Location previousLocation = this.getLocation();
            // To avoid getting stuck
            Collections.shuffle(possibleMoves);
            Location newLocation = possibleMoves.get(0);

            // Find strongest signal
            mothership.emitSignal(field);
            for (int i = 1; i < possibleMoves.size(); i++) {
                Location current = possibleMoves.get(i);
                if (field.getSignalStrength(current) > field.getSignalStrength(newLocation)) {
                    newLocation = current;
                }
            }

            field.place(this, newLocation);
            this.setLocation(newLocation);

            field.clearLocation(previousLocation);
        }
    }

    private void travelDownGradient(Field field, Mothership mothership) {
        ArrayList<Location> possibleMoves = field.getAllfreeAdjacentLocations(this.getLocation());
        if (!possibleMoves.isEmpty()) {
            Location previousLocation = this.getLocation();
            // To avoid getting stuck
            Collections.shuffle(possibleMoves);
            Location newLocation = possibleMoves.get(0);

            // Find strongest signal
            mothership.emitSignal(field);
            for (int i = 1; i < possibleMoves.size(); i++) {
                Location current = possibleMoves.get(i);
                if (field.getSignalStrength(current) < field.getSignalStrength(newLocation)) {
                    newLocation = current;
                }
            }

            // If crumbs adjacent, move to crumbs rather than away from signal
            for (Location loc : possibleMoves) {
                int crumbs = field.getCrumbQuantityAt(loc);
                if (crumbs > 0 && crumbs > field.getCrumbQuantityAt(newLocation)) {
                    newLocation = loc;
                }
            }

            field.place(this, newLocation);
            this.setLocation(newLocation);

            field.clearLocation(previousLocation);
        }
    }

    private void moveRandomly(Field field) {
        ArrayList<Location> possibleMoves = field.getAllfreeAdjacentLocations(this.getLocation());
        if (!possibleMoves.isEmpty()) {
            int numMoves = possibleMoves.size();
            Location previousLocation = this.getLocation();
            Location newLocation = possibleMoves.get(RandomGenerator.getInstance().nextInt(numMoves));

            field.place(this, newLocation);
            this.setLocation(newLocation);

            field.clearLocation(previousLocation);
        }
    }

    private void dropCrumbs(Field field, int crumbs) {
        field.dropCrumbs(this.getLocation(), 2);
    }

    private void pickCrumb(Field field) {
        field.pickUpACrumb(this.getLocation());
    }
}
