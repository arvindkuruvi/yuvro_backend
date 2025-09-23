#!/bin/bash

# Config
KEYCLOAK_URL="http://${KEYCLOAK_HOST}:${KEYCLOAK_PORT}"
KEYCLOAK_ADMIN_USER=admin
KEYCLOAK_ADMIN_PASS=admin
REALM_NAME=yuvro-realm
CLIENT_ID=yuvro-client
CLIENT_SECRET=yuvrosecret123
USER_NAME=yuvro
USER_PASS=yuvro
ROLE_NAME=YUVRO_ADMIN

KEYCLOAK_HOST=${KEYCLOAK_HOST:-keycloak}
KEYCLOAK_PORT=${KEYCLOAK_PORT:-8080}
KEYCLOAK_HEALTH_URL="http://${KEYCLOAK_HOST}:${KEYCLOAK_PORT}/health/ready"

echo "⏳ Waiting for Keycloak to be ready at $KEYCLOAK_HEALTH_URL..."

MAX_RETRIES=50
RETRIES=0

while [ "$RETRIES" -lt "$MAX_RETRIES" ]; do
  HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" "$KEYCLOAK_HEALTH_URL" 2>/dev/null)

  if [ "$HTTP_CODE" != "000" ]; then
    echo "✅ Keycloak is up and ready!"
    break
  else
    echo "$(date) 🔁 Keycloak not ready yet (HTTP $HTTP_CODE)..."
    RETRIES=$((RETRIES + 1))
    sleep 5
  fi
done

if [ "$RETRIES" -eq "$MAX_RETRIES" ]; then
  echo "❌ Timed out waiting for Keycloak."
  exit 1
fi


# === STEP 1: Get admin access token ===
echo "🔐 Getting admin access token..."
ADMIN_TOKEN=$(curl -s -X POST "$KEYCLOAK_URL/realms/master/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=$KEYCLOAK_ADMIN_USER" \
  -d "password=$KEYCLOAK_ADMIN_PASS" \
  -d "grant_type=password" \
  -d "client_id=admin-cli" | jq -r .access_token)

if [ -z "$ADMIN_TOKEN" ] || [ "$ADMIN
_TOKEN" == "null" ]; then
  echo "❌ Failed to get admin access token"
  exit 1
fi

echo "🧱 ADMIN_TOKEN '$ADMIN_TOKEN'"


echo "🧱 Creating realm '$REALM_NAME'..."
curl --fail -X POST "$KEYCLOAK_URL/admin/realms" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "realm": "'"$REALM_NAME"'",
    "enabled": true
  }'

while true; do
  EXISTS=$(curl -s -H "Authorization: Bearer $ADMIN_TOKEN" "$KEYCLOAK_URL/admin/realms/$REALM_NAME")
  if echo "$EXISTS" | grep -q '"realm"'; then
    echo "✅ Realm is now available"
    break
  fi
  echo "🔁 Waiting for realm..."
  sleep 2
done


echo "🔑 Creating client '$CLIENT_ID'..."
curl -s -X POST "$KEYCLOAK_URL/admin/realms/$REALM_NAME/clients" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "'"$CLIENT_ID"'",
    "secret": "'"$CLIENT_SECRET"'",
    "protocol": "openid-connect",
    "name": "client for yuvro app",
    "description": "Client for Yuvro Application",
    "publicClient": false,
    "authorizationServicesEnabled": false,
    "serviceAccountsEnabled": true,
    "implicitFlowEnabled": false,
    "directAccessGrantsEnabled": true,
    "standardFlowEnabled": true,
    "frontchannelLogout": true,
    "attributes": {
        "saml_idp_initiated_sso_url_name": "",
        "oauth2.device.authorization.grant.enabled": false,
        "oidc.ciba.grant.enabled": false,
        "post.logout.redirect.uris": "'"$KEYCLOAK_URL/*"'"
    },
    "alwaysDisplayInConsole": false,
    "rootUrl": "'"$KEYCLOAK_URL"'",
    "baseUrl": "'"$KEYCLOAK_URL"'",
    "webOrigins": [
        "*"
    ]
  }'

echo "🔍 Fetching client details for '$CLIENT_ID'..."
curl -s "$KEYCLOAK_URL/admin/realms/$REALM_NAME/clients?clientId=$CLIENT_ID" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq '.[0]'

# Get internal client ID
CLIENT_UUID=$(curl -s "$KEYCLOAK_URL/admin/realms/$REALM_NAME/clients?clientId=$CLIENT_ID" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq -r '.[0].id')


echo "🔧 Adding protocol mapper to client..."
curl -s -X POST "$KEYCLOAK_URL/admin/realms/$REALM_NAME/clients/$CLIENT_UUID/protocol-mappers/models" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "protocol": "openid-connect",
    "protocolMapper": "oidc-usermodel-realm-role-mapper",
    "name": "yuvro-mapper",
    "config": {
        "usermodel.realmRoleMapping.rolePrefix": "",
        "multivalued": "true",
        "claim.name": "roles",
        "jsonType.label": "String",
        "id.token.claim": "true",
        "access.token.claim": "true",
        "lightweight.claim": "false",
        "userinfo.token.claim": "true",
        "introspection.token.claim": "true"
    }
}'



echo "🛡️ Creating realm role 'YUVRO_ADMIN'..."
curl -s -X POST "$KEYCLOAK_URL/admin/realms/$REALM_NAME/roles" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "YUVRO_ADMIN",
    "description": "Admin role for Yuvro application",
    "attributes": {}
  }'

echo "🛡️ Creating realm role 'STUDENT'..."
curl -s -X POST "$KEYCLOAK_URL/admin/realms/$REALM_NAME/roles" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "STUDENT",
    "description": "STUDENT role for Yuvro application",
    "attributes": {}
  }'

echo "🛡️ Creating realm role 'FACULTY'..."
curl -s -X POST "$KEYCLOAK_URL/admin/realms/$REALM_NAME/roles" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "FACULTY",
    "description": "FACULTY role for Yuvro application",
    "attributes": {}
  }'


echo "🔍 Fetching 'realm-management' client UUID..."
REALM_MGMT_UUID=$(curl -s "$KEYCLOAK_URL/admin/realms/$REALM_NAME/clients?clientId=realm-management" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq -r '.[0].id')

echo "👤 Fetching service account user ID for client '$CLIENT_ID'..."
SERVICE_ACCOUNT_USER_ID=$(curl -s "$KEYCLOAK_URL/admin/realms/$REALM_NAME/clients/$CLIENT_UUID/service-account-user" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq -r '.id')

echo "📥 Fetching all realm-management roles..."
REALM_MGMT_ROLES=$(curl -s "$KEYCLOAK_URL/admin/realms/$REALM_NAME/clients/$REALM_MGMT_UUID/roles" \
  -H "Authorization: Bearer $ADMIN_TOKEN")

echo "🔗 Assigning realm-management roles to service account..."
curl -s -X POST "$KEYCLOAK_URL/admin/realms/$REALM_NAME/users/$SERVICE_ACCOUNT_USER_ID/role-mappings/clients/$REALM_MGMT_UUID" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d "$REALM_MGMT_ROLES"

echo "✅ All 'realm-management' roles assigned to service account for '$CLIENT_ID'."

echo "🎉 All done. User token should contain roles."

