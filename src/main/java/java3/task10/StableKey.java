package java3.task10;

/* Ключ, где в hashCode участвует только id.
 * tag можно менять - на hashCode это не влияет. */
public class StableKey {

    private final long id;
    private String tag;

    public StableKey(long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public long id() { return id; }
    public String tag() { return tag; }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StableKey k)) return false;
        return k.id == id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return "StableKey(id=" + id + ", tag=" + tag + ")";
    }
}