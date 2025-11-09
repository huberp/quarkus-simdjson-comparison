package com.example;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;
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
        User user = parser.parse(jsonBytes, jsonBytes.length, User.class);
        return user;
    }
}