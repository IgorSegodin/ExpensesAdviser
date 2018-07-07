Heroku Configuration

1) expenses-adviser-backend
Add env variables:
    GRADLE_TASK = buildBackend
    START_SCRIPT = expenses-adviser-backend/start.sh

2) expenses-adviser-telegram-bot
Add env variables:
    GRADLE_TASK = buildBot
    START_SCRIPT = expenses-adviser-telegram-bot/start.sh
    BACKEND_URL = https://expenses-adviser-backend.herokuapp.com
    TOKEN = <telegram bot token>