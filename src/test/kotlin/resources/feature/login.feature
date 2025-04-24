Feature: User Login

  Scenario: User can login and receive JWT token
    Given A registered user with username "TestUser" and password "password123"
    When I send a POST request to "/auth/login" for login
    Then I should receive a 200 response with a JWT token