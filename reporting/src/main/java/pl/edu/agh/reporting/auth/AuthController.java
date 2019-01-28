package pl.edu.agh.reporting.auth;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthController {

    private final AuthenticationController controller;
    private final String userInfoAudience;
    private final String domain;
    private final String clientId;

    @Autowired
    public AuthController(AuthConfig config) {
        domain = config.getDomain();
        clientId = config.getClientId();
        controller = AuthenticationController.newBuilder(domain, clientId, config.getClientSecret()).build();
        userInfoAudience = String.format("https://%s/userinfo", domain);
    }

    public Tokens handle(HttpServletRequest request) throws IdentityVerificationException {
        return controller.handle(request);
    }
    
    private String buildCanonicalUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() +
                (request.getServerPort() != 80 ? ":" + request.getServerPort() : "");
    }

    public String buildCallbackUrl(HttpServletRequest request) {
        return buildCanonicalUrl(request) + "/callback";
    }

    public String buildHomeUrl(HttpServletRequest request) {
        return buildCanonicalUrl(request) + "/home";
    }

    public String buildLogoutUrl(HttpServletRequest request) {
        return String.format("https://%s/v2/logout?client_id=%s&returnTo=%s",
                domain,
                clientId,
                buildCanonicalUrl(request));
    }

    public String buildAuthorizeUrl(HttpServletRequest request) {
        return controller
                .buildAuthorizeUrl(request, buildCallbackUrl(request))
                .withAudience(userInfoAudience)
                .build();
    }

}
