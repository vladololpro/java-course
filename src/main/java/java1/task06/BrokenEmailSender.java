package java1.task06;

public class BrokenEmailSender extends BrokenSender {

    /* @Override - без override ошибки не видно, что приводит к ловушке*/
    public String send(Object message) {
        return "broken.send(Object): " + message;
    }
}