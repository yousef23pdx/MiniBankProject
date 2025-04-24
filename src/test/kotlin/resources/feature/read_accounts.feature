Feature: Read List of Accounts

  Scenario: Successfully retrieve a list of accounts
    Given The global user has at least 1 account
    When I request the list of accounts
    Then I should receive a list containing accounts