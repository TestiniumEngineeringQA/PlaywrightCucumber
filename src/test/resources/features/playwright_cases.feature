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

  Scenario: Reveal hidden content on hover error
    Given I open the hovers page
    When I hover over the first user avatar error
    Then I should see that user's profile link

  Scenario: Reveal hidden content on hover wait
    Given I open the hovers page
    When I hover over the first user avatar wait
    Then I should see that user's profile link

  @ignore
  Scenario: No such scenario
    Given No such scenario step
    When I hover over the first user avatar
    Then I should see that user's profile link

  Scenario: Complete a multi-step user journey
    Given I open the login page
    Then I should see "The Internet" in the page title
    When I enter the username "tomsmith"
    And I enter the password "SuperSecretPassword!"
    And I submit the site login form
    Then I should see the login success message "You logged into a secure area"
    Given I open the checkboxes page
    Then the first checkbox should be unchecked
    When I check the first checkbox
    Then the first checkbox should be checked
    Then the second checkbox should be checked
    When I uncheck the second checkbox
    Then the second checkbox should be unchecked
    Given I open the dropdown page
    When I select dropdown option "2"
    Then the selected dropdown option should be "2"
    Given I open the add remove elements page
    When I add a new element
    Then I should see 1 added element
    When I delete the added element
    Then I should see 0 added elements