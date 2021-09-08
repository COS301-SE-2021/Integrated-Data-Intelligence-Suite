#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER emerge WITH PASSWORD 'emerge0000';
    CREATE DATABASE UserServiceDatabase OWNER emerge;
    GRANT ALL PRIVILEGES ON DATABASE UserServiceDatabase TO docker;
EOSQL