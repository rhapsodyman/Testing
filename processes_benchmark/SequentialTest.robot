*** Settings ***

*** Variables ***

*** Test Cases ***
Just a test[1]
    Template keyword

Just a test[2]
    Template keyword

Just a test[3]
    Template keyword

Just a test[4]
    Template keyword

Just a test[5]
    Template keyword

Just a test[6]
    Template keyword

Just a test[7]
    Template keyword

Just a test[8]
    Template keyword

Just a test[9]
    Template keyword

Just a test[10]
    Template keyword

*** Keywords ***
Template keyword
    ${str}    Set Variable    Hello
    : FOR    ${index}    IN RANGE    10
    \    Sleep    20ms
    \    ${str}    Set Variable    ${str} ${index}
    \    Log    ${str}
