Feature: End to end tests for ToolsQA's Book Store API
  Background: User generates token for Authorisation
    Given I am "USERNAME" an authorized user in the "BASE_URL" URL

  Scenario: Authorized user is able to Add and Remove a book
    Given A list of books are available in the "BASE_URL"
    When I "USER_ID" add a book to my reading list int the "BASE_URL"
    Then The book is added
    When I remove a book from my reading list in the "BASE_URL"
    Then The book is removed in the "BASE_URL"