package CS3910.coursework;

public class Order {

    private final int length;
    private final int quantity;

    public Order(int length, int quantity) {
        this.length = length;
        this.quantity = quantity;
    }

    public int getLength() {
        return length;
    }

    public int getQuantity() {
        return quantity;
    }
}
