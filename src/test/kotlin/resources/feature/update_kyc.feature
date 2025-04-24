Feature: User Update KYC

  Scenario: Successfully update existing KYC information
    Given The global user has existing KYC information
    When I submit updated KYC information
    Then I should receive the updated KYC response