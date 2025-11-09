package com.example;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;
import org.eclipse.simdjson.JsonParser;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimdJsonUserResource {
    
    @POST
    @Path("/simdjson")
    public User parseUserSimdJson(InputStream inputStream) throws Exception {
        JsonParser parser = new JsonParser();
        User user = parser.parse(inputStream, User.class);
        return user;
    }
}