package eu.qcloud.security.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.security.JwtAuthenticationRequest;
import eu.qcloud.security.JwtTokenUtil;
import eu.qcloud.security.model.User;
import eu.qcloud.security.repository.UserRepository;
import eu.qcloud.security.service.JwtAuthenticationResponse;

@RestController
public class AuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        // Perform the security
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            Optional<User> userOpt = userRepository.findOneByUsername(authenticationRequest.getUsername());
            if (userOpt.isPresent()) {
                userOpt.get().setLastQCloudLoginDate(new Date());
                userRepository.save(userOpt.get());
            }
        }

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Date expirationDate = generateExpirationDate();
        final String token = jwtTokenUtil.generateToken(userDetails, expirationDate);

        // Return the token
        HttpHeaders headers = new HttpHeaders();
        headers.set("tokenexpiration", Long.toString((expirationDate.getTime())));
        // headers.set("tokenExpiration",expirationDate.toString());

        // return ResponseEntity.ok(new JwtAuthenticationResponse(token));

        return (new ResponseEntity<>(new JwtAuthenticationResponse(token), headers, HttpStatus.OK));
    }

    /**
     *
     * @param RequestHeader String username
     * @param RequestHeader String password
     * @return A response with the login token
     * @throws AuthenticationException
     * @desc Just calls the other auth method This endpoint is used by the pipeline
     *       and QCrawler and uses the headers instead the body, but at the end is
     *       the same because just calls the same method If some header is missing
     *       just throws a bad request error If the credentials are wrong just
     *       returns a 401 error
     */
    @RequestMapping(value = "/api/4uth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationTokenPipeline(@RequestHeader(name = "username") String username,
            @RequestHeader(name = "password") String password) throws AuthenticationException {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            Optional<User> userOpt = userRepository.findOneByUsername(username);
            if (userOpt.isPresent()) {
                userOpt.get().setLastQcrawlerLoginDate(new Date());
                userRepository.save(userOpt.get());
            }
        }

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Date expirationDate = generateExpirationDate();
        final String token = jwtTokenUtil.generateToken(userDetails, expirationDate);

        // Return the token
        HttpHeaders headers = new HttpHeaders();
        headers.set("tokenexpiration", Long.toString((expirationDate.getTime())));
        return (new ResponseEntity<>(new JwtAuthenticationResponse(token), headers, HttpStatus.OK));
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

}
