@UNIT
Feature: Calculator unit operations
  Scenario Outline: Perform basic math
    Given two numbers <a> and <b>
    When I <op> them
    Then the result should be <expected>
    Examples:
      | a  | b  | op       | expected |
      | 2  | 3  | add      | 5        |
      | 10 | 4  | subtract | 6        |
      | 7  | 6  | multiply | 42       |
      | 10 | 5  | divide   | 2        |
