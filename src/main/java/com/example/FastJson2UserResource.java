package com.example;

import com.alibaba.fastjson2.JSON;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FastJson2UserResource {
    
    @POST
    @Path("/fastjson2")
    public User parseUserFastJson2(InputStream inputStream) throws Exception {
        byte[] jsonBytes = inputStream.readAllBytes();
        return JSON.parseObject(jsonBytes, User.class);
    }
}
