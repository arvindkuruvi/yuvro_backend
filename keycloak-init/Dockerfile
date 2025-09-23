FROM debian:bullseye-slim

RUN apt-get update && apt-get install -y curl bash jq && rm -rf /var/lib/apt/lists/*

COPY ./init-keycloak.sh /scripts/init-keycloak.sh
RUN chmod +x /scripts/init-keycloak.sh

ENTRYPOINT ["/scripts/init-keycloak.sh"]
