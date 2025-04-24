Feature: Multiple Account Creation

  Scenario: User with valid token can create multiple accounts
    When I create 2 bank accounts for the global user
    Then I should receive 2 successful account creation responses