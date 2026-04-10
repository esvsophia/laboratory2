# 🏯 Sorcery Mission Analyzer v2.0 

![Java](https://img.shields.io/badge/Java-25-orange)
![Maven](https://img.shields.io/badge/build-Maven-C71A36)
![Patterns](https://img.shields.io/badge/patterns-Factory%20%7C%20Strategy%20%7C%20Decorator-green)

Продвинутая система анализа отчетов о магических инцидентах. Вторая итерация проекта сфокусирована на **гибкой архитектуре** и **динамическом расширении данных**, позволяя обрабатывать новые форматы протоколов без изменения ядра системы.

## ✨ Ключевые особенности
* **Архитектурная гибкость:** Использование паттернов проектирования для устранения "хрупкости" кода.
* **Динамические поля:** Автоматическая обработка любых новых атрибутов в файлах (ущерб, погода, психологический статус) через механизм `extraFields`.
* **Поддержка 5 форматов:**
    * Стандартные: `.json`, `.xml`.
    * Секционные: `.txt` (INI-style).
    * Структурные: `.yaml` (чувствителен к отступам).
    * Протокольные: `.a5` (собственный формат Колледжа на базе пайпов `|`).
* **Рекурсивный вывод:** Умная система формирования отчетов, визуализирующая вложенные списки и словари любой глубины.

## 🛠 Технологии и Паттерны
* **Java 25** (использование `Map.of()`, `instanceof` patterns).
* **Jackson** — парсинг JSON/XML и автоматический маппинг через `@JsonAnySetter`.
* **Паттерны проектирования:**
    * **Strategy:** Инкапсуляция алгоритмов чтения разных форматов.
    * **Simple Factory:** Централизованное создание парсеров в `ParserFactory`.
    * **Decorator:** Наслоение дополнительной информации на базовый отчет без изменения логики `SummBase`.

---

## 📁 Структура проекта
```text
├── src/main/java/org/example/
│   ├── model/           # Доменная модель (Mission, Curse)
│   ├── parser/          # Стратегии парсинга и Фабрика
│   ├── output/          # Система отчетов (Декораторы)
│   ├── GUI.java         # Графический интерфейс (Swing)
│   └── Main.java        # Точка входа
├── Данные о миссиях/    # Примеры отчетов во всех форматах
├── pom.xml              # Конфигурация Maven
└── README.md
```

---

## 🚀 Установка и запуск

1. **Сборка проекта:**
   ```bash
   mvn clean install
   ```

2. **Создание исполняемого архива (Fat JAR):**
   ```bash
   mvn clean compile assembly:single
   ```

3. **Запуск:**
   ```bash
   java -jar target/lab2-mission-analyzer-jar-with-dependencies.jar
   ```

---

## 📄 Примеры новых форматов данных 

### 1. YAML (Mission A2)
```yaml
missionId: M-2024-028
curse:
  name: Проклятие стеклянной галереи
  threatLevel: SPECIAL_GRADE
sorcerers:
  - name: Годжо Сатору
    rank: SPECIAL
```

### 2. A5 Protocol (Mission A5)
```text
MISSION_CREATED|M-A5-99|2026-04-10|Киото
SORCERER_ASSIGNED|name=Нанами Кенто|rank=GRADE_1
TECHNIQUE_USED|name=Пропорция|type=RATIO|damage=800000
CIVILIAN_IMPACT|evacuated=120|injured=5
```

---

## 👩‍💻 Автор
**София Егорова** (@esvsophia)  
Студентка группы Б24-902

## 📜 Лицензия
Проект распространяется под лицензией **Apache License 2.0**.
