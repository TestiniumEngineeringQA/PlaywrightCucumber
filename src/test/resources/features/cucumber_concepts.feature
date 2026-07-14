Feature: Cucumber concepts

  Background:
    Given I start with a clean browser session

  @smoke @login
  Scenario Outline: Login attempts with different credentials
    Given I prepare the validated login page
    When I enter username "<username>" and password "<password>"
    And I submit the login form
    Then I should see the login result "<result>"

    Examples: Valid and invalid credentials
      | username        | password         | result              |
      | testinium-user  | secret-password  | Login successful    |
      | testinium-user  | wrong-password   | Invalid credentials |
      | wrong-user      | secret-password  | Invalid credentials |
      |                 |                  | Invalid credentials |

  @regression @data-table
  Scenario: Add multiple todo items using a data table
    Given I prepare the todo page
    When I add the following todo items:
      | Write cucumber scenarios       |
      | Review pull request            |
      | Update Playwright dependencies |
    Then I should see 3 todo items in the list

  @regression @doc-string
  Scenario: Save multi-line notes using a doc string
    Given I prepare the notes page
    When I write the following notes:
      """
      Regression run summary:
      - Login scenarios: passed
      - Todo scenarios: passed
      - Notes captured successfully
      """
    Then I should see the saved notes:
      """
      Regression run summary:
      - Login scenarios: passed
      - Todo scenarios: passed
      - Notes captured successfully
      """
