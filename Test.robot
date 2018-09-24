*** Settings ***
Suite Setup       Load Config    ${EXEC_DIR}
Library           DatabaseLibrary
Library           ConfigReader.py
Library           CustomAsserts.py
Library           LoginHelper.py
Library           ExcelReader.py    ${TestDataPath}
Library           rwb.DebugLibrary
Library           Dialogs
Library           DateTime
Library           SudsLibrary
Library           XML
Resource          external.txt

*** Variables ***
${TestDataPath}    datasheets/Doc.xlsx
${RowNum}         %RowNum%

*** Test Cases ***
Create person table
    Keyword from resources
    Log    ${EXEC_DIR}
    Should Be Equal As Strings    5    5
    ${value}    Get Config Value    Section3.var3
    Log    ${value}
    ${rows}    Read Workbook
    Log    ${rows}
    Log    &{rows[${RowNum}]}[FirstName]

Just a test
    ${boolVar}    Set Variable    ${False}
    Log    ${boolVar}
    Run Keyword If    ${boolVar}    Log    It is true
    MyKeyword

Test Logging
    ${var1}    Set Variable    5
    ${var2}    Set Variable    5
    Should Be Equal As Strings    ${var1}    ${var2}
    Should Be Equal As Strings With Logging    ${var1}    ${var2}
    ${date} =    Get Current Date    result_format=datetime
    Should Be Equal As Strings With Logging    ${date}    ${date}
    Run Keyword If    ${True}    Should Be Equal As Strings With Logging    ${var1}    ${var2}

Test SOAP
    ${wsdl}=    set variable    http://www.dneonline.com/calculator.asmx?wsdl
    ${wsdl_operation}=    set variable    Add
    ${msg}=    Set Variable    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/"> \ \ \ <soapenv:Header/> \ \ \ <soapenv:Body> \ \ \ \ \ \ <tem:Add> \ \ \ \ \ \ \ \ \ <tem:intA>1</tem:intA> \ \ \ \ \ \ \ \ \ <tem:intB>2</tem:intB> \ \ \ \ \ \ </tem:Add> \ \ \ </soapenv:Body> </soapenv:Envelope>
    Create Soap Client    ${wsdl}
    # try to create object
    ${obj}    Create Wsdl Object    Add
    Set Wsdl Object Attribute    ${obj}    intA    1
    Set Wsdl Object Attribute    ${obj}    intB    3
    ${raw_msg}=    Create Raw Soap Message    ${msg}
    # using object
    ${result}=    Call Soap Method    ${wsdl_operation}    ${obj}
    # or using raw
    ${result}=    Call Soap Method    ${wsdl_operation}    ${raw_msg}

*** Keywords ***
MyKeyword
    ${var1}    Set Variable    6
    Run Keyword And Continue On Failure    Should Be Equal As Integers    ${var1}    7
    Return From Keyword
    Log    after return will not print

assert values
    ${var1}    Set Variable    6
    ${var2}    Set Variable    6
    Should Be Equal As Strings    ${var1}    ${var2}
