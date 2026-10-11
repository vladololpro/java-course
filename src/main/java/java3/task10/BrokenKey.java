package java3.task10;

/* Ключ, где в hashCode участвует и tag.
 * Если tag изменить после добавления в HashSet - элемент «потеряется». */
public class BrokenKey {

    private final long id;
    private String tag;

    public BrokenKey(long id, String tag) {
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
        if (!(o instanceof BrokenKey k)) return false;
        return k.id == id && k.tag.equals(tag);
    }

    @Override
    public int hashCode() {
        return 31 * Long.hashCode(id) + tag.hashCode();
    }

    @Override
    public String toString() {
        return "BrokenKey(id=" + id + ", tag=" + tag + ")";
    }
}