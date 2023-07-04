Feature: Test CRUD methods in Sample Employee REST API Testing
  Background:
    Given User "BASE_URL_DUMMY" base URL'ini kullanır

  Scenario: Add Employee Record
    When Path parametreleri için "create" kullanılır
    And Request body "Mehmet Ali ESGI","25000","30" bilgileri ile olusturulur
    And POST request gonderilir ve testleri yapmak icin response degerini kaydeder
    Then response'da status degerinin "success" olduğu kontrol edilir
    Then response attributelerini degerlerinin "Mehmet Ali ESGI","25000","30" olduğunu kontrol et

  Scenario: Update Employee Record
    When Path parametreleri için "update/5662" kullanılır
    And Request body "Mehmet Ali ESGI","35000","20" bilgileri ile olusturulur
    And  PUT request gonderilir ve testleri yapmak icin response degeri kaydedilir
    Then response'da status degerinin "success" olduğu kontrol edilir

  Scenario: Get Employee Record
    When Path parametreleri için "employee/4283" kullanılır
    And GET request gonderilir ve testleri yapmak icin response degeri kaydedilir
    And response'da status degerinin "success" olduğu kontrol edilir
    And response attributelerini degerlerinin "Mehmet Ali ESGI","35000","20" olduğunu kontrol et

  Scenario: Delete Employee Record
    And Path parametreleri için "delete/4283" kullanılır
    When DELETE request gonderilir
    And response'da status degerinin "success" olduğu kontrol edilir
    And response'da message degerinin "Successfully! Record has been deleted" oldugu kontrol edilir
