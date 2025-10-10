package io.github.manojohnsons.gamesoundtracksapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.manojohnsons.gamesoundtracksapi.security.CustomAccessDeniedHandler;
import io.github.manojohnsons.gamesoundtracksapi.security.CustomAuthenticationEntryPoint;
import io.github.manojohnsons.gamesoundtracksapi.security.SecurityFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final SecurityFilter securityFilter;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	public SecurityConfig(SecurityFilter securityFilter, CustomAccessDeniedHandler customAccessDeniedHandler,
			CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
		this.securityFilter = securityFilter;
		this.customAccessDeniedHandler = customAccessDeniedHandler;
		this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
	}

	@Bean
	@Profile("dev")
	SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
		System.out.println(">>> CARREGANDO CONFIGURAÇÕES DE SEGURANÇA PARA [DEV] <<<");
		http
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // For
																											// H2
																											// Console
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.POST, "/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/users").permitAll()
						.requestMatchers("/v3/api-docs/**", "/swagger-ui.html",
								"/swagger-ui/**")
						.permitAll()
						.requestMatchers("/h2-console/**").permitAll()
						.requestMatchers(HttpMethod.GET).permitAll()
						// POST and DELETE favorites, PUT and DELETE for users: only
						// authenticated (fine-grained control via @PreAuthorize)
						.requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()
						.requestMatchers("/users/favorites/**").authenticated()
						.requestMatchers(HttpMethod.POST).hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
						.anyRequest().authenticated())
				.exceptionHandling(ex -> ex
						.accessDeniedHandler(customAccessDeniedHandler)
						.authenticationEntryPoint(customAuthenticationEntryPoint))
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	@Profile("!dev")
	SecurityFilterChain prodSecurityFilterChain(HttpSecurity http) throws Exception {
		System.out.println(">>> CARREGANDO CONFIGURAÇÕES DE SEGURANÇA PARA [PROD] <<<");
		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.POST, "/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/users").permitAll()
						.requestMatchers("/v3/api-docs/**", "/swagger-ui.html",
								"/swagger-ui/**")
						.permitAll()
						.requestMatchers(HttpMethod.GET).permitAll()
						// POST and DELETE favorites, PUT and DELETE for users: only
						// authenticated (fine-grained control via @PreAuthorize)
						.requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()
						.requestMatchers("/users/favorites/**").authenticated()
						.requestMatchers(HttpMethod.POST).hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
						.anyRequest().authenticated())
				.exceptionHandling(ex -> ex
						.accessDeniedHandler(customAccessDeniedHandler)
						.authenticationEntryPoint(customAuthenticationEntryPoint))
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
