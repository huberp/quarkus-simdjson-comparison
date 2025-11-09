package com.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.lessThan;

public class LoadTest {
    
    @Test
    public void testStandardEndpoint() {
        RestAssured.given()
            .baseUri("http://localhost:8080")
            .body(new User() /* ... */)
            .contentType("application/json")
            .post("/user/standard")
            .then()
            .statusCode(200)
            .time(lessThan(5000L));
    }
    
    @Test
    public void testSimdJsonEndpoint() {
        RestAssured.given()
            .baseUri("http://localhost:8080")
            .body(new User() /* ... */)
            .contentType("application/json")
            .post("/user/simdjson")
            .then()
            .statusCode(200)
            .time(lessThan(5000L));
    }
}