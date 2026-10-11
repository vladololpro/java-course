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