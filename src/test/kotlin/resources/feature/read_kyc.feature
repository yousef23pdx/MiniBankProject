Feature: User Read KYC

  Scenario: Successfully retrieve KYC information
    Given The global user has submitted KYC information
    When I request the KYC information for the global user
    Then I should receive the correct KYC details