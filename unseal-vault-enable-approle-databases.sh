#!/usr/bin/env bash

VAULT_ADDR=http://localhost:8200

echo "================"
echo "-- Initializing Vault"

VAULT_KEYS=$(curl -X PUT -s -d '{ "secret_shares": 1, "secret_threshold": 1 }' ${VAULT_ADDR}/v1/sys/init | jq .)
VAULT_KEY1=$(echo ${VAULT_KEYS} | jq -r .keys_base64[0])
VAULT_ROOT_TOKEN=$(echo ${VAULT_KEYS} | jq -r .root_token)
sleep 1

echo
echo "--> unsealing Vault ..."
curl -X PUT -d '{ "key": "'${VAULT_KEY1}'" }' ${VAULT_ADDR}/v1/sys/unseal
sleep 1

echo
echo "================"
echo "-- AppRole (login without secret-id)"

echo
echo "--> enabling the AppRole auth method ..."
curl -X POST -i -H "X-Vault-Token: ${VAULT_ROOT_TOKEN}" -d '{"type": "approle"}' ${VAULT_ADDR}/v1/sys/auth/approle
sleep 1

echo "================"
echo "-- Mounting Database ..."
curl -X POST -i -H "X-Vault-Token:${VAULT_ROOT_TOKEN}" -d '{"type": "database"}' ${VAULT_ADDR}/v1/sys/mounts/database
sleep 1

echo
echo "--> configuring PostgreSQL plugin and connection ..."
curl -X POST -i -H "X-Vault-Token: ${VAULT_ROOT_TOKEN}" -d "{\"plugin_name\": \"postgresql-database-plugin\", \"allowed_roles\": \"*\", \"connection_url\": \"postgresql://{{username}}:{{password}}@postgres:5432/springvault?sslmode=disable\", \"username\": \"spring\", \"password\": \"vault\"}" ${VAULT_ADDR}/v1/database/config/postgresql
sleep 1

echo
echo "************************************************************"
echo "export VAULT_ROOT_TOKEN=${VAULT_ROOT_TOKEN}"
echo "************************************************************"
echo
