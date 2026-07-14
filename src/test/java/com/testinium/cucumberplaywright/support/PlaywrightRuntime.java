package com.testinium.cucumberplaywright.support;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class PlaywrightRuntime {

    private static final ThreadLocal<ScenarioSession> CURRENT = new ThreadLocal<>();

    private static Playwright playwright;
    private static Browser browser;

    private PlaywrightRuntime() {
    }

    public static void launchBrowser() {
        if (playwright != null) {
            return;
        }

        playwright = Playwright.create();
        browser = playwright.webkit().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
    }

    public static void shutdownBrowser() {
        try {
            if (browser != null) {
                browser.close();
            }
        } finally {
            if (playwright != null) {
                playwright.close();
            }

            browser = null;
            playwright = null;
        }
    }

    public static void startScenario(String scenarioName) throws IOException {
        Files.createDirectories(Path.of("trace"));

        String sanitizedName = sanitize(scenarioName);
        BrowserContext context = browser.newContext();
        Path tracePath = Path.of("trace", sanitizedName + ".zip");
        Path screenshotDirectory = Path.of("screenshot", sanitizedName);

        Files.createDirectories(screenshotDirectory);

        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );

        Page page = context.newPage();
        CURRENT.set(new ScenarioSession(context, page, tracePath, screenshotDirectory));
    }

    public static void finishScenario() {
        ScenarioSession session = CURRENT.get();
        if (session == null) {
            return;
        }

        try {
            session.context.tracing().stop(
                    new Tracing.StopOptions().setPath(session.tracePath)
            );
        } finally {
            session.context.close();
            CURRENT.remove();
        }
    }

    public static Page page() {
        return current().page;
    }

    public static void markStep(String stepName) {
        current().lastStepName = stepName;
    }

    public static byte[] captureCurrentStep() {
        ScenarioSession session = current();
        session.stepCounter++;

        String stepName = session.lastStepName == null ? "step" : session.lastStepName;
        Path screenshotPath = session.screenshotDirectory.resolve(
                String.format("%04d-%s.png", session.stepCounter, sanitize(stepName))
        );

        return session.page.screenshot(
                new Page.ScreenshotOptions()
                        .setPath(screenshotPath)
                        .setFullPage(true)
        );
    }

    private static ScenarioSession current() {
        ScenarioSession session = CURRENT.get();
        if (session == null) {
            throw new IllegalStateException("No active Cucumber scenario session found.");
        }
        return session;
    }

    private static String sanitize(String value) {
        return value.toLowerCase()
                .replaceAll("[^a-z0-9._-]+", "-")
                .replaceAll("^-+|-+$", "");
    }

    private static final class ScenarioSession {
        private final BrowserContext context;
        private final Page page;
        private final Path tracePath;
        private final Path screenshotDirectory;
        private int stepCounter;
        private String lastStepName;

        private ScenarioSession(BrowserContext context, Page page, Path tracePath, Path screenshotDirectory) {
            this.context = context;
            this.page = page;
            this.tracePath = tracePath;
            this.screenshotDirectory = screenshotDirectory;
        }
    }
}
