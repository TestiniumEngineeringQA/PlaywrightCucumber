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

        // 1. COMMAND_PARAMETER
        String demoParam = System.getProperty("commandParameter");
        // 2. ENVIRONMENT_PARAMETER
        String denemeParam = System.getenv("environmentParameter");

        // 1. COMMAND_PARAMETER
        String demoParam2 = System.getProperty("commandParameter2");
        // 2. ENVIRONMENT_PARAMETER
        String denemeParam2 = System.getenv("environmentParameter2");

        System.out.println(">>> [COMMAND_PARAMETER] demo: " + demoParam);
        System.out.println(">>> [ENVIRONMENT_PARAMETER] deneme: " + denemeParam);

        System.out.println(">>> [COMMAND_PARAMETER] demo2: " + demoParam2);
        System.out.println(">>> [ENVIRONMENT_PARAMETER] deneme2: " + denemeParam2);

        String scenarioIDValue = System.getenv("SCENARIO_ID");
        String executionIDValue = System.getenv("EXECUTION_ID");
        String testResultIDValue = System.getenv("TEST_RESULT_ID");

        System.out.println(">>> [SCENARIO_ID] : " + scenarioIDValue);
        System.out.println(">>> [EXECUTION_ID] : " + executionIDValue);
        System.out.println(">>> [TEST_RESULT_ID] : " + testResultIDValue);
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
