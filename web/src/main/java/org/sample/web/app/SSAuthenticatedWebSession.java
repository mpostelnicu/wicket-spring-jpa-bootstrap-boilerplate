package org.sample.web.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 * AuthenticatedWebSession implementation using Spring Security.
 *
 * Based on: https://cwiki.apache.org/confluence/display/WICKET/Spring+Security+and+Wicket-auth-roles
 *
 * @author Marcin Zajączkowski, 2011-02-05
 * @author mpostelnicu
 */
public class SSAuthenticatedWebSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = 1L;


	protected final Logger log = LoggerFactory.getLogger(getClass());
    

    @SpringBean(name = "authenticationManagerBean")
    private AuthenticationManager authenticationManager;


	private HttpSession httpSession;

    public SSAuthenticatedWebSession(Request request) {
        super(request);
        Injector.get().inject(this);
        this.httpSession = ((HttpServletRequest) request.getContainerRequest()).getSession();
        if (authenticationManager == null) {
            throw new IllegalStateException("Injection of AuthenticationManager failed.");
        }
        
    }

    public static SSAuthenticatedWebSession getSSAuthenticatedWebSession() {
        return (SSAuthenticatedWebSession) Session.get();
    }
    
    @Override
    public boolean authenticate(String username, String password) {
        boolean authenticated;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);      
        	httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        			SecurityContextHolder.getContext());
            authenticated = authentication.isAuthenticated();
        } catch (AuthenticationException e) {
            log.warn("User '{}' failed to login. Reason: {}", username, e.getMessage());
            authenticated = false;
        }
        return authenticated;
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();
        getRolesIfSignedIn(roles);
        return roles;
    }

    private void getRolesIfSignedIn(Roles roles) {
        if (isSignedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();                        
            addRolesFromAuthentication(roles, authentication);
        }
    }

    private void addRolesFromAuthentication(Roles roles, Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority().replaceFirst("ROLE_",""));
        }
    }

}
