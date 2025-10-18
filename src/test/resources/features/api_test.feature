@API
Feature: API uptime check for calculator site
  Scenario: Site returns HTTP 200 on GET /
    Given I have the calculator base URL
    When I send a GET request to the base URL
    Then the response status should be 200
