Feature: User Registration

  Scenario: Successful user registration
    Given I have a valid user registration payload
    When I send a POST request to "/users/v1/register"
    Then I should receive a 200 response