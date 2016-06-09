#!/bin/bash

SWITCH="\e["
NORMAL="${SWITCH}0m"
YELLOW="${SWITCH}33m"
RED="${SWITCH}31m"
GREEN="${SWITCH}32m"
BLUE="${SWITCH}34m"

dir=$(dirname "${BASH_SOURCE[0]}")


user=admin
pass=admin-1
creds=$user:$pass
#person_to_update=$(jq -r '.nome + "-|-" + .cognome' $dir/persona-update.json)
person_to_update=$(jq -r '.email' $dir/persona-update.json)
echo $person_to_update
curl -X PUT -d @$dir/persona-update.json -H "Content-Type: application/json" -H "Accepts: application/json" "http://$creds@localhost:8080/persona/$person_to_update?email=test"
echo curl -X PUT -d @$dir/persona-update.json -H "Content-Type: application/json" -H "Accepts: application/json" "http://$creds@localhost:8080/persona/$person_to_update"
#curl -X PUT -d @$dir/persona-update.json -H "Content-Type: application/json" -H "Accepts: application/json" http://$creds@localhost:8080/persona/30
