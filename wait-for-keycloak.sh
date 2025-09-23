#!/bin/sh

KEYCLOAK_HOST=${KEYCLOAK_HOST:-keycloak}
KEYCLOAK_PORT=${KEYCLOAK_PORT:-8080}
KEYCLOAK_URL="http://${KEYCLOAK_HOST}:${KEYCLOAK_PORT}/health/ready"

echo "⏳ Waiting for Keycloak to be ready at $KEYCLOAK_URL..."

MAX_RETRIES=30
RETRIES=0

while [ "$RETRIES" -lt "$MAX_RETRIES" ]; do
  HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" "$KEYCLOAK_URL" 2>/dev/null)

  if [ "$HTTP_CODE" != "000" ]; then
    echo "✅ Keycloak is up!"
    break
  else
    echo "$(date) 🔁 Keycloak not ready yet (HTTP $HTTP_CODE)..."
    RETRIES=$((RETRIES + 1))
    sleep 2
  fi
done

if [ "$RETRIES" -eq "$MAX_RETRIES" ]; then
  echo "❌ Timed out waiting for Keycloak."
  exit 1
fi

echo "🚀 Starting Spring Boot app..."
exec java -jar yuvro.jar
