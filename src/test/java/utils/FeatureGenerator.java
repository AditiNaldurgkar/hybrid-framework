package utils;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class FeatureGenerator {

    public static void generateFeature() throws Exception {

        List<Map<String, String>> data = ExcelUtils.getTestData("Sheet1");

        FileWriter writer = new FileWriter("src/test/resources/features/saucedemo.feature");

        writer.write("Feature: SauceDemo Flow\n\n");

        // ✅ Scenario 1 — Success cases
        writer.write("  Scenario Outline: Login and checkout\n");
        writer.write("    Given user logs in with \"<username>\" and \"<password>\"\n");
        writer.write("    When user applies low to high price filter\n");
        writer.write("    And user adds first product to cart\n");
        writer.write("    And user navigates to cart\n");
        writer.write("    And user proceeds to checkout\n");
        writer.write("    Then user should reach checkout page\n\n");
        writer.write("    Examples:\n");
        writer.write("    | username | password |\n");

        for (Map<String, String> row : data) {
            if (row.get("expected").equalsIgnoreCase("success")) {
                writer.write("    | " + row.get("username") + " | " + row.get("password") + " |\n");
            }
        }

        writer.write("\n");

        // ✅ Scenario 2 — Failure cases
        writer.write("  Scenario Outline: Login should fail\n");
        writer.write("    Given user logs in with \"<username>\" and \"<password>\"\n");
        writer.write("    Then user should see login error\n\n");
        writer.write("    Examples:\n");
        writer.write("    | username | password |\n");

        for (Map<String, String> row : data) {
            if (row.get("expected").equalsIgnoreCase("failure")) {
                writer.write("    | " + row.get("username") + " | " + row.get("password") + " |\n");
            }
        }

        writer.close();
        System.out.println("✅ Feature file generated successfully");
    }
}