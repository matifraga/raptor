package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.SignupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by luis on 1/31/17.
 */

@Path("signup")
@Component
public class SignupController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserDTOBuilder.class);
    @Autowired
    UserService us;

    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response signup(final SignupDTO signup) {
        if (!isValidSignup(signup))
            return Response.status(Response.Status.BAD_REQUEST).build();

        User user = us.register(signup.getUsername(),
                signup.getPassword(), signup.getEmail(),
                signup.getFirstName(),signup.getLastName());

        if (user == null)
            return Response.status(Response.Status.CONFLICT).build();

        return Response.status(Response.Status.CREATED).build();
    }


    private boolean isValidSignup(SignupDTO signupDTO) {
        LOGGER.info(signupDTO.toString());
        if (signupDTO.getFirstName() == null || signupDTO.getLastName() == null
                || signupDTO.getUsername() == null || signupDTO.getPassword() == null
                || signupDTO.getEmail() == null)
            return false;
        return true;
    }

}
