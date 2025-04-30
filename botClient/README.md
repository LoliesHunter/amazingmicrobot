# Telegram Bot Client

Клиент Telegram-бота, который отправляет сообщения в микросервисы и экспортирует XLSX-файлы с данными.

## 🚀 Описание
- Принимает все текстовые сообщения от пользователей и пересылает их в Message Saver Service (`POST /messages`).
- По команде `/export` от админа (ID в списке `admins`) запрашивает XLSX из Message Processor Service (`GET /export`) и отправляет его в чат.
- Использует Micronaut HTTP Client под капотом.

## 📋 Предварительные требования
- Java 21
- Gradle
- Docker & Docker Hub
- Telegram Bot Token и Username

## 🛠 Сборка и запуск локально
```bash
cd botClient
./gradlew clean shadowJar
java -jar build/libs/app.jar
```

## 🐳 Docker
```bash
docker build -t lolieshunter/bot-client:latest .
docker push lolieshunter/bot-client:latest
```

## ☸️ Kubernetes
```bash
kubectl apply -f k8s/bot-client-service.yaml
kubectl apply -f k8s/bot-client-deployment.yaml
```
Сервис будет работать в кластере и подключаться к:
- Message Saver: `message-saver-service:8081`
- Message Processor: `message-processor-service:8082`

## ⚙️ Конфигурация (env)
- `TELEGRAM_BOT_USERNAME=qnapsybot_bot`
- `TELEGRAM_BOT_TOKEN=8199399033:AAHyVb5uDWTrIZ0ePFIBLvf_dgfDn88lDE0`
- `MESSAGE_SAVER_URL=http://message-saver-service:8081`
- `MESSAGE_PROCESSOR_URL=http://message-processor-service:8082`

## 📄 Использование
1. Запустите бота.
2. Отправьте сообщение — оно сохранится и попадёт в Kafka.
3. Админ (ID в `admins`) отправляет `/export` — бот пришлёт Excel-файл.
