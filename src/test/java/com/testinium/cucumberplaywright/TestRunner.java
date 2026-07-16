package com.testinium.cucumberplaywright;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {
                "com.testinium.cucumberplaywright.steps",
                "com.testinium.cucumberplaywright.support"
        },
        plugin = {
                "pretty",
                "summary",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json"
        },
        monochrome = true,
        publish = false,
        tags = "not @ignore"
)
public class TestRunner {
}
