Feature: User Create KYC

  Scenario: Successfully submit KYC information
    Given The global user is authenticated
    When I submit valid KYC information
    Then I should receive a successful KYC response