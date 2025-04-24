Feature: Close User Account

  Scenario: Successfully close an active bank account
    Given The global user has an active bank account
    When I send a request to close that account
    Then I should receive confirmation that the account is closed