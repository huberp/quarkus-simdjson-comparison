package com.example;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;
import org.simdjson.JsonValue;
import org.simdjson.SimdJsonParser;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimdJsonUserResource {
    
    private static final SimdJsonParser parser = new SimdJsonParser();
    
    @POST
    @Path("/simdjson")
    public User parseUserSimdJson(InputStream inputStream) throws Exception {
        byte[] jsonBytes = inputStream.readAllBytes();
        JsonValue jsonValue = parser.parse(jsonBytes, jsonBytes.length);
        
        // Manual mapping from JsonValue to User
        User user = new User();
        if (jsonValue.isObject()) {
            JsonValue nameValue = jsonValue.get("name");
            if (nameValue != null && nameValue.isString()) {
                user.name = nameValue.asString();
            }
            
            JsonValue ageValue = jsonValue.get("age");
            if (ageValue != null && ageValue.isLong()) {
                user.age = (int) ageValue.asLong();
            }
            
            JsonValue emailValue = jsonValue.get("email");
            if (emailValue != null && emailValue.isString()) {
                user.email = emailValue.asString();
            }
        }
        
        return user;
    }
}