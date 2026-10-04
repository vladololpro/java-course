package java1.task14;

public class SubscriptionPayment implements PaymentPolicy {

    private final int monthlyPrice;

    public SubscriptionPayment(int monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    @Override
    public String describe() {
        return "subscription " + monthlyPrice + "/month";
    }
}