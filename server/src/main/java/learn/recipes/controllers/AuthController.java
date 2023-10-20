package learn.recipes.controllers;

import learn.recipes.domain.AppUserService;
import learn.recipes.domain.Result;
import learn.recipes.models.AppUser;
import learn.recipes.security.JwtConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ConditionalOnWebApplication
public class AuthController {

    private final AppUserService appUserService;
    private final JwtConverter jwtConverter;
    private final AuthenticationManager authenticationManager;

    public AuthController(AppUserService appUserService,
                          JwtConverter jwtConverter,
                          AuthenticationManager authenticationManager) {
        this.appUserService = appUserService;
        this.jwtConverter = jwtConverter;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                credentials.get("username"), credentials.get("password"));

        // Moved AuthenticationException handling to the GlobalErrHandler
        Authentication authentication = authenticationManager.authenticate(authToken);
        if (authentication.isAuthenticated()) {
            AppUser appUser = (AppUser) authentication.getPrincipal();
            String jwt = jwtConverter.getTokenFromUser(appUser);
            Map<String, String> result = new HashMap<>();
            result.put("jwt_token", jwt);
            return ResponseEntity.ok(result);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> credentials) {
        Result<AppUser> result = appUserService.add(
                credentials.get("username"), credentials.get("password"));
        if (result.isSuccess()) {
            Map<String, Integer> userId = new HashMap<>();
            userId.put("user_id", result.getPayload().getId());
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@AuthenticationPrincipal AppUser appUser) {
        String jwt = jwtConverter.getTokenFromUser(appUser);
        Map<String, String> result = new HashMap<>();
        result.put("jwt_token", jwt);
        return ResponseEntity.ok(result);
    }
}
