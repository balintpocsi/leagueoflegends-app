package com.reaktorlabs.resource;

import com.reaktorlabs.model.User;
import com.reaktorlabs.service.AuthenticationService;
import java.security.Principal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Viki
 */
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("auth")
public class AuthResource {
    
    @Context
    private SecurityContext securityContext;
    
    @Inject
    private AuthenticationService authService;
    
    @GET
    public Response getLoggedInUser() {
        final Principal userPrincipal = this.securityContext.getUserPrincipal();
        if(null != userPrincipal) {
            return Response.ok(userPrincipal).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
    @POST
    @Path("login")
    public Response login(User user) throws ServletException {
        authService.login(user);
        return Response.ok().build();
    }
    
    @POST
    @Path("logout")
    public Response logout() throws ServletException {
        authService.logout();
        return Response.ok().build();
    }
    
    @POST
    @Path("register")
    public Response register(User user) {
        authService.register(user);
        return Response.ok().build();
    }
}
