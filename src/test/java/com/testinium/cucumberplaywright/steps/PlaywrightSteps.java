package com.testinium.cucumberplaywright.steps;

import com.microsoft.playwright.Page;
import com.testinium.cucumberplaywright.support.PlaywrightRuntime;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

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

    // ---- Live site scenarios (the-internet.herokuapp.com) ----

    private Page newTabPage;

    @Given("I open the JavaScript alerts page")
    public void iOpenTheJavaScriptAlertsPage() {
        PlaywrightRuntime.markStep("open-js-alerts-page");
        page().navigate("https://the-internet.herokuapp.com/javascript_alerts");
    }

    @When("I trigger the confirm dialog and accept it")
    public void iTriggerTheConfirmDialogAndAcceptIt() {
        PlaywrightRuntime.markStep("trigger-confirm-dialog");
        page().onDialog(dialog -> dialog.accept());
        page().locator("text=Click for JS Confirm").click();
    }

    @Then("I should see the confirm result message")
    public void iShouldSeeTheConfirmResultMessage() {
        PlaywrightRuntime.markStep("assert-confirm-result");
        Assert.assertEquals("You clicked: Ok", page().locator("#result").textContent());
    }

    @Given("I open the multiple windows page")
    public void iOpenTheMultipleWindowsPage() {
        PlaywrightRuntime.markStep("open-multiple-windows-page");
        page().navigate("https://the-internet.herokuapp.com/windows");
    }

    @When("I click the link that opens a new tab")
    public void iClickTheLinkThatOpensANewTab() {
        PlaywrightRuntime.markStep("open-new-tab");
        newTabPage = page().context().waitForPage(() ->
                page().locator("text=Click Here").click()
        );
        newTabPage.waitForLoadState();
    }

    @Then("I should see the new tab content")
    public void iShouldSeeTheNewTabContent() {
        PlaywrightRuntime.markStep("assert-new-tab-content");
        Assert.assertEquals("New Window", newTabPage.locator("h3").textContent());
    }

    @Given("I open the rich text editor page")
    public void iOpenTheRichTextEditorPage() {
        PlaywrightRuntime.markStep("open-iframe-editor-page");
        page().navigate("https://the-internet.herokuapp.com/iframe");
    }

    @When("I type text inside the editor iframe")
    public void iTypeTextInsideTheEditorIframe() {
        PlaywrightRuntime.markStep("type-inside-iframe");
        page().frameLocator("#mce_0_ifr").locator("#tinymce")
                .fill("Testinium Playwright suite");
    }

    @Then("I should see my text inside the editor iframe")
    public void iShouldSeeMyTextInsideTheEditorIframe() {
        PlaywrightRuntime.markStep("assert-iframe-text");
        Assert.assertEquals(
                "Testinium Playwright suite",
                page().frameLocator("#mce_0_ifr").locator("#tinymce").textContent()
        );
    }

    @Given("I open the dynamic loading page")
    public void iOpenTheDynamicLoadingPage() {
        PlaywrightRuntime.markStep("open-dynamic-loading-page");
        page().navigate("https://the-internet.herokuapp.com/dynamic_loading/2");
    }

    @When("I start the dynamic loading process")
    public void iStartTheDynamicLoadingProcess() {
        PlaywrightRuntime.markStep("start-dynamic-loading");
        page().locator("#start button").click();
    }

    @Then("I should see the dynamically loaded text")
    public void iShouldSeeTheDynamicallyLoadedText() {
        PlaywrightRuntime.markStep("assert-dynamic-loading-text");
        page().locator("#finish h4").waitFor();
        Assert.assertEquals("Hello World!", page().locator("#finish h4").textContent());
    }

    @Given("I open the hovers page")
    public void iOpenTheHoversPage() {
        PlaywrightRuntime.markStep("open-hovers-page");
        page().navigate("https://the-internet.herokuapp.com/hovers");
    }

    @When("I hover over the first user avatar")
    public void iHoverOverTheFirstUserAvatar() {
        PlaywrightRuntime.markStep("hover-first-avatar");
        page().locator(".figure").first().hover();
    }

    @Then("I should see that user's profile link")
    public void iShouldSeeThatUsersProfileLink() {
        PlaywrightRuntime.markStep("assert-profile-link");
        Assert.assertEquals(
                "View profile",
                page().locator(".figure").first().locator("a").textContent()
        );
    }

    // ---- Background ----

    @Given("I start with a clean browser session")
    public void iStartWithACleanBrowserSession() {
        PlaywrightRuntime.markStep("clean-session");
        page().navigate("about:blank");
    }

    // ---- Scenario Outline / Examples (parameterized login) ----

    @Given("I prepare the validated login page")
    public void iPrepareTheValidatedLoginPage() {
        PlaywrightRuntime.markStep("prepare-validated-login-page");
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
                "        const u = document.querySelector('#username').value;",
                "        const p = document.querySelector('#password').value;",
                "        const ok = (u === 'testinium-user' && p === 'secret-password');",
                "        document.querySelector('#result').textContent = ok ? 'Login successful' : 'Invalid credentials';",
                "      };",
                "    </script>",
                "  </body>",
                "</html>"
        ));
    }

    @When("I enter username {string} and password {string}")
    public void iEnterUsernameAndPassword(String username, String password) {
        PlaywrightRuntime.markStep("enter-parameterized-credentials");
        page().locator("#username").fill(username);
        page().locator("#password").fill(password);
    }

    // Note: "I submit the login form" is already defined in PlaywrightSteps.java
    // and is reused here as-is - Cucumber matches step text across all step classes.

    @Then("I should see the login result {string}")
    public void iShouldSeeTheLoginResult(String expectedResult) {
        PlaywrightRuntime.markStep("assert-parameterized-login-result");
        Assert.assertEquals(expectedResult, page().locator("#result").textContent());
    }

    // ---- Data Table (bulk todo items) ----
    // Reuses "I prepare the todo page" from PlaywrightSteps.java.

    @When("I add the following todo items:")
    public void iAddTheFollowingTodoItems(DataTable table) {
        PlaywrightRuntime.markStep("add-todo-items-datatable");
        List<String> items = table.asList();
        for (String item : items) {
            page().locator("#todo").fill(item);
            page().locator("#add").click();
        }
    }

    @Then("I should see {int} todo items in the list")
    public void iShouldSeeNTodoItemsInTheList(int expectedCount) {
        PlaywrightRuntime.markStep("assert-todo-item-count");
        Assert.assertEquals(expectedCount, page().locator("#list li").count());
    }

    // ---- Doc String (multi-line notes) ----

    @Given("I prepare the notes page")
    public void iPrepareTheNotesPage() {
        PlaywrightRuntime.markStep("prepare-notes-page");
        page().setContent(String.join("\n",
                "<html>",
                "  <head><title>Notes</title></head>",
                "  <body>",
                "    <textarea id=\"notes\" rows=\"6\" cols=\"40\"></textarea>",
                "    <button id=\"save\">Save</button>",
                "    <pre id=\"saved-notes\"></pre>",
                "    <script>",
                "      document.querySelector('#save').onclick = () => {",
                "        document.querySelector('#saved-notes').textContent = document.querySelector('#notes').value;",
                "      };",
                "    </script>",
                "  </body>",
                "</html>"
        ));
    }

    @When("I write the following notes:")
    public void iWriteTheFollowingNotes(String docString) {
        PlaywrightRuntime.markStep("write-notes-docstring");
        page().locator("#notes").fill(docString);
        page().locator("#save").click();
    }

    @Then("I should see the saved notes:")
    public void iShouldSeeTheSavedNotes(String docString) {
        PlaywrightRuntime.markStep("assert-saved-notes");
        Assert.assertEquals(docString, page().locator("#saved-notes").textContent());
    }
}
