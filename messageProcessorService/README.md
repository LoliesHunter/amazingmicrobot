# Message Processor Service

Микросервис, который потребляет сообщения из Kafka, сохраняет их в ArangoDB и предоставляет экспорт в Excel.

## 🚀 Описание
- Подписывается на Kafka-топик `messages-topic`.
- Сохраняет каждое сообщение в коллекцию `messages` в ArangoDB, создавая базу/коллекцию при отсутствии.
- Предоставляет эндпоинт `GET /export` для скачивания XLSX-файла с последними сообщениями.
- Защищает экспорт с помощью заголовка `Authorization: Bearer <token>`.

## 📋 Предварительные требования
- Java 21
- Gradle
- Docker & доступ к Docker Hub
- Kafka
- ArangoDB

## 🛠 Сборка и запуск локально
```bash
cd messageProcessorService
./gradlew clean shadowJar
java -jar build/libs/app.jar
```
По умолчанию приложение слушает порт **8082**.

## 🐳 Docker
```bash
docker build -t lolieshunter/messageprocessor-service:latest .
docker push lolieshunter/messageprocessor-service:latest
```

## ☸️ Kubernetes
```bash
kubectl apply -f k8s/message-processor-service.yaml
kubectl apply -f k8s/message-processor-deployment.yaml
```
Сервис доступен на `NodePort 30082` (порт 8082).

## 📡 Конфигурация (env)
- `KAFKA_BOOTSTRAP_SERVERS=kafka:9092`
- `ARANGODB_HOST=arangodb`
- `ARANGODB_PORT=8529`
- `ARANGODB_USER=root`
- `ARANGODB_PASSWORD=password`
- `ARANGODB_DATABASE=messages`
- `EXPORT_AUTH_TOKEN=secretToken1234`

## 📄 Эндпоинты
| Метод | Путь      | Параметры                  | Описание                                      |
|-------|-----------|----------------------------|-----------------------------------------------|
| GET   | `/export` | `Authorization: Bearer ...` | Скачать XLSX с сообщениями (только для админов) |


