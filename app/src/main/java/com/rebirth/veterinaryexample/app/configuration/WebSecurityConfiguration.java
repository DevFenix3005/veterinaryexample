package com.rebirth.veterinaryexample.app.configuration;

import com.rebirth.veterinaryexample.app.web.resources.WebResourceConstants;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Import(KeycloakSpringBootConfigResolver.class)
@AllArgsConstructor(onConstructor_ = {@Autowired})
@ComponentScan(basePackageClasses = {KeycloakSecurityComponents.class})
public class WebSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    private final SecurityProblemSupport problemSupport;

    private final KeycloakClientRequestFactory keycloakClientRequestFactory;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(this::authorizeRequests);
    }

    private void authorizeRequests(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry) {
        expressionInterceptUrlRegistry
                // ALL ROLES
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(WebResourceConstants.API_PET + "/**").hasAnyRole(ConfigurationConstants.ALL_ROLES)
                .antMatchers(HttpMethod.GET,WebResourceConstants.API_BREED + "/**").hasAnyRole(ConfigurationConstants.ALL_ROLES)
                .antMatchers(HttpMethod.GET,WebResourceConstants.API_PETDETAIL + "/**").hasAnyRole(ConfigurationConstants.ALL_ROLES)
                // ADMIN AND VET ROLES
                .antMatchers(HttpMethod.POST,WebResourceConstants.API_BREED + "/**").hasAnyRole(ConfigurationConstants.ADMINISTRATIVE_ROLES)
                .antMatchers(HttpMethod.PUT,WebResourceConstants.API_BREED + "/**").hasAnyRole(ConfigurationConstants.ADMINISTRATIVE_ROLES)
                .antMatchers(HttpMethod.DELETE,WebResourceConstants.API_BREED + "/**").hasAnyRole(ConfigurationConstants.ADMINISTRATIVE_ROLES)
                .antMatchers(HttpMethod.POST,WebResourceConstants.API_PETDETAIL + "/**").hasAnyRole(ConfigurationConstants.ADMINISTRATIVE_ROLES)
                .anyRequest()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate() {
        return new KeycloakRestTemplate(keycloakClientRequestFactory);
    }

}