#!/bin/bash

echo "Welcome to TicTacToe starter."
echo "There are three application modes: Local, RestServer and Web"
echo "You can quit the script with [q]."
read -p "Enter app mode [ L | R | W ] and press [ENTER]: " appMode
echo $appMode

function restart() {
  chmod +x oneClickStart.sh
  ./oneClickStart.sh
}

function local() {
  cd game
  gradle run

}
function rest() {
  cd game
  gradle run --args="http:/localhost:8080/"
}

function installNpm() {
  cd frontend
  npm install
  cd ..
}

function web() {
  read -p "Have you run the command 'npm install' in /frontend/? [y|n]: " npmInstall
  case $npmInstall in
    y|Y) echo "Good" ;;
    n|N) installNpm ;;
    q|Q) ;;
    *) web ;;
  esac
  cd game
  start gradle run --args="serverOnly" &
  sleep 1
  cd ..
  cd frontend
  start npm start
}

case $appMode in
  l|L) local ;;
  r|R) rest ;;
  w|W) web ;;
  q|Q) ;;
  *) restart ;;
esac
