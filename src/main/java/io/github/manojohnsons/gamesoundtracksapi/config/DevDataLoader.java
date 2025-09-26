package io.github.manojohnsons.gamesoundtracksapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.manojohnsons.gamesoundtracksapi.user.Role;
import io.github.manojohnsons.gamesoundtracksapi.user.User;
import io.github.manojohnsons.gamesoundtracksapi.user.UserRepository;

@Configuration
@Profile("dev")
public class DevDataLoader {
    
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin") == null) {
                System.out.println(">>> [DEV] Criando usuário admin...");

                User admin = new User("admin", passwordEncoder.encode("admin123"), Role.ADMIN);
                userRepository.save(admin);

                System.out.println(">>> [DEV] Usuário admin criado com sucesso!");
            }
        };
    }
}
