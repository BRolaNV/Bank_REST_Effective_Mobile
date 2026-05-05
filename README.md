# Система управления банковскими картами

Backend-приложение на Java (Spring Boot) для управления банковскими картами.

## Функциональность

**Администратор:**
- Создаёт, блокирует, активирует, удаляет карты
- Видит все карты

**Пользователь:**
- Просматривает свои карты (фильтрация + пагинация)
- Запрашивает блокировку карты
- Делает переводы между своими картами
- Смотрит баланс

## Технологии

Java 17, Spring Boot 3.2, Spring Security, Spring Data JPA,
PostgreSQL, Liquibase, Docker, JWT, Swagger (OpenAPI)

## Предварительные требования

- Docker
- Java 17

## Запуск

1. Запустить базу данных:
```bash
   docker-compose up -d
```

2. Запустить приложение через IDE — класс `BankRESTApplication`  
   или через Maven:
```bash
   mvn spring-boot:run
```

3. Открыть Swagger UI:  
   http://localhost:8080/swagger-ui/index.html

## Учётные данные по умолчанию

| |          |
|---|----------|
| Логин | `admin`  |
| Пароль | `admin123` |

## Обзор API

| Метод | Эндпоинт | Описание |
|---|---|---|
| POST | `/api/auth/register` | Регистрация пользователя |
| POST | `/api/auth/login` | Вход, получение JWT |
| GET | `/api/user/cards` | Список своих карт |
| GET | `/api/user/cards/{id}/balance` | Баланс карты |
| PUT | `/api/user/cards/{id}/request-block` | Запрос блокировки |
| POST | `/api/user/transfers` | Перевод между картами |
| GET | `/api/admin/cards` | Все карты (admin) |
| POST | `/api/admin/cards` | Создать карту (admin) |
| PUT | `/api/admin/cards/{id}/activate` | Активировать карту (admin) |
| PUT | `/api/admin/cards/{id}/block` | Заблокировать карту (admin) |
| DELETE | `/api/admin/cards/{id}` | Удалить карту (admin) |

## Документация

Полная OpenAPI спецификация: `docs/openapi.yaml`
