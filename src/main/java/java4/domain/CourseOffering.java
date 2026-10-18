package java4.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/* Модель вебинара (Course Offering). */
public final class CourseOffering {

    private final String title;
    private final Instant startInstant;
    private final ZoneId organizerZone;
    private final Money basePrice;
    private final Instant earlyBirdDeadline;
    private final Money earlyBirdDiscount;
    private final int totalSessions;
    private final int completedSessions;

    public CourseOffering(String title, Instant startInstant, ZoneId organizerZone, Money basePrice, Instant earlyBirdDeadline, Money earlyBirdDiscount, int totalSessions, int completedSessions) {
        this.title = title;
        this.startInstant = startInstant;
        this.organizerZone = organizerZone;
        this.basePrice = basePrice;
        this.earlyBirdDeadline = earlyBirdDeadline;
        this.earlyBirdDiscount = earlyBirdDiscount;
        this.totalSessions = totalSessions;
        this.completedSessions = completedSessions;
    }

    public ZonedDateTime getStartInOrganizerZone() {
        return startInstant.atZone(organizerZone);
    }

    public ZonedDateTime getStartInUserZone(ZoneId userZone) {
        return startInstant.atZone(userZone);
    }

    public boolean isEarlyBird(Instant purchaseInstant) {
        return purchaseInstant.isBefore(earlyBirdDeadline);
    }

    public Money getBasePrice() {
        return basePrice;
    }

    public Money getEarlyBirdDiscount() {
        return earlyBirdDiscount;
    }

    public int getTotalSessions() {
        return totalSessions;
    }

    public int getCompletedSessions() {
        return completedSessions;
    }

    public int getRemainingSessions() {
        return totalSessions - completedSessions;
    }

    @Override
    public String toString() {
        return "CourseOffering{" + title + ", start=" + startInstant + "}";
    }
}