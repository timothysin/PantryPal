package learn.recipes.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {

    private final JwtConverter jwtConverter;

    public SecurityConfig(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {

        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/refresh-token").authenticated()
                .antMatchers(HttpMethod.GET, "/api/home/*").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/game").authenticated()
//                .antMatchers(HttpMethod.PUT, "/api/game/*").authenticated()
//                .antMatchers(HttpMethod.DELETE, "/api/game/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/category").authenticated()
                .antMatchers(HttpMethod.GET, "/api/category/*").authenticated()
                .antMatchers(HttpMethod.POST, "/api/category").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/category/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/category/*").authenticated()
                .antMatchers(HttpMethod.GET, "/api/recipes").authenticated()
                .antMatchers(HttpMethod.GET, "/api/recipes/*").authenticated()
                .antMatchers(HttpMethod.POST, "/api/recipes").authenticated()
                .antMatchers(HttpMethod.POST, "/api/recipes/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/recipes/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/recipes/*").authenticated()
//                .antMatchers(HttpMethod.GET, "/api/availability").permitAll()
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), jwtConverter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
