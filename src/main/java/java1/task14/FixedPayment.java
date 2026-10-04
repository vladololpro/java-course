package java1.task14;

public class FixedPayment implements PaymentPolicy {

    private final int price;

    public FixedPayment(int price) {
        this.price = price;
    }

    @Override
    public String describe() {
        return "fixed " + price;
    }
}