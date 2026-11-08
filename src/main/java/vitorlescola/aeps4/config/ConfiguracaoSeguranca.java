
package vitorlescola.aeps4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vitorlescola.aeps4.service.CustomUserDetailsService;

@Configuration
public class ConfiguracaoSeguranca {

    @Bean
    public PasswordEncoder encoderSenha() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomUserDetailsService userDetailsService
    ) throws Exception {

        http
                .userDetailsService(userDetailsService)

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )

                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/cadastro",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        // Somente fornecedores
                        .requestMatchers("/fornecedor/**")
                        .hasRole("FORNECEDOR")

                        // Somente usuários
                        .requestMatchers("/usuario/**")
                        .hasRole("USUARIO")

                        // Demais páginas exigem autenticação
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/menu", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }
}