# Задача 1. List и порядок

## Условие
- Сохраните последовательность регистраций, вставьте и удалите элемент, найдите дубликаты. Сравните ArrayList и LinkedList для чтения по индексу и обхода; выбор подтвердите измерением без заявления о микробенчмарке.

## Что сделано
- Модель `Registration` с полями studentId и courseId, а также equals и toString.
- `ListDemo` с тремя методами: basicOperations, findDuplicates, measure.
- `Main` вызывает три метода.

## Операции с List
- `add(element)` - добавляет в конец.
- `add(index, element)` - вставляет на позицию index, сдвигая остальные вправо.
- `remove(index)` - удаляет элемент по индексу (не по значению), сдвигая остальные влево.
- `get(index)` - читает по индексу.
- `for-each` - обход в порядке добавления.

  - List сохраняет порядок, допускает дубликаты.

## Поиск дубликатов
- Без Set - это просто двойной цикл. Для каждого элемента проверяем, не сообщали ли о нём раньше. Если нет - считаем вхождения. Печатаем, если их больше 1.
- Результат на тестовых данных:
```
Reg(student=1, course=10) x3
Reg(student=2, course=10) x2
```

## Сравнение ArrayList и LinkedList
- Измерение (10 000 чтений по случайным индексам, 100 000 элементов):
```
Index access (10k) ArrayList:  0 ms
Index access (10k) LinkedList: 247 ms
Iteration ArrayList:  3 ms
Iteration LinkedList: 5 ms
```
- Порядок величин:
  - Чтение по индексу: ArrayList O(1), LinkedList O(n). Разница в сотни раз - структурная, не случайная.
  - Обход: разница меньше (3 мс против 5 мс). LinkedList идёт по ссылкам последовательно, процессор кэширует.

`----------------------------------`

# Задача 2. Set и уникальность

## Условие
- Удалите дубликаты CourseCode, сохранив первый порядок появления. Выберите реализацию set и объясните влияние equals/hashCode.

## Что сделано
- `CourseCodeWithoutEquals` - модель без equals/hashCode. Показывает проблему.
- `CourseCode` - модель с equals/hashCode. Показывает решение.
- `SetDemo` - два метода: showProblem и solve.
- `Main` - точка входа.

## Что такое Set
- Set - коллекция без дубликатов. В отличие от List, каждый элемент может встречаться не более одного раза. Если добавить тот же элемент - размер не увеличится.
- Три главные реализации:
  - HashSet - порядок не гарантируется. Быстрый.
  - LinkedHashSet - сохраняет порядок вставки.
  - TreeSet - держит элементы отсортированными.

## Выбор реализации
- Условие: «сохранив первый порядок появления». Значит, нужен LinkedHashSet.
- HashSet в методе showProblem используется только для демонстрации проблемы: без equals/hashCode даже HashSet не убирает дубликаты.

## Как Set определяет дубликаты
- Через equals и hashCode. При добавлении в HashSet:
1. Считается hashCode элемента.
2. По хешу находится корзина.
3. В корзине ищется элемент через equals.
4. Если найден - не добавляется.

## Почему порядок разный у HashSet и LinkedHashSet
- HashSet хранит элементы по хеш-таблицам. Порядок обхода зависит от хешей и размера массива корзин. Пользователь не может на него рассчитывать.
- LinkedHashSet внутри HashSet плюс связный список, хранящий порядок вставки. Обход идёт по этому списку - в порядке добавления.

`----------------------------------`

# Задача 3. Map и индекс

## Условие

- Постройте индекс CourseId -> Course, обработайте повторный ID без молчаливой перезаписи. Сравните get, containsKey, putIfAbsent и computeIfAbsent.

## Что сделано
- CourseIndex с Map<Long, Course> на HashMap.
- Методы addSafely, get, contains, putIfAbsent, computeIfAbsent.
- Main демонстрирует все четыре метода.

## Что такое Map
- Map<K, V> - коллекция пар «ключ -> значение». По ключу можно быстро найти значение. Ключи уникальны, значения могут повторяться. Порядок зависит от реализации.
- Для задачи «индекс CourseId -> Course» выбрана HashMap: поиск по ключу O(1), порядок не важен, ключи Long уже имеют корректные equals/hashCode.

## Молчаливая перезапись
- Метод put(k, v) перезаписывает значение, если ключ уже есть, и ничего об этом не сообщает. Если это не задумано - баг. Чтобы избежать - используют containsKey + put, либо putIfAbsent, либо computeIfAbsent.

## Сравнение методов
- get(k) - вернуть значение или null. Не меняет Map.
- containsKey(k) - true/false, есть ли ключ. Не меняет Map.
- put(k, v) - положить значение. Если ключ был - перезаписать. Возвращает предыдущее значение или null.
- putIfAbsent(k, v) - положить, только если ключа нет. Возвращает текущее (старое) значение или null.
- computeIfAbsent(k, f) - если ключа нет, вычислить через функцию и положить. Функция вызывается только когда ключа нет.

### Демонстрация
- Add safely (без перезаписи):
```
add java:    true
add algo:    true
add javaDup: false
size: 2
course 1 still: Java Basics
```
  - javaDup не добавлен - id=1 уже был. Старое значение осталось.

- containsKey / get:
```
contains 1: true
contains 9: false
get 1: Java Basics
get 9: null
```
  - get возвращает null, если ключа нет. Это норма, не ошибка.

- putIfAbsent:
```
putIfAbsent on existing id=1 returned: Java Basics
course 1 still: Java Basics
putIfAbsent on new id=3 returned: null
course 3 now: Databases
```
  - На существующий ключ - ничего не делает, возвращает старое.
  - На новый - кладёт, возвращает null.

- computeIfAbsent:
```
computeIfAbsent on existing id=1: Java Basics
computeIfAbsent on new id=4: Networks
course 4 now: Networks
```
  - На существующий - не вызывает функцию, возвращает старое.
  - На новый - вызывает функцию, кладёт результат.

`----------------------------------`

# Задача 4. Очередь ожидания

## Условие
- Реализуйте FIFO waitlist через Queue: добавить, посмотреть следующего, извлечь, обработать пустую очередь. Объясните пары add/offer, remove/poll, element/peek.

## Что сделано
- Waitlist с Queue<String> на ArrayDeque.
- Методы: add (offer), peekNext (peek), pollNext (poll), isEmpty, size.
- Main демонстрирует FIFO, пустую очередь, пары методов.

## Что такое Queue
- Queue - коллекция в порядке FIFO: первый добавленный извлекается первым. Как очередь в магазине. Добавление - в конец, извлечение - с начала. Индексов нет.
- Реализации:
  - ArrayDeque - массив, современный выбор для очереди.
  - LinkedList - список, тоже работает.
  - PriorityQueue - извлекает по приоритету, не FIFO.
- Выбрана ArrayDeque: FIFO и быстро.

## Разбор методов
- Методы:
  - add(e) / offer(e) - добавить.
  - remove() / poll() - извлечь.
  - element() / peek() - посмотреть голову без извлечения.
- Разница:
  - Первая версия (add, remove, element) бросает исключение при неудаче.
  - Вторая версия (offer, poll, peek) возвращает null/false.

- На неограниченных очередях (ArrayDeque, LinkedList) add и offer работают одинаково - обе добавляют.
- remove на пустой очереди бросает NoSuchElementException. poll на пустой возвращает null.
- element на пустой бросает NoSuchElementException. peek на пустой возвращает null.

- Использование:
  - offer/poll/peek - безопасные. Не бросают исключений. Обычно используют их.
  - add/remove/element - когда пустая очередь это ошибка, и хочется узнать об этом через исключение.

### Демонстрация
- FIFO:
```
size: 3
peekNext: student-1
pollNext: student-1
pollNext: student-2
size after 2 polls: 1
peekNext: student-3
```
  - Кто первый пришёл - тот первый ушёл. peekNext показывает голову, не извлекая pollNext извлекает.

- Пустая очередь:
```
isEmpty: true
peek on empty: null
poll on empty: null
```
  - peek и poll на пустой очереди возвращают null. Если бы использовали element или remove - было бы исключение.

- Пары методов:
```
after offer A, B: [A, B]
element: A
peek: A
still: [A, B]
remove: A
poll: B
after remove and poll: []
```
  - element и peek показывают голову, очередь не меняется. remove и poll извлекают.

`----------------------------------`

# Задача 5. Безопасное удаление при обходе

## Условие
- Воспроизведите ConcurrentModificationException при удалении из list в enhanced for. Исправьте через iterator и через removeIf. Объясните, почему копирование всей коллекции не всегда лучший default.

## Что сделано
- Main с четырьмя методами:
  - broken - воспроизводит ConcurrentModificationException.
  - viaIterator - исправление через Iterator.remove.
  - viaRemoveIf - исправление через removeIf.
  - copyWay - альтернатива через копию, с оговоркой, почему это не лучший default.

## Как работает for-each
- Когда мы пишем `for (String s : list)`, кажется, что цикл сам ходит по элементам списка. Но на самом деле цикл просит у списка специальный объект - Iterator - и ходит через него. То есть `for (String s : list)` - это короткая запись для:
```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    ...
}
```
  - Компилятор заменяет одно на другое. Когда мы пишем for-each, мы на самом деле работаем с Iterator, просто не видим его.
- Iterator - объект, который водит нас по коллекции. У него есть hasNext (есть ли ещё), next (дай следующий) и remove (удали тот, что только что вернул).

## Почему возникает исключение
- ArrayList внутри себя считает, сколько раз её меняли. Каждое добавление или удаление увеличивает этот счётчик. Назовём его счётчик изменений.
- Когда мы просим у списка Iterator, он запоминает текущее значение этого счётчика. Скажем, на момент старта было 4 изменения.
- Потом на каждом next Iterator проверяет: счётчик изменений всё ещё 4?
- Если да - ничего не менялось, продолжаем.
- Если нет - список изменился после того, как мы начали обход.
- Iterator не знает, как именно он изменился. Его внутренние расчёты «где мы находимся» больше не верны. Продолжать нельзя.
- В такой ситуации Iterator не пытается угадать, что делать. Он бросает ConcurrentModificationException - «коллекция изменилась, пока я по ней ходил».
- Теперь почему удаление через list.remove вызывает исключение.
- Когда мы пишем:
```java
for (String s : list) {
    if (s.equals("B")) {
        list.remove(s);
    }
}
```
  - мы удаляем элемент через сам список. Список увеличивает свой счётчик изменений.
- А Iterator об этом не знает. У него в памяти всё ещё старое значение: было 4. Теперь в списке стало 5. Iterator на следующем next это видит: «Я помню 4. Сейчас 5. Кто-то изменил список. Я не могу продолжать, потому что не знаю, что изменилось». И бросает исключение.

## Почему Iterator.remove не вызывает исключение
- Идея: раз проблема в том, что список меняется в обход Iterator, давай менять его через Iterator:
```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    if (s.equals("B")) {
        it.remove();
    }
}
```
- Что делает it.remove():
  - Удаляет элемент из списка (список увеличивает счётчик).
  - Сам обновляет своё запомненное значение - теперь оно совпадает с новым счётчиком списка.
- На следующем next проверка проходит: счётчик списка = счётчик в Iterator. Всё в порядке. Исключения нет.
- Таким образом, Iterator следит за изменениями списка. Если список меняется в обход Iterator - Iterator теряет ориентацию и бросает исключение, чтобы мы не работали с неправильными данными. Если менять список через Iterator - он знает о каждом изменении и продолжает корректно.

## Исправление через removeIf
```java
list.removeIf(s -> s.equals("B"));
```
  - removeIf сам внутри использует Iterator, поэтому безопасен.
- Запись `s -> s.equals("B")` - короткая форма условия: «для каждого элемента s проверь, равен ли он B». Слева от стрелки - параметр, справа - что вернуть.
- Раньше писали длинно, через анонимный класс:
```java
list.removeIf(new Predicate<String>() {
    @Override
    public boolean test(String s) {
        return s.equals("B");
    }
});
```
  - Это то же самое, но громоздко. Java позволяет писать короче.
- Результат: [A, C, D].

## Альтернатива через копию
```java
List<String> copy = new ArrayList<>(list);
for (String s : copy) {
    if (s.equals("B")) {
        list.remove(s);
    }
}
```
  - Обходим копию, удаляем из оригинала. Iterator копии не следит за оригиналом, исключения нет. Работает.

## Почему копирование - не лучший default
- Память: копия дублирует все элементы. Для большого списка двойной расход.
- Время: копирование O(n), даже если удалить надо один элемент.
- Не атомарно: между копированием и удалением оригинал может измениться в многопоточном коде.
- Не нужно: Iterator и removeIf решают задачу без копии.
  - Копирование оправдано редко: если элементов мало, если логика сложнее простого удаления, или если удалять надо много элементов и проще пройтись по копии. Как default - не годится.

### Демонстрация
```
=== Broken: remove in for-each ===
ConcurrentModificationException: null
list after failure: [A, C, D]

=== Fix 1: via Iterator ===
after Iterator: [A, C, D]

=== Fix 2: via removeIf ===
after removeIf: [A, C, D]

=== Alternative: via copy ===
after copy way: [A, C, D]
```
- В первом блоке list.remove(s) удалил B, но следующий next обнаружил изменение счётчика и бросил исключение. Обход прервался. Список уже изменён: [A, C, D].
- Сообщение исключения может быть null - у ConcurrentModificationException часто нет текста.
- В остальных трёх блоках результат один и тот же: [A, C, D]. Разница только в способе.

`----------------------------------`

# Задача 6. Первый generic-тип

## Условие
- Реализуйте Result<T> или Page<T> без raw types. Добавьте фабрики успеха/ошибки, инварианты и методы чтения. Покажите две конкретизации и compile-time защиту от смешения.

## Что сделано
- Result<T> - generic-класс, хранит либо значение типа T (успех), либо сообщение об ошибке (провал).
- Фабрики: Result.success(value) и Result.failure(message).
- Методы чтения: isSuccess, value, message.
- Main показывает две конкретизации: Result<Course> и Result<String>.

## Что такое generic
- Generic - параметр типа. Пишется в угловых скобках: <T>. Класс работает с любым типом, но компилятор проверяет типы.
- Без generic пришлось бы хранить Object и приводить вручную, ошибки вылезали бы в runtime.
- С generic компилятор знает тип заранее. Попытка положить не тот тип - ошибка компиляции.

## Raw type
- Raw type - использование generic-класса без параметра: Box box = new Box();
- Компилятор тогда работает с Object, проверка типов теряется.
- В современном Java raw types не используют.

## Инварианты Result
- Либо success = true, value не null, message null.
- Либо success = false, value null, message не null.
- Оба поля не могут быть заполнены одновременно. Оба не могут быть null одновременно.
- Это обеспечивается приватным конструктором и фабриками: success и failure - единственные способы создать Result.

### Демонстрация
```
=== Result<Course> ===
isSuccess: true
value: Java Basics
message: null

isSuccess: false
value: null
message: course not found

=== Result<String> ===
isSuccess: true
value: hello
message: null

isSuccess: false
value: null
message: bad input
```
- Для success: value заполнено, message null.
- Для failure: value null, message заполнено.
- Две конкретизации работают одинаково: Result<Course> и Result<String>.

## Compile-time защита
- Ошибка компиляции при попытке положить String в Result<Course>:
```
error: incompatible types: inference variable T has incompatible bounds
        Result<Course> broken = Result.success("not a course");
                                              ^
    equality constraints: Course
    lower bounds: String
  where T is a type-variable:
    T extends Object declared in method <T>success(T)
1 error
```
- Это означает, что смешать типы нельзя. Ошибка видна до запуска, а не в runtime.

`----------------------------------`

# Задача 7. Generic-метод

## Условие
- Напишите firstOrThrow(List<T>), swap(List<T>, int, int) и indexBy(List<T>, Function<T,K>). Последний метод должен явно обрабатывать конфликт ключей.

## Что сделано
- ListUtils с тремя generic-методами: firstOrThrow, swap, indexBy.
- Main демонстрирует все три, включая пустой список и конфликт ключей.

## Generic-метод
- Generic-метод - метод со своим параметром типа. Пишется перед возвращаемым типом: public static <T> T firstOrThrow(List<T> list).
- При вызове компилятор подставляет конкретный тип. Для List<String> T = String.

## firstOrThrow
- Возвращает первый элемент списка.
- Если список пуст - бросает IllegalArgumentException с сообщением list is empty.
- Обработка пустого случая явная, не через возврат null.

## swap
- Меняет местами элементы по двум индексам.
- Через временную переменную: tmp = get(i), set(i, get(j)), set(j, tmp).
- Работает для любого типа T.

## indexBy
- Строит Map<K, T> из списка.
- Ключ получается функцией keyExtractor: Function<T, K>. Значение - сам элемент.
- Function<T, K> - интерфейс из java.util.function, у него один метод apply(T), возвращает K.
- Запись `c -> c.id()` - короткая форма: принимает c, возвращает c.id().
- При совпадении ключей - исключение IllegalArgumentException с сообщением duplicate key: <ключ>.
- Молчаливая перезапись не допускается: если два элемента дают один ключ, терять данные нельзя.

### Демонстрация
```
=== firstOrThrow ===
first: Anna
on empty: list is empty

=== swap ===
before: [A, B, C]
after swap(0,2): [C, B, A]

=== indexBy ===
map size: 3
id 2 -> Algorithms

=== indexBy with duplicate key ===
on duplicate: duplicate key: 1
```
- firstOrThrow на непустом списке вернул Anna.
- На пустом - исключение, поймано через try-catch, напечатано сообщение.
- swap(0,2) переставил первый и последний элементы.
- indexBy построил Map с тремя парами.
- На дубликате ключа - исключение, второй элемент не перезаписал первый.

`----------------------------------`

# Задача 8. Ограничение типа

## Условие
- Реализуйте max для элементов Comparable. Корректно сформулируйте bound с учётом подтипов. Проверьте на Integer, String и собственном value object.

## Что сделано
- MaxUtils с generic-методом max.
- Score - собственный value object, реализующий Comparable<Score>.
- Main демонстрирует max на Integer, String, Score.

## Что такое Comparable
- Интерфейс Comparable<T> говорит: объекты этого класса можно сравнивать друг с другом.
- Один метод: int compareTo(T other).
- Возвращает: отрицательное - если this меньше other; 0 - если равны; положительное - если this больше.
- Integer, String уже реализуют Comparable.

## Bound
- Generic-метод можно ограничить: принимать только те типы, которые реализуют Comparable.
- Запись: public static <T extends Comparable<T>> T max(List<T> list).
- Читается: T - любой тип, который реализует Comparable<T>.
- Без ограничения компилятор не знает, есть ли у T метод compareTo, и не даст его вызвать.

## Score
- Собственный value object с одним полем points.
- Реализует Comparable<Score>: compareTo сравнивает по points через Integer.compare.
- Значит объекты Score можно сортировать и сравнивать.

### Демонстрация
```
=== max on Integer ===
max: 9

=== max on String ===
max: cherry

=== max on Score ===
max: Score(80)

=== max on empty list ===
on empty: list is empty
```
- max на Integer вернул 9.
- max на String вернул cherry (по алфавиту последнее).
- max на Score вернул Score(80) - наибольший балл.
- На пустом списке - исключение, поймано через try-catch.

`----------------------------------`

# Задача 9. PECS на практике

## Условие
- Создайте методы копирования из producer-списка в consumer-список. Объясните, почему нельзя добавлять конкретный объект в List<? extends Course> и что разрешено читать из List<? super Course>.

## Что сделано
- OnlineCourse - подтип Course для демонстрации наследования.
- CopyUtils с методом copy(List<? extends T> src, List<? super T> dst).
- Main демонстрирует копирование, чтение из producer, запись в consumer.

## Wildcards
- ? - wildcard, «любой тип».
- ? extends T - «T или его подтип».
- ? super T - «T или его родитель»

## PECS
- Producer Extends, Consumer Super.
- Если коллекция отдаёт элементы - ? extends T.
- Если коллекция принимает элементы - ? super T.

## List<? extends T>
- Читать можно как T: каждый элемент точно T или подтип.
- Добавлять нельзя: точный тип неизвестен. Если список на самом деле List<OnlineCourse>, положить обычный Course туда нельзя.

## List<? super T>
- Добавлять T можно: любой T подойдёт в List<T>, List<Object>, List<родитель T>.
- Читать можно только как Object: точный тип может быть Object.

## Метод copy
- public static <T> void copy(List<? extends T> src, List<? super T> dst).
- src - producer (extends), dst - consumer (super).
- Работает для разных комбинаций: List<OnlineCourse> -> List<Course>, List<Course> -> List<Object> и т.д.

### Демонстрация
```
=== copy: producer extends, consumer super ===
dst size: 2
dst[0]: Java Online

=== copy into List<Object> ===
objects size: 2

=== List<? extends Course> — читать можно ===
read from ? extends Course: Java Online

=== List<? super Course> — писать можно ===
added to ? super Course, size: 1

=== List<? super Course> — читать только как Object ===
read from ? super Course: Course
```
- В первом блоке скопировали List<OnlineCourse> в List<Course> — сработало благодаря extends и super.
- Во втором скопировали в List<Object> — тоже сработало.
- В третьем прочитали из List<? extends Course> как Course.
- В четвёртом добавили Course в List<? super Course>.
- В пятом прочитали из List<? super Course> как Object (не как Course).

`----------------------------------`

# Задача 10. Контракт ключа

## Условие
- Проверьте сущности Course в HashSet и как ключи HashMap. Измените поле, не участвующее в равенстве, затем намеренно включите его в hash и воспроизведите проблему.

## Что сделано
- StableKey - ключ, где в hashCode участвует только id. tag можно менять без последствий.
- BrokenKey - ключ, где в hashCode участвует и tag. Изменение tag ломает поиск.
- Main демонстрирует оба случая, а также поведение model.Course в HashSet и HashMap.

## Почему это важно
- HashSet кладёт элемент в корзину по hashCode. Поиск (contains) снова считает hashCode и идёт в нужную корзину, потом сравнивает через equals.
- Если между добавлением и поиском изменить поле, участвующее в hashCode - хеш станет другим. Коллекция пойдёт в другую корзину и не найдёт элемент. Хотя он физически в Set.

## StableKey
- В hashCode участвует только id: Long.hashCode(id).
- tag не входит ни в hashCode, ни в equals.
- Меняем tag - хеш не меняется. contains находит элемент и до, и после.
- Это правильно для ключей: значимые поля не меняются.

## BrokenKey
- В hashCode участвуют id и tag: 31 * Long.hashCode(id) + tag.hashCode().
- tag входит и в equals.
- Меняем tag после добавления - хеш меняется. contains возвращает false.
- Элемент физически в Set (size = 1), но contains его не находит.
- Печать Set показывает элемент - потому что он реально там лежит.
- Это классическая ловушка: менять поля, участвующие в hashCode, после добавления в HashSet или HashMap нельзя.

## Course в HashSet
- Course из model не переопределяет equals и hashCode. Работает Object.equals (сравнение ссылок).
- c1 и c2 с одинаковыми полями - разные объекты. c1.equals(c2) = false.
- Оба попали в Set. size = 2.
- Хотя поля одинаковые, для Set они разные.

## Course как ключ HashMap
- Course не переопределяет equals и hashCode.
- byId.put(a.id(), a) - кладёт по ключу 1.
- byId.put(b.id(), b) - снова ключ 1, перезаписывает.
- map size = 1. Ключи 1 (Long) одинаковые, поэтому второй put заменил значение.

## Почему для Course нельзя провести эксперимент
- Course не переопределяет equals и hashCode. Работает Object.equals (сравнение ссылок) и Object.hashCode (от адреса в памяти).
- Ни одно поле Course не участвует в hashCode. Значит, нет поля, которое можно «включить в hash» - включать нечего.
- Условие требует: изменить поле в hash и воспроизвести проблему. Для Course это невозможно.
- Поэтому сделали свои классы StableKey и BrokenKey: сами задаём, какие поля входят в hashCode, и можем их менять.

### Демонстрация
```
=== StableKey: tag не входит в hashCode ===
contains before change: true
contains after change:  true
size: 1

=== BrokenKey: tag входит в hashCode ===
contains before change: true
contains after change:  false
size: 1
set still has it: [BrokenKey(id=1, tag=after)]

=== Course в HashSet ===
c1.equals(c2): false
set size: 2

=== Course как ключ HashMap ===
map size: 1
id=1 -> Java
```
- StableKey: tag не в hashCode - contains работает и после смены tag.
- BrokenKey: tag в hashCode - contains ломается после смены tag, хотя элемент физически в Set.
- Course в HashSet: equals по ссылке, оба объекта разные, size = 2.
- Course в HashMap: ключ Long одинаковый, второй put перезаписал значение, size = 1.

`----------------------------------`

# Задача 11. Выбор по нагрузке

## Условие
- Для шести сценариев - каталог по ID, отсортированный leaderboard, очередь, уникальные теги с порядком, диапазонный поиск, частый доступ по индексу - выберите интерфейс и реализацию. Укажите сложность главных операций и цену памяти/порядка.

## Что важно при выборе коллекции
- Что нужно делать чаще всего: читать по ключу, по индексу, добавлять/удалять, обходить в порядке.
- Нужен ли порядок: никакой, порядок вставки, сортировка.
- Нужны ли уникальные элементы или дубликаты допустимы.
  - Под каждый набор ответов - своя коллекция.

## Сложности основных операций
- ArrayList: доступ по индексу O(1), вставка в конец O(1), в середину O(n), обход O(n).
- LinkedList: доступ по индексу O(n), вставка в начало/конец O(1), обход O(n).
- HashMap: доступ по ключу O(1), вставка O(1), обход O(n) без порядка.
- LinkedHashMap: то же, но обход в порядке вставки.
- TreeMap: доступ O(log n), обход в отсортированном порядке.
- HashSet: contains O(1), вставка O(1), обход O(n).
- LinkedHashSet: то же, но порядок вставки.
- TreeSet: contains O(log n), обход в отсортированном порядке.
- ArrayDeque: добавление и извлечение в голове/хвосте O(1).

## Цена памяти и порядка
- Порядок вставки (LinkedHashMap, LinkedHashSet) - дополнительная память на связный список.
- Сортировка (TreeMap, TreeSet) - дерево из узлов, O(log n) на операции вместо O(1).
- Массив (ArrayList, ArrayDeque) - компактнее.
- Правило: если порядок не нужен - не плати за него. Бери HashMap/HashSet.

## Шесть сценариев

### 1. Каталог по ID
- Интерфейс: Map<Long, Course>. Реализация: HashMap.
- Доступ по ключу: O(1).
- Память: средняя.
- Порядок не нужен - не платим.

### 2. Отсортированный leaderboard
- Интерфейс: NavigableMap<StudentId, Score> или NavigableSet. Реализация: TreeMap или TreeSet.
- Доступ: O(log n). Обход уже отсортирован.
- Память выше, чем у HashMap.
- Платим за сортировку: O(log n) на операции.

### 3. Очередь
- Интерфейс: Queue<T>. Реализация: ArrayDeque.
- Добавление и извлечение: O(1).
- Память: компактная.
- FIFO - цель.

### 4. Уникальные теги с порядком
- Интерфейс: Set<String>. Реализация: LinkedHashSet.
- Проверка и добавление: O(1).
- Память чуть больше, чем у HashSet (связный список).
- Платим за порядок вставки.

### 5. Диапазонный поиск
- Интерфейс: NavigableMap или NavigableSet. Реализация: TreeMap или TreeSet.
- Поиск диапазона: O(log n) + размер диапазона.
- Память: дерево.
- Сортировка нужна для диапазонов.

### 6. Частый доступ по индексу
- Интерфейс: List<T>. Реализация: ArrayList.
- Доступ по индексу: O(1).
- Память: компактная.
- Порядок сохраняется, индекс стабилен.

`----------------------------------`

# Задача 12. Generic repository

## Условие
- Спроектируйте Repository<ID, T> с save, findById, findAll, deleteById. In-memory реализация не отдаёт наружу изменяемую внутреннюю коллекцию, не принимает null, определяет семантику повторного save.

## Что сделано
- Repository<ID, T> - generic-класс с Map<ID, T> на HashMap.
- Методы save, findById, findAll, deleteById.
- Main демонстрирует save, upsert, копию в findAll, deleteById, проверки null.

## Что такое Repository
- Repository - хранилище объектов. Внешний код говорит «сохрани», «дай по id», «дай все», «удали».
- Как именно хранятся объекты - не важно: в памяти, в файле, в БД.
- В задаче - in-memory реализация на HashMap.

## Generic <ID, T>
- T - тип объекта (Course, Student).
- ID - тип идентификатора (Long, String).
- Компилятор проверяет: нельзя положить Course туда, где ожидается Student. Ошибка компиляции.

## Семантика save
- Метод save работает как upsert: update или insert.
- Если ключа нет - добавляет новый объект.
- Если ключ есть - заменяет значение.
- Один метод для создания и обновления.
- В коде это работает через Map.put - он и добавляет, и перезаписывает.

## findAll возвращает копию
- Метод возвращает new ArrayList<>(storage.values()) - новый список с теми же элементами.
- Клиент может менять этот список (clear, add, remove) - внутреннее хранилище не затрагивается.
- Если бы метод возвращал storage.values() напрямую - это было бы представление над Map. Клиент мог бы очистить репозиторий извне.
- Правило: внутренние коллекции не отдаём наружу. Отдаём копию или неизменяемое представление.

## Проверки null
- save(null id) - IllegalArgumentException с сообщением id is null.
- save(id, null) - IllegalArgumentException с сообщением entity is null.
- findById(null) - IllegalArgumentException.
- deleteById(null) - IllegalArgumentException.
- null в id или объекте - ошибка вызывающего кода, а не нормальное поведение.

### Демонстрация
```
=== save and findById ===
found: Java

=== save with same id (upsert) ===
after upsert: Java Advanced

=== findAll returns copy ===
all size: 2
after client clear, repo size still: 2

=== deleteById ===
findById(2): null
size: 1

=== null checks ===
save null id: id is null
save null entity: entity is null
```
- save и findById: сохранили два курса, нашли первый по id.
- upsert: сохранили с тем же id - значение заменилось на Java Advanced.
- findAll returns copy: клиент очистил список, полученный из findAll. Внутри репозитория данные не тронуты - size всё ещё 2.
- deleteById: удалили id=2. findById(2) вернул null. Size стал 1.
- null checks: save с null id и null entity - оба бросили IllegalArgumentException с понятными сообщениями.