*** Settings ***
Suite Setup       Load Config    ${EXEC_DIR}
Library           DatabaseLibrary
Library           ConfigReader.py

*** Test Cases ***
Create person table
    [Setup]
    Log    ${EXEC_DIR}
    Should Be Equal As Strings    5    5
    ${value}    Get Config Value    Section3.var3
    Log    ${value}
