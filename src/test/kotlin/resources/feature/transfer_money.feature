Feature: Money Transfer

  Scenario: User can transfer money between accounts
    Given A user has two active accounts with balances
    When I transfer 100.00 from the first account to the second account
    Then I should receive confirmation of successful transfer with updated balance