package com.testinium.cucumberplaywright.steps;

import com.microsoft.playwright.Page;
import com.testinium.cucumberplaywright.support.PlaywrightRuntime;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class PlaywrightSteps {

    private Page page() {
        return PlaywrightRuntime.page();
    }

    @Given("I open the Google page")
    public void iOpenTheGooglePage() {
        PlaywrightRuntime.markStep("open-google-page");
        page().navigate("https://www.google.com");
    }

    @Then("I should see Google in the title")
    public void iShouldSeeGoogleInTheTitle() {
        PlaywrightRuntime.markStep("assert-google-title");
        Assert.assertTrue(page().title().contains("Google"));
    }

    @Given("I prepare the login page")
    public void iPrepareTheLoginPage() {
        PlaywrightRuntime.markStep("prepare-login-page");
        page().setContent(String.join("\n",
                "<html>",
                "  <head><title>Login</title></head>",
                "  <body>",
                "    <label>Username <input id=\"username\"></label>",
                "    <label>Password <input id=\"password\" type=\"password\"></label>",
                "    <button id=\"login\">Login</button>",
                "    <p id=\"result\"></p>",
                "    <script>",
                "      document.querySelector('#login').onclick = () => {",
                "        document.querySelector('#result').textContent = 'Login successful';",
                "      };",
                "    </script>",
                "  </body>",
                "</html>"
        ));
    }

    @When("I enter login credentials")
    public void iEnterLoginCredentials() {
        PlaywrightRuntime.markStep("enter-login-credentials");
        page().locator("#username").fill("testinium-user");
        page().locator("#password").fill("secret-password");
    }

    @When("I submit the login form")
    public void iSubmitTheLoginForm() {
        PlaywrightRuntime.markStep("submit-login-form");
        page().locator("#login").click();
    }

    @Then("I should see the login success message")
    public void iShouldSeeTheLoginSuccessMessage() {
        PlaywrightRuntime.markStep("assert-login-success");
        Assert.assertEquals("Login successful", page().locator("#result").textContent());
    }

    @Given("I prepare the todo page")
    public void iPrepareTheTodoPage() {
        PlaywrightRuntime.markStep("prepare-todo-page");
        page().setContent(String.join("\n",
                "<html>",
                "  <head><title>Todo List</title></head>",
                "  <body>",
                "    <input id=\"todo\" placeholder=\"New task\">",
                "    <button id=\"add\">Add</button>",
                "    <ul id=\"list\"></ul>",
                "    <script>",
                "      document.querySelector('#add').onclick = () => {",
                "        const input = document.querySelector('#todo');",
                "        const item = document.createElement('li');",
                "        item.textContent = input.value;",
                "        document.querySelector('#list').appendChild(item);",
                "        input.value = '';",
                "      };",
                "    </script>",
                "  </body>",
                "</html>"
        ));
    }

    @When("I add a todo item")
    public void iAddATodoItem() {
        PlaywrightRuntime.markStep("add-todo-item");
        page().locator("#todo").fill("Check the Playwright trace report");
        page().locator("#add").click();
    }

    @Then("I should see the todo item in the list")
    public void iShouldSeeTheTodoItemInTheList() {
        PlaywrightRuntime.markStep("assert-todo-item");
        Assert.assertEquals(
                "Check the Playwright trace report",
                page().locator("#list li").textContent()
        );
    }

    @Given("I prepare the preferences page")
    public void iPrepareThePreferencesPage() {
        PlaywrightRuntime.markStep("prepare-preferences-page");
        page().setContent(String.join("\n",
                "<html>",
                "  <head><title>Test Preferences</title></head>",
                "  <body>",
                "    <label>Browser",
                "      <select id=\"browser\">",
                "        <option value=\"chromium\">Chromium</option>",
                "        <option value=\"firefox\">Firefox</option>",
                "        <option value=\"webkit\">WebKit</option>",
                "      </select>",
                "    </label>",
                "    <label><input id=\"headless\" type=\"checkbox\"> Run headless</label>",
                "  </body>",
                "</html>"
        ));
    }

    @When("I choose Firefox and enable headless mode")
    public void iChooseFirefoxAndEnableHeadlessMode() {
        PlaywrightRuntime.markStep("set-preferences");
        page().locator("#browser").selectOption("firefox");
        page().locator("#headless").check();
    }

    @Then("I should see the selected preferences")
    public void iShouldSeeTheSelectedPreferences() {
        PlaywrightRuntime.markStep("assert-preferences");
        Assert.assertEquals("firefox", page().locator("#browser").inputValue());
        Assert.assertTrue(page().locator("#headless").isChecked());
    }
}
