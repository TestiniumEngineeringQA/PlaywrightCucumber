Feature: Playwright cases

  Scenario: Open Google
    Given I open the Google page
    Then I should see Google in the title

  Scenario: Complete login form
    Given I prepare the login page
    When I enter login credentials
    When I submit the login form
    Then I should see the login success message

  Scenario: Add a todo item
    Given I prepare the todo page
    When I add a todo item
    Then I should see the todo item in the list

  Scenario: Select test preferences
    Given I prepare the preferences page
    When I choose Firefox and enable headless mode
    Then I should see the selected preferences
