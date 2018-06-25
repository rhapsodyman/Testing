*** Settings ***
Suite Setup       Load Config    ${EXEC_DIR}
Library           DatabaseLibrary
Library           ConfigReader.py
Library           ExcelReader.py    ${TestDataPath}
Library           rwb.DebugLibrary
Library           Dialogs

*** Variables ***
${TestDataPath}    datasheets/Doc.xlsx
${RowNum}         %RowNum%

*** Test Cases ***
Create person table
    [Setup]
    Log    ${EXEC_DIR}
    Should Be Equal As Strings    5    5
    ${value}    Get Config Value    Section3.var3
    Log    ${value}
    ${rows}    Read Workbook
    Log    ${rows}
    Log    &{rows[${RowNum}]}[FirstName]

Just a test
    [Setup]
    ${boolVar}    Set Variable    ${False}
    Log    ${boolVar}
    Pause Execution
    Run Keyword If    ${boolVar}    Log    It is true
    ${var1}    Set Variable    6
    ${var2}    Set Variable    7
    Run Keyword If    ${var1} > ${var2}    Log    Its greater
