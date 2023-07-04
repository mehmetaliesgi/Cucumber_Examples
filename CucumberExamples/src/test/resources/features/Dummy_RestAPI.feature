Feature: Test CRUD methods in Sample Employee REST API Testing
  Scenario: Add Employee Record
    Given User "BASE_URL_DUMMY" base URL'ini kullanır
    When Path parametreleri için "create" kullanılır
    And Request body "Mehmet Ali ESGI","25000","30" bilgileri ile olusturulur
    And POST request gonderilir ve testleri yapmak icin response degerini kaydeder
    Then response'da status degerinin "success" olduğu kontrol edilir
    Then response attributelerini degerlerinin "Mehmet Ali ESGI","25000","30" olduğunu kontrol et

  Scenario: Update Employee Record
    Given User "BASE_URL_DUMMY" base URL'ini kullanır
    When Path parametreleri için "update/5662" kullanılır
    And Request body "Mehmet Ali ESGI","35000","20" bilgileri ile olusturulur
    And  PUT request gonderilir ve testleri yapmak icin response degeri kaydedilir
    Then response'da status degerinin "success" olduğu kontrol edilir
