#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

echo "[1/4] 启动 MySQL 容器..."
docker compose up -d mysql

echo "[2/4] 等待 MySQL 就绪..."
for i in {1..30}; do
  if docker compose exec -T mysql mysqladmin ping -h 127.0.0.1 -uroot -proot --silent >/dev/null 2>&1; then
    echo "MySQL 已就绪"
    break
  fi
  if [ "$i" -eq 30 ]; then
    echo "MySQL 启动超时，请执行: docker compose logs mysql"
    exit 1
  fi
  sleep 2
done

echo "[3/4] 启动后端与前端..."
docker compose up -d backend frontend

echo "[4/4] 当前容器状态："
docker compose ps

echo "\n访问地址："
echo "- 前端: http://localhost:5173"
echo "- 后端: http://localhost:8080/api"
echo "\n查看日志：docker compose logs -f backend frontend"
