package openuss.spring.services;

/**
 * Internal service for user authentication.
 * @author Carsten Fiedler
 */
public interface AuthenticationService {
    boolean authenticateUser(String username, String password);
}
