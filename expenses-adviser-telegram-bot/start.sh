#!/usr/bin/env bash
java -Dserver.port=$PORT -Dexpenses-adviser.backend.url=$BACKEND_URL -Dtelegram.token=$TOKEN -Ddatabase.url=$DATABASE_URL -jar build/expenses-adviser-telegram-bot.jar