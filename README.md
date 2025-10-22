# PointCatcher — JSF Web Application

**PointCatcher** — это интерактивное web-приложение на базе **Jakarta Faces (JSF)**,  
выполняющее геометрическую проверку попадания точки в заданную область на координатной плоскости.  
Результаты сохраняются в базе данных и отображаются в виде таблицы и графика.  
Проект реализует типовой стек корпоративных приложений: **JSF + JDBC + Gradle/Ant**.

---

## Основная идея

Приложение предоставляет web-интерфейс, где пользователь выбирает радиус области `R`  
и задаёт координаты точки `(x, y)` вручную или кликом по графику.  
Серверная часть определяет, попадает ли точка в допустимую область (объединение треугольника, прямоугольника и сектора круга).  
Результаты вычислений сохраняются в базе данных, после чего отображаются на странице в таблице и графике.

---

## Архитектура и структура проекта

```
jsf-webapp-pointcatcher/
├── build.gradle
├── build.xml                    
├── params.properties             
├── src/
│   └── main/
│       ├── java/itmo/web/demo1/
│       │   ├── beans/
│       │   │   ├── AreaCalculator.java
│       │   │   ├── FormBean.java
│       │   │   ├── TableBean.java
│       │   │   ├── PointStats.java
│       │   │   └── MBeanRegistry.java
│       │   ├── database/
│       │   │   ├── DataBaseManager.java
│       │   │   └── models/Point.java
│       │   └── utils/PointHitManager.java
│       ├── webapp/
│       │   ├── faces/
│       │   │   ├── index.xhtml
│       │   │   └── main.xhtml
│       │   ├── resources/
│       │   │   ├── css/
│       │   │   └── js/
│       │   └── WEB-INF/
│       │       ├── web.xml
│       │       └── faces-config.xml
└── settings.gradle
```

**Основные компоненты:**
- `AreaCalculator`, `PointHitManager` — бизнес-логика вычисления попаданий.
- `DataBaseManager` — слой доступа к БД (JDBC + PostgreSQL).
- `FormBean`, `TableBean` — JSF Managed Beans для формы и таблицы.
- `index.xhtml`, `main.xhtml` — интерфейс пользователя (JSF Facelets).
- `main.js`, `index.js` — интерактивная логика: обработка кликов, валидация, динамическая перерисовка графика.
- `PointStats`, `MBeanRegistry` — JMX-мониторинг и статистика попаданий.

---

## Функциональные возможности

- Проверка попадания точки `(x, y)` в сложную область (треугольник, прямоугольник, сектор круга);
- Ввод координат вручную и через клик по графику;
- Отображение результатов на SVG-графике и в таблице;
- Автоматическая валидация входных данных;
- Хранение истории попаданий в PostgreSQL;
- Подсчёт статистики попаданий через JMX (встроенные MBeans);
- Совместимость с Tomcat 10+ (Jakarta EE 9);
- Сборка через Gradle или Ant.

---

## ⚙️ Технологический стек

**Языки и платформы**  
![Java](https://img.shields.io/badge/Java_17-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![JakartaEE](https://img.shields.io/badge/Jakarta_EE-ED8B00?style=for-the-badge&logo=jakartaee&logoColor=white)

**Фреймворки и библиотеки**  
![JSF](https://img.shields.io/badge/Jakarta_Faces_(JSF)_3.0-5382A1?style=for-the-badge&logo=java&logoColor=white)
![PrimeFaces](https://img.shields.io/badge/PrimeFaces-1E90FF?style=for-the-badge&logo=primefaces&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

**Сервер и инфраструктура**  
![Tomcat](https://img.shields.io/badge/Apache_Tomcat_10+-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

**Frontend**  
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![jQuery](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)

**Сборка и автоматизация**  
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Apache Ant](https://img.shields.io/badge/Apache_Ant-A81C7D?style=for-the-badge&logo=apache&logoColor=white)

**Мониторинг и управление**  
![JMX](https://img.shields.io/badge/JMX-CB2E6D?style=for-the-badge&logo=java&logoColor=white)
![MBeans](https://img.shields.io/badge/Managed_Beans-007396?style=for-the-badge&logo=oracle&logoColor=white)


---

## Конфигурация базы данных

По умолчанию приложение подключается к PostgreSQL:

```
URL:      jdbc:postgresql://localhost:5433/postgres
USER:     postgres
PASSWORD: password
```

Эти параметры можно изменить в классе `DataBaseManager`  
или вынести в переменные окружения при деплое.

---

## Сборка и запуск

### Gradle
```bash
# Клонировать репозиторий
git clone https://github.com/e345ee/jsf-webapp-pointcatcher.git
cd jsf-webapp-pointcatcher

# Собрать WAR-файл
./gradlew clean build

# Результат будет в build/libs/server.war
```

### Ant
```bash
# Выполнить цели Ant
ant compile     # компиляция исходников
ant build       # упаковка в WAR
ant clean       # очистка
```

---

## Развёртывание

1. Убедитесь, что PostgreSQL запущен и доступен по указанному URL.  
2. Скопируйте скомпилированный `server.war` в директорию `webapps/` вашего Tomcat.  
3. Запустите сервер (`catalina.sh run` или `startup.bat`).  
4. Откройте в браузере:
   ```
   http://localhost:8080/server/faces/index.xhtml
   ```

---

## Автор

**Садовой Григорий**  
Software Engineer  
[Telegram](https://t.me/e345ee) • [VK](https://vk.com/kobievportfievleze) • [Email](mailto:gsad1030@gmail.com)

---

© 2025 PointCatcher — JSF-based geometric validation web app.
