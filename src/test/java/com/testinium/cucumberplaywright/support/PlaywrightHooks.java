package com.testinium.cucumberplaywright.support;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

import java.io.IOException;

public class PlaywrightHooks {

    @BeforeAll
    public static void beforeAll() {
        PlaywrightRuntime.launchBrowser();
    }

    @AfterAll
    public static void afterAll() {
        PlaywrightRuntime.shutdownBrowser();
    }

    @Before
    public void beforeScenario(Scenario scenario) throws IOException {
        PlaywrightRuntime.startScenario(scenario.getName());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        byte[] screenshot = PlaywrightRuntime.captureCurrentStep();
        scenario.attach(screenshot, "image/png", "step-screenshot");
    }

    @After
    public void afterScenario() {
        PlaywrightRuntime.finishScenario();
    }
}
