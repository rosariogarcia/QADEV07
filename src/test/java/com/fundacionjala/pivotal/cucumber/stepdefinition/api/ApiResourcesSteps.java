package com.fundacionjala.pivotal.cucumber.stepdefinition.api;

import java.util.Map;

import com.jayway.restassured.response.Response;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.fundacionjala.pivotal.api.Mapper.addResponse;
import static com.fundacionjala.pivotal.api.Mapper.mapEndpoint;
import static com.fundacionjala.pivotal.api.RequestManager.*;
import static org.junit.Assert.assertEquals;

public class ApiResourcesSteps {

    private Response response;

    private Map<String, Object> parameters;

    private String endPoint;

    @Given("^I have the (.*) endpoint$")
    public void iHaveTheEndpoint(String endPoint) {
        this.endPoint = mapEndpoint(endPoint);
    }

    @Given("^I have the next parameters:$")
    public void iHaveTheNextParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @When("^I send a GET request to (.*) endpoint$")
    public void iSendAGETRequest(String endPoint) {
        response = getRequest(endPoint);
    }

    @Given("^I send a POST request to (.*)$")
    public void iSendAPOSTRequestToEndpoint(String endPoint, Map<String, Object> values) {
        System.out.println("End point post request: " + endPoint);
        System.out.println("endpoint post mapped: " + mapEndpoint(endPoint));
        response = postRequest(mapEndpoint(endPoint), values);
        System.out.println("post response: " + response.prettyPrint());
    }

    @When("^I send a PUT request to (.*?)$")
    public void iSendAPUTRequest(String endPoint, Map<String, Object> parameters) {
        response = putRequest(mapEndpoint(endPoint), parameters);
    }

    @When("^I send a DELETE request$")
    public void iSendADELETERequest() {
        response = deleteRequest(endPoint);
    }

    @And("^stored as (.*)")
    public void storedAs(String key) {
        addResponse(key, response);
    }

    @Then("^I expect the status code (\\d+)$")
    public void iExpectStatusCode(int statusCodeExpected) {
        assertEquals(statusCodeExpected, response.statusCode());
    }

    public Response getResponse() {
        return response;
    }

    public String getEndPoint() {
        return endPoint;
    }
}