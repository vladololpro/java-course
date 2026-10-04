# Задача 1. Один контракт, две реализации

## Что сделано:
- Интерфейс `CourseFormatter` с методом `format(Course)`.
- Реализации `CompactCourseFormatter` и `DetailedCourseFormatter`.
- Демонстрация в `Main`: одна переменная интерфейсного типа, две реализации.

## Что известно компилятору:
- Компилятор видит только статический тип переменной - `CourseFormatter`. Он проверяет, что метод `format(Course)` объявлен в этом типе, и что типы аргумента и возвращаемого значения совпадают.
- Что лежит внутри переменной - `CompactCourseFormatter` или `DetailedCourseFormatter` - компилятор не анализирует. Оба класса реализуют интерфейс, значит, оба умеют `format`.

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

- Хотя в `CompactCourseFormatter` и `DetailedCourseFormatter` методы `format` остались - компилятор их не видит, потому что смотрит на тип переменной (`CourseFormatter`), а не на её содержимое.

## Что выбирается во время выполнения:
- JVM во время выполнения смотрит на динамический тип объекта - фактический класс, лежащий в переменной, - и вызывает его реализацию метода `format`. Поэтому один и тот же вызов `formatter.format(course)` даёт разный результат в зависимости от того, какой объект сейчас в переменной.

`----------------------------------`

# Задача 2. Dynamic dispatch на бумаге

## Что сделано:
- Иерархия `NotificationSender` -> `EmailSender`, `ConsoleSender`.
- Метод `send(String)` переопределён в наследниках (override).
- Метод `send(String, int)` добавлен в родителя как перегруженный (overload).
- `Main` вызывает методы через переменные разных статических типов.

## Override: выбор по динамическому типу:
- Метод `send(String)` есть у родителя и переопределён у наследников. Компилятор проверяет только, что метод объявлен в статическом типе переменной. Какую реализацию вызвать - решает JVM по фактическому классу объекта.

- Пример:
  - `NotificationSender asBase = new EmailSender();`.
  - `asBase.send("hello");` → `EmailSender.send: hello`.
  - Статический тип - `NotificationSender`, но объект - `EmailSender`, поэтому вызывается его реализация.

## Overload: выбор по статическому типу:
- Метод `send(String, int)` объявлен только в `NotificationSender`. `EmailSender` его не переопределял (переопределил только `send(String)`).
- Компилятор ищет подходящий метод по статическому типу переменной и типам аргументов.   Находит `NotificationSender.send(String, int)` и связывает вызов с ним.

- Пример:
  - `NotificationSender asBase = new EmailSender();`
  - `asBase.send("hello", 5);` → `NotificationSender.send(priority=5): hello`
  - Хотя объект - `EmailSender`, у него нет собственной версии `send(String, int)`, поэтому вызывается родительская.

`----------------------------------`

# Задача 3. Полиморфный массив

## Что сделано:
- Массив интерфейсного типа `CourseFormatter[]` с тремя реализациями.
- Обход через `for-each` без `if` по типу.
- Добавлена третья реализация `JsonCourseFormatter`, интерфейс и первые две реализации переиспользованы из `task01`.
- Обход не изменился при добавлении третьей реализации.

## Почему это полиморфный массив
- Массив объявлен как `CourseFormatter[]` - то есть массив интерфейсного типа. В него можно положить объект любого класса, реализующего `CourseFormatter`.
- При обходе переменная `formatter` имеет тип `CourseFormatter`. На каждом вызове `formatter.format(course)` JVM смотрит на динамический тип объекта (фактический класс) и вызывает его реализацию. Поэтому один и тот же код обхода работает по-разному для разных элементов массива.

## Почему нет `if` по типу
- Логика «что делать» инкапсулирована в самих классах-реализациях через метод `format`. Клиенту не нужно проверять, кто именно в массиве: он просто вызывает метод контракта. Это и есть «клиент зависит только от интерфейса».

## Добавление четвёртой реализации
- Если появится новая реализация (например, `MisterusCourseFormatter`):
1. Создаётся новый класс, реализующий `CourseFormatter`.
2. Он добавляется в массив в `Main`.
3. Код обхода НЕ меняется.
- Это главный критерий задачки: расширение не затрагивает клиентский код.

`----------------------------------`

# Задача 4. Абстрактный базовый класс

## Что сделано:
- Абстрактный класс `AbstractNotificationSender` с полем `recipient`.
- Публичный метод `send(String)` - общий алгоритм, помечен `final`.
- Абстрактный метод `doSend(String)` - точка расширения.
- Наследники `EmailSender`, `ConsoleSender` реализуют `doSend`.
- `Main` демонстрирует три случая: email, console, пустой адресат.

## Что общего в алгоритме
- Метод `send(String)` описывает одинаковую последовательность для всех наследников:
1. Проверить адресата.
2. Напечатать начало отправки.
3. Вызвать `doSend` — специфичную реализацию наследника.
4. Напечатать завершение.
- Шаги 1, 2, 4 не зависят от типа отправителя - они в родителе. Шаг 3 разный - он вынесен в абстрактный `doSend`.

## Почему `send` сделан `final`
- `final` запрещает переопределение. Это значит, что наследник не может изменить общий алгоритм: не может, например, пропустить проверку адресата или изменить порядок шагов. Инвариант поведения защищён.
- Если бы `send` не был `final`, наследник мог бы его переопределить и сломать контракт родителя - например, отправлять пустому адресату. `final` это запрещает на уровне компилятора.

## Что даёт абстрактный класс
- Общий код (проверка адресата, обрамление) не дублируется.
- Каждый наследник обязан реализовать `doSend` - компилятор это проверяет.
- Создать `new AbstractNotificationSender(...)` нельзя - этот класс только для наследования.