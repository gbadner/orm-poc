#!/usr/bin/env bash
set -e

deleteMysql() {

  local CONTAINER=$(docker container ls -a -q -f name=mysql)
  if [[ -n $CONTAINER ]]
  then
    docker container rm -f $CONTAINER
  fi

  local IMAGE=$(docker image ls -a -q -f reference='mysql:*')

  if [[ -n $IMAGE ]]
  then
    docker image rm -f $IMAGE
  fi
}

startMysql() {
  local CONTAINER=$(docker container ls -a -q -f name=mysql)
  if [[ -z $CONTAINER ]]
  then
    docker run --name mysql  -e MYSQL_DATABASE=testdb \
    -e MYSQL_USER=user1 -e MYSQL_PASSWORD=user1 -e MYSQL_ROOT_PASSWORD=admin1234 \
    -p 3306:3306 mysql:5.7 \
    --character-set-server=utf8 --collation-server=utf8_general_ci
  else
    docker container start mysql
  fi
}

run () {
  deleteMysql
  startMysql
}

run
