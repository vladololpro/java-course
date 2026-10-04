package java1.task14;

public class FreePayment implements PaymentPolicy {

    @Override
    public String describe() {
        return "free";
    }
}