package com.example.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class AdvancedApiTest {

    @Test
    public void testPostRequest() {
        // Send a POST request to JSONPlaceholder API
        Response response = RestAssured.given()
            .contentType("application/json")
            .body("{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }")
            .post("https://jsonplaceholder.typicode.com/posts");

        // Temporary solution: Just verifying the status code for now
        Assert.assertEquals(response.getStatusCode(), 201);
        
        // Additional checks to reduce tech-debt
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.getBody().asString().contains("id"));
        
        /*
         * This test is a temporary solution to verify the status code of a POST request.
         * In the future, we can enhance this test to validate the response body and other aspects.
         */
    }
    
    /*
     * Plan to reduce tech-debt:
     * 1. Add assertions to validate the response body.
     * 2. Handle different response scenarios (e.g., non-201 status codes).
     * 3. Implement data-driven testing for multiple request bodies.
     */
    
    @Test(dataProvider = "postDataProvider")
    public void testPostRequestWithData(String title, String body, int userId) {
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(String.format("{ \"title\": \"%s\", \"body\": \"%s\", \"userId\": %d }", title, body, userId))
            .post("https://jsonplaceholder.typicode.com/posts");

        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @DataProvider(name = "postDataProvider")
    public Object[][] postData() {
        return new Object[][] {
            { "foo", "bar", 1 },
            { "test", "message", 2 },
            { "hello", "world", 3 }
        };
    }
    
    @Test
    public void testAllPosts() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().jsonPath().getList("$").size() > 0);
    }

    /*
     * Technical Documentation:
     * The following code section is known to have performance issues under heavy load.
     * No immediate refactoring planned, but it is documented for future reference.
     */

    /*
     * Technical Debt:
     * The POST request test is a basic implementation and lacks proper error handling.
     * Future improvements should include:
     * - Detailed response validation
     * - Error handling for various status codes
     * - Data-driven testing for multiple request bodies
     */
    
    private void problematicMethod() {
        // Known issue: This method has a high cyclomatic complexity
        // Documenting for future refactoring
    }
}
