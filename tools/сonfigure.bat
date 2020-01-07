@echo off
IF NOT EXIST bot-config.properties (
    (
        echo notification_time = 00:00:00
        echo path_to_days = days
        echo path_to_daily = daily
    ) > bot-config.properties
)

IF NOT EXIST vk-config.properties (
    (
        echo access_token = /*you access token*/
        echo group_id = /*you group ID*/
    ) > vk-config.properties
)

IF NOT EXIST days (
    mkdir days
    echo monday;1-1;10:00;10:30 > days\monday.txt
    echo tuesday;1-1;10:00;10:30 > days\tuesday.txt
    echo wednesday;1-1;10:00;10:30 > days\wednesday.txt
    echo thursday;1-1;10:00;10:30 > days\thursday.txt
    echo friday;1-1;10:00;10:30 > days\friday.txt
    echo saturday;1-1;10:00;10:30 > days\saturday.txt
    echo sunday;1-1;10:00;10:30 > days\sunday.txt
)
IF NOT EXIST daily (
    mkdir daily
    (
       echo Доброе утро! Твоя сводка на сегодня:
       echo Сегодня будет count уроков с start_time до end_time
       echo TodaySchedule
       echo Удачного дня!
    )> daily\daily_text.txt
)