package com.anaplan.test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static org.junit.Assert.*;

public class StepDefinitions {
    private String token;
    private static Response response;
    private static String jsonString;

    @Given("I am logged in")
    public void i_am_logged_in() {
        RestAssured.baseURI = "https://accounts.spotify.com";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization","Basic YTMyYjY4ZDUxZTdjNGZlZjk3YWRhMzRkOWQxZjJmYjM6OWI5N2NjNmMyYTEwNDEyYmFjMTNlNDc0MmQ0ZGFjMmE=");
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.param("grant_type","client_credentials");
        response = request
                .post("/api/token");

        jsonString = response.asString();
        System.out.println("Token "+ jsonString);
        token = JsonPath.from(jsonString).get("access_token");
        System.out.println("Token "+ token);
    }

    @When("I ask for artist {string}")
    public void i_ask_for_artist(String id) {

        RestAssured.baseURI = "https://api.spotify.com/v1";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization","Bearer " + token);
        response = request
                .get("/artists/" + id);
        Assert.assertEquals(200,response.statusCode());
        jsonString = response.asString();

    }
    @Then("I should get the name as {string}")
    public void i_should_get_the_name_as(String artistName) {
        String name = JsonPath.from(jsonString).get("name");
        System.out.println("Name "+ name);
        Assert.assertEquals(artistName, name);
    }


}
