#!/bin/sh -e

CERT_NAME="${1:-server}"
CERT_SIZE="${2:-2048}"
CERT_DAYS="${3:-3650}"
DEST_DIR="${4:-./}"

echo "Create directory ${DEST_DIR}"
mkdir -p "${DEST_DIR}"

echo "Generating server keystore ${CERT_NAME}.p12 with size: ${CERT_SIZE} days: ${CERT_DAYS}"
keytool -genkeypair -alias ${CERT_NAME} -keyalg RSA -keysize ${CERT_SIZE} -storetype PKCS12 -keystore ${DEST_DIR}${CERT_NAME}.p12 -validity ${CERT_DAYS}

echo "Generating server keystore ${CERT_NAME}.jks with size: ${CERT_SIZE} days: ${CERT_DAYS}"
keytool -genkeypair -alias ${CERT_NAME} -keyalg RSA -keysize ${CERT_SIZE} -keystore ${DEST_DIR}${CERT_NAME}.jks -validity ${CERT_DAYS}

echo "Import keystore ${CERT_NAME}.jks into ${CERT_NAME}.p12"
keytool -importkeystore -srckeystore ${DEST_DIR}${CERT_NAME}.jks -destkeystore ${DEST_DIR}${CERT_NAME}.p12 -deststoretype pkcs12