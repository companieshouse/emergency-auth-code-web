#!/bin/bash
#
# Start script for emergency-auth-code-web

PORT=8080

exec java -jar -Dserver.port="${PORT}" "emergency-auth-code-web.jar"
