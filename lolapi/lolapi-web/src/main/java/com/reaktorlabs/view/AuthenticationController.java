package com.reaktorlabs.view;

import com.reaktorlabs.model.User;
import com.reaktorlabs.service.AuthenticationInterface;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Viki
 */
@RequestScoped
@Named(value = "authController")
public class AuthenticationController {
    private User user;
    private String errorMsg = "";
    private String successRegistrationMsg = "";
    private AuthenticationInterface authService;
    
    @Inject
    public AuthenticationController(AuthenticationInterface authService) {
        this.user = new User();
        this.authService = authService;
    }
    
    public String createUser() {
        authService.register(user);
        this.successRegistrationMsg = "Sikeresen regisztráltál, mostmár be tudsz jelentkezni.";
        return "/common/login.xhtml";
    }
    
    public String logoutUser() {
        authService.logout();
        return "/common/login.xhtml";
    }
    
    public String loginUser() {
        if(authService.login(user).equals("ok")) {
            return "/user/main.xhtml";
        } else {
            this.errorMsg = "Hibás felhasználónév vagy jelszó.";
            return "/common/login.xhtml";
        }     
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthenticationInterface getAuthService() {
        return authService;
    }

    public void setAuthService(AuthenticationInterface authService) {
        this.authService = authService;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessRegistrationMsg() {
        return successRegistrationMsg;
    }

    public void setSuccessRegistrationMsg(String successRegistrationMsg) {
        this.successRegistrationMsg = successRegistrationMsg;
    }
    
    
}
