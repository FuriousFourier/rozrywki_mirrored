package pl.edu.agh.reporting.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.agh.reporting.auth.AuthController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {

    @Autowired
    private AuthController controller;

    @GetMapping(value = "/logout")
    protected String logout(final HttpServletRequest req) {
        invalidateSession(req);
        return "redirect:" + controller.buildLogoutUrl(req);

    }

    private void invalidateSession(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
    }

}
