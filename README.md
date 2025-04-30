# РАСЧУДЕСНЫЙ МИКРОБОТ

---

## 📁 Структура репозитория
```
/ (root)
├── messageSaverService/            # Микросервис для сохранения сообщений
├── messageProcessorService/        # Микросервис обработки и экспорта сообщений
├── botClient/                      # Telegram Bot Client
├── k8s/                            # Kubernetes манифесты для всех компонентов
├── docker-compose.yml              # Локальный стек через Docker Compose
├── deploy.sh                       # Скрипт для сборки и пуша Docker-образов
└── README.md                       # Общий README (этот файл)
```

---

## 🏗️ Сборка и деплой

### 1. Локальный запуск через Docker Compose
```bash
# Запустить все компоненты (Postgres, ArangoDB, Zookeeper, Kafka, микросервисы и бот):
docker-compose up -d
```

### 2. Сборка Docker-образов и пуш в Docker Hub
```bash
# Запуск скрипта сборки и пуша
./deploy.sh
```

### 3. Деплой в Kubernetes
```bash
# Применить все манифесты из папки k8s
kubectl apply -f k8s/
```
После этого в кластере поднимутся Postgres, ArangoDB, Zookeeper, Kafka, три микросервиса и бот.

---

## 📚 Дополнительные материалы
- Папка `k8s/` содержит все Deployment, Service и ConfigMap для продакшен-деплоя в Kubernetes.  
- Скрипт `deploy.sh` автоматизирует сборку fat-jar и пуш образов.  

