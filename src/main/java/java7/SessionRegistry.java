package java7;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionRegistry {

    public record ActiveSession(long sessionId, long studentId, String courseCode) {
    }

    private final ConcurrentHashMap<Long, ActiveSession> sessions = new ConcurrentHashMap<>();

    public boolean createIfAbsent(ActiveSession session) {
        return sessions.putIfAbsent(session.sessionId(), session) == null;
    }

    public Optional<ActiveSession> find(long sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    public boolean remove(long sessionId) {
        return sessions.remove(sessionId) != null;
    }

    public int activeCount() {
        return sessions.size();
    }

    public Map<Long, ActiveSession> snapshot() {
        return Map.copyOf(sessions);
    }
}
