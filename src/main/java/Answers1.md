# Задача 1. Один контракт, две реализации

## Что сделано:
- Интерфейс `CourseFormatter` с методом `format(Course)`.
- Реализации `CompactCourseFormatter` и `DetailedCourseFormatter`.
- Демонстрация в `Main`: одна переменная интерфейсного типа, две реализации.

## Что известно компилятору:
- Компилятор видит только статический тип переменной - `CourseFormatter`.
  Он проверяет, что метод `format(Course)` объявлен в этом типе, и что
  типы аргумента и возвращаемого значения совпадают.
- Что лежит внутри переменной - `CompactCourseFormatter` или
  `DetailedCourseFormatter` - компилятор не анализирует. Оба класса
  реализуют интерфейс, значит, оба умеют `format`.

## Эксперимент:
- Если убрать метод `format` из `CourseFormatter`, компилятор выдаёт четыре ошибки:

```
vlad@misteRus:/mnt/d/vscode_work/DV_java/java-course$ javac -d out src/main/java/model/Course.java src/main/java/java1/task01/*.java
src/main/java/java1/task01/CompactCourseFormatter.java:9: error: method does not override or implement a method from a supertype
    @Override
    ^
src/main/java/java1/task01/DetailedCourseFormatter.java:8: error: method does not override or implement a method from a supertype
    @Override
    ^
src/main/java/java1/task01/Main.java:21: error: cannot find symbol
        System.out.println(formatter.format(course));
                                    ^
  symbol:   method format(Course)
  location: variable formatter of type CourseFormatter
src/main/java/java1/task01/Main.java:25: error: cannot find symbol
        System.out.println(formatter.format(course));
                                    ^
  symbol:   method format(Course)
  location: variable formatter of type CourseFormatter
4 errors
```

- Хотя в `CompactCourseFormatter` и `DetailedCourseFormatter` методы
`format` остались - компилятор их не видит, потому что смотрит на тип
переменной (`CourseFormatter`), а не на её содержимое.

## Что выбирается во время выполнения:
- JVM во время выполнения смотрит на динамический тип объекта - фактический класс, лежащий в переменной, - и вызывает его реализацию
метода `format`. Поэтому один и тот же вызов `formatter.format(course)` даёт разный результат в зависимости от того, какой объект сейчас в переменной.