@echo off
IF EXIST bin (
    cd bin
)
for %%f in (*bot*.jar) do (
        java -jar %%f
    )
@echo  *bot*.jar not found.
@pause