# Message Saver Service

Это микросервис, который получает сообщения от Telegram-бота по HTTP, сохраняет их в PostgreSQL и публикует в Kafka для дальнейшей обработки.

## 🚀 Описание
- Принимает `POST /messages` с телом JSON `{ "username": "<user>", "text": "<message>" }`.
- Сохраняет запись в PostgreSQL (`messages` db).
- Публикует то же сообщение в Kafka-топик `messages-topic`.
- Предоставляет эндпоинт `GET /actuator/health` для проверки состояния.

## 📋 Предварительные требования
- Java 21
- Gradle
- Docker & доступ к Docker Hub
- PostgreSQL (локально или в кластере)
- Kafka (локально или в кластере)

## 🛠 Сборка и запуск локально
```bash
cd messageSaverService
./gradlew clean shadowJar
java -jar build/libs/app.jar
```
По умолчанию приложение слушает порт **8081**.

## 🐳 Docker
```bash
docker build -t lolieshunter/messagesaver-service:latest .
docker push lolieshunter/messagesaver-service:latest
```

## ☸️ Kubernetes
```bash
kubectl apply -f k8s/message-saver-service.yaml
kubectl apply -f k8s/message-saver-deployment.yaml
```
Сервис будет доступен на `NodePort 30081` (порт 8081 внутри кластера).

## 📡 Конфигурация (env)
- `DATASOURCES_DEFAULT_URL=jdbc:postgresql://postgres:5432/messages`
- `DATASOURCES_DEFAULT_USERNAME=postgres`
- `DATASOURCES_DEFAULT_PASSWORD=password`
- `KAFKA_BOOTSTRAP_SERVERS=kafka:9092`

## 📄 Эндпоинты
| Метод | Путь             | Описание                            |
|-------|------------------|-------------------------------------|
| POST  | `/messages`      | Сохранить сообщение                 |
| GET   | `/actuator/health` | Проверить статус микросервиса      |

---
*License: MIT*