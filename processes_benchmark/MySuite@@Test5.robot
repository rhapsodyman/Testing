*** Settings ***

*** Variables ***

*** Test Cases ***
Just a test5
    ${str}    Set Variable    Hello
    : FOR    ${index}    IN RANGE    10
    \    Sleep    20ms
    \    ${str}    Set Variable    ${str} ${index}
    \    Log    ${str}

*** Keywords ***
