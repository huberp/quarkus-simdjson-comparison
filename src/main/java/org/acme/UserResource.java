package org.acme;

import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec;
import com.github.plokhotnyuk.jsoniter_scala.core.readFromStream;
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("/user")
public class UserResource {

    private static final JsonValueCodec<User> codec = JsonCodecMaker.make(User.class);

    @POST
    @Path("/standard")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String parseStandard(User user) {
        return "Parsed standard: " + user.name;
    }

    @POST
    @Path("/simdjson")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String parseSimdJson(InputStream inputStream) {
        User user = readFromStream(inputStream, codec);
        return "Parsed simdjson: " + user.name;
    }
}