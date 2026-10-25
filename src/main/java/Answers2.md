# Задание по Java 2 (Практика 4) - разборы задач

## Задание 1. Прочитать stack trace

### Теория
- `Stack trace` — это «след стека», который JVM печатает при падении. Он показывает, какой метод упал, с каким сообщением и по какой цепочке вызовов программа к этому пришла.
- Первый элемент в стеке — это место, где произошла ошибка (метод `methodD`).
- Последний элемент — это точка входа (`Main.main`).
- Ошибку нужно исправлять в источнике (в нашем случае — деление на ноль), а не просто «глушить» исключение.

### Демонстрация
Вывод программы:
```
=== Задание 1: Stack trace ===
Тип: java.lang.ArithmeticException
Сообщение: / by zero
Где возникло (первый элемент): java2.errors.StackTraceDemo.methodD(StackTraceDemo.java:33)
Цепочка вызовов:
  at java2.errors.StackTraceDemo.methodD(StackTraceDemo.java:33)
  at java2.errors.StackTraceDemo.methodC(StackTraceDemo.java:29)
  at java2.errors.StackTraceDemo.methodB(StackTraceDemo.java:25)
  at java2.errors.StackTraceDemo.methodA(StackTraceDemo.java:21)
  at java2.errors.StackTraceDemo.run(StackTraceDemo.java:8)
  at java2.Main.main(Main.java:10)
```
Объяснение: исключение возникло в `methodD` из-за деления на ноль. Цепочка вызовов показывает путь от `main` до места падения.

#### Правило
Всегда читайте stack trace сверху вниз, чтобы понять, где произошла ошибка, и снизу вверх, чтобы понять, как программа к этому пришла.

`----------------------------------`

## Задание 2. Checked или unchecked

### Теория
- **Checked (проверяемые):** Наследники `Exception` (но не `RuntimeException`). Компилятор заставляет их обрабатывать.
- **Unchecked (непроверяемые):** Наследники `RuntimeException`. Обычно означают баги в коде.
- **Error:** Наследники `Error` (например, `OutOfMemoryError`). Восстановить невозможно.

### Демонстрация
Вывод программы:
```
=== Задание 2: Checked или unchecked ===
Ситуация: Неверный аргумент (age = -5)
Решение: Unchecked (IllegalArgumentException) - баг программиста

Ситуация: Отсутствующий файл (config.txt)
Решение: Checked (FileNotFoundException) - можно восстановить (создать файл)

Ситуация: Нарушение состояния (баланс < 0)
Решение: Unchecked (IllegalStateException) - объект в неверном состоянии

Ситуация: Сетевой отказ (timeout)
Решение: Checked (IOException) - можно повторить попытку

Ситуация: Ошибка конфигурации (нет URL)
Решение: Checked (IOException) - прервать запуск и попросить проверить

Ситуация: Недостаток памяти (OutOfMemory)
Решение: Error (OutOfMemoryError) - восстановление невозможно

Ситуация: Ошибка парсинга числа (NumberFormat)
Решение: Unchecked (NumberFormatException) - неверные данные от пользователя

Ситуация: Файл занят другим процессом (IOException)
Решение: Checked (IOException) - закрыть файл и повторить

Ситуация: Деление на ноль
Решение: Unchecked (ArithmeticException) - баг в логике

Ситуация: Ссылка на null
Решение: Unchecked (NullPointerException) - баг в коде
```
Объяснение: программа классифицирует 10 ситуаций, показывая, к какому типу относится каждая.

#### Правило
Если ситуацию можно предвидеть и восстановить (нет файла) — `checked`. Если это баг программиста (неверный аргумент) — `unchecked`.

`----------------------------------`

## Задание 3. Собственное исключение

### Теория
- Свои исключения нужны, чтобы отличать ошибки бизнес-логики от системных.
- При оборачивании (`throw new MyException(..., cause)`) передаем исходное исключение как `cause`, чтобы не потерять контекст.
- В `toString()` и сообщении не должно быть лишних деталей.

### Демонстрация
Вывод программы:
```
=== Задание 3: Собственное исключение ===
Сообщение: Enrollment rejected. Reason: CAPACITY_FULL
Student: 1
Course: 10
Reason: CAPACITY_FULL

Сообщение: Enrollment rejected. Reason: PREREQUISITE_MISSING
Cause: Database is down
```
Объяснение: в первом случае бизнес-ошибка (нет мест), во втором — оборачивание системной ошибки (`Database is down`).

#### Правило
Всегда передавайте `cause` при оборачивании, чтобы сохранить цепочку ошибок.

`----------------------------------`

## Задание 4. try-catch-finally

### Теория
- `finally` выполняется всегда (даже если был `return` или `throw`).
- Если в `finally` написать `return`, он **перезапишет** возвращаемое значение и **скроет** любое исключение.

### Демонстрация
Вывод программы:
```
=== Задание 4: try-catch-finally ===
1. Возврат из try:
  try
  finally
Результат: try

2. Исключение в try, ловится в catch:
  try
  catch
  finally
Результат: catch

3. Ошибка в finally (скрывает исключение):
  try
  finally
Результат: finally
```
Объяснение: в третьем случае `return` в `finally` полностью скрыл исключение, и вернулось значение `"finally"`.

#### Правило
Никогда не пишите `return` в блоке `finally`.

`----------------------------------`

## Задание 5. Try-with-resources

### Теория
- `try-with-resources` автоматически закрывает ресурс.
- Если при закрытии тоже возникает исключение, оно добавляется как **suppressed (подавленное)**. Достать его можно через `e.getSuppressed()`.

### Демонстрация
Вывод программы:
```
=== Задание 5: Try-with-resources ===
1. Успешное выполнение:
  [OPEN] Resource-1
  [USE] Resource-1
  [CLOSE] Resource-1

2. Исключение в работе:
  [OPEN] Resource-2
  [USE] Resource-2
  [CLOSE] Resource-2
  [CATCH] Ошибка в работе!

3. Ошибка и в работе, и в close (suppressed):
  [OPEN] Resource-3
  [USE] Resource-3
  [CLOSE] Resource-3
  [CATCH] Основное: Главная ошибка в работе!
  [SUPPRESSED] Ошибка при закрытии Resource-3
```
Объяснение: в третьем случае основное исключение (в работе) и подавленное (в close) оба были пойманы и выведены.

#### Правило
Для работы с ресурсами всегда используйте `try-with-resources` и проверяйте `getSuppressed()` для диагностики.

`----------------------------------`

## Задание 6. Граница перевода исключения

### Теория
- Низкоуровневое исключение (например, `NumberFormatException`) должно быть переведено в бизнес-исключение (например, `CourseImportException`).
- При переводе обязательно передавать `cause`.
- Нельзя ловить `Exception`, ловите конкретные типы.

### Демонстрация
Вывод программы:
```
=== Задание 6: Граница перевода исключения ===
Ошибка: Import failed at record 1: Invalid number format
Запись №: 1
Причина: NumberFormatException
```
Объяснение: `NumberFormatException` был перехвачен и обернут в `CourseImportException` с номером записи.

#### Правило
Переводите низкоуровневые исключения в бизнес-исключения, сохраняя `cause`.

`----------------------------------`

## Задание 7. Ошибка как значение или исключение

### Теория
- **Optional** — для нормальной ситуации отсутствия (например, курс не найден).
- **Unchecked Exception** — для багов и нарушения контракта (например, отрицательная цена).

### Демонстрация
Вывод программы:
```
=== Задание 7: Ошибка как значение ===
1. findCourse (Optional - отсутствие это норма):
  Найден: Course Java

2. calculatePrice (Unchecked - баг в коде):
  Ошибка: Цена не может быть отрицательной: -100
```
Объяснение: `findCourse` вернул `Optional` (норма), `calculatePrice` бросил исключение (баг).

#### Правило
Выбирайте тип ошибки в зависимости от контекста: `Optional` для нормальных ситуаций, исключения — для критических.

`----------------------------------`

## Задание 8-15. Тестирование (JUnit 5)

### Теория
Мы настроили Maven и JUnit 5. Написали тесты для всех задач, используя:
- `@Test` и паттерн AAA (Arrange-Act-Assert).
- `@ParameterizedTest` и `@CsvSource` для проверки множества входных данных.
- `@TempDir` для тестов с файлами (в Java 5).
- `assertThrows` для проверки исключений.
- Таблицу решений (Decision Table) для бизнес-логики зачисления.
- `@BeforeEach` для независимости тестов (никакого `static state`).

### Демонстрация
Результат запуска тестов:
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running java2.Task08Test
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.079 s -- in java2.Task08Test
[INFO] Running java2.Task09Test
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.004 s -- in java2.Task09Test
[INFO] Running java2.Task10Test
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.180 s -- in java2.Task10Test
[INFO] Running java2.Task11Test
ERR
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 s -- in java2.Task11Test
[INFO] Running java2.Task12And13Test
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.030 s -- in java2.Task12And13Test
[INFO] Running java2.Task14Test
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.005 s -- in java2.Task14Test
[INFO] Running java2.Task15Test
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.011 s -- in java2.Task15Test

...

[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 60, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```