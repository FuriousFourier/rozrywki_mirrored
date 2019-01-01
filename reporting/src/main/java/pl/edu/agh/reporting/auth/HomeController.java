package pl.edu.agh.reporting.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private AuthController controller;

    @GetMapping(value = "/home")
    protected String home(final HttpServletRequest req) {
        return "index.html";
    }

    @GetMapping("/")
    protected String login(final HttpServletRequest req) {
        return "redirect:" + controller.buildHomeUrl(req);
    }

}
