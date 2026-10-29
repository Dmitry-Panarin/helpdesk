# Helpdesk — система заявок техподдержки

Консольное приложение на Java для учёта заявок в техподдержку. Учебный pet-проект: тренирую ООП, коллекции, исключения и Stream API.

## Возможности
- Создание заявки (заголовок, описание)
- Список всех заявок и фильтр по статусу
- Смена статуса с проверкой допустимых переходов
- Статистика: число заявок по статусам, самая старая открытая заявка

## Статусы и переходы
```
NEW ──► IN_PROGRESS ──► CLOSED
 └──────────────────────►┘
```
Правила переходов живут в самом `enum Status` (`canTransitionTo`). Недопустимый переход выбрасывает проверяемое `InvalidStatusTransitionException`.

## Архитектура
```
ui/ConsoleUi ──► service/TicketService ──► repository/TicketRepository (интерфейс)
                                                  ▲
                                   InMemoryTicketRepository (LinkedHashMap)
```
| Пакет | Что внутри |
|---|---|
| `model` | `Ticket` (валидация, `equals`/`hashCode`), `Status`, `InvalidStatusTransitionException` |
| `repository` | интерфейс `TicketRepository` и реализация в памяти |
| `service` | бизнес-логика, `TicketNotFoundException` (непроверяемое) |
| `ui` | консольное меню |
| `Main` | только собирает зависимости и запускает меню |

Сервис зависит от интерфейса репозитория, поэтому хранилище в памяти можно заменить на базу данных, не трогая сервис и UI.

## Запуск
Нужен JDK 17+ (используются `switch`-выражения).
```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out ru.dmitry_panarin.helpdesk.Main
```

## Планы
- [ ] Unit-тесты на JUnit 5 (переходы статусов, сервис)
- [ ] Сборка через Maven
- [ ] Хранение в PostgreSQL через JDBC
