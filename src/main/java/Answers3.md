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

## Демонстрация
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

## Демонстрация
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