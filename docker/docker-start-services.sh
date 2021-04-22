#!/bin/bash
set -e

source compose-utils.sh

# -f docker-compose.kafka.yaml
ADDITIONAL_COMPOSE_QUEUE_ARGS=$(additionalComposeQueueArgs) || exit $?

# -f docker-compose.postgres.yml
ADDITIONAL_COMPOSE_ARGS=$(additionalComposeArgs) || exit $?

docker-compose -f docker-compose.yml $ADDITIONAL_COMPOSE_ARGS $ADDITIONAL_COMPOSE_QUEUE_ARGS up  -d $@
