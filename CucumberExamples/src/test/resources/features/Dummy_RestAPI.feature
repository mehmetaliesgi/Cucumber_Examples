Feature: Test CRUD methods in Sample Employee REST API Testing
  Scenario: Add Employee Record
    Given User "BASE_URL_DUMMY" base URL'ini kullanır
    When Path parametreleri için "create" kullanılır
    And Post request icin "Mehmet Ali ESGI", "25000", "30" bilgileri ile request body olusturulur
    And POST request gonderilir ve testleri yapmak icin response degerini kaydeder
    Then response'da status degerinin "success" olduğu kontrol edilir
    Then response attributelerini degerlerinin "Mehmet Ali ESGI","25000","30" olduğunu kontrol et