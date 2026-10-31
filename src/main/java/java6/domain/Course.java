package java6.domain;

import java.util.List;

/* Модель курса с модулями (темами). */
public record Course(long id, String code, String level, int duration, List<String> modules) {
}
