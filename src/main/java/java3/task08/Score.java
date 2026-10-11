package java3.task08;

/* Собственный value object: счёт. Реализует Comparable<Score>,
 * значит объекты можно сравнивать между собой. */
public class Score implements Comparable<Score> {

    private final int points;

    public Score(int points) {
        this.points = points;
    }

    public int points() {
        return points;
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(this.points, other.points);
    }

    @Override
    public String toString() {
        return "Score(" + points + ")";
    }
}