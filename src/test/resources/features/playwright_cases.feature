Feature: Playwright cases

  @openGoogle
  Scenario: Open Google
    Given I open the Google page
    Then I should see Google in the title

  @complete-login-form
  Scenario: Complete login form
    Given I prepare the login page
    When I enter login credentials
    When I submit the login form
    Then I should see the login success message

  @addATodoItem
  Scenario: Add a todo item
    Given I prepare the todo page
    When I add a todo item
    Then I should see the todo item in the list

  @select-test-preferences
  Scenario: Select test preferences
    Given I prepare the preferences page
    When I choose Firefox and enable headless mode
    Then I should see the selected preferences

  Scenario: Handle a JavaScript confirm dialog on a live site
    Given I open the JavaScript alerts page
    When I trigger the confirm dialog and accept it
    Then I should see the confirm result message

  Scenario: Interact with content opened in a new tab
    Given I open the multiple windows page
    When I click the link that opens a new tab
    Then I should see the new tab content

  Scenario: Type inside an iframe on a live site
    Given I open the rich text editor page
    When I type text inside the editor iframe
    Then I should see my text inside the editor iframe

  Scenario: Wait for dynamically loaded content
    Given I open the dynamic loading page
    When I start the dynamic loading process
    Then I should see the dynamically loaded text

  Scenario: Reveal hidden content on hover
    Given I open the hovers page
    When I hover over the first user avatar
    Then I should see that user's profile link