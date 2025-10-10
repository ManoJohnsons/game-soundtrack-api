package io.github.manojohnsons.gamesoundtracksapi.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Game Soundtracks API", version = "1.0", description = "A RESTful API to catalog and explore video game soundtracks. "
		+
		"This project includes full CRUD features, JWT-based user authentication, and role-based authorization.", contact = @Contact(name = "John Paulo S. Barbosa", email = "johnpaulo0401@gmail.com", url = "https://github.com/ManoJohnsons"), license = @License(name = "MIT License", url = "https://github.com/ManoJohnsons/game-soundtrack-api/blob/main/LICENSE")), tags = {
				@Tag(name = "Authentication", description = "Endpoint for user authentication (login)."),
				@Tag(name = "Users", description = "Endpoints for managing users and their favorite songs."),
				@Tag(name = "Games", description = "Endpoints for managing games in the catalog."),
				@Tag(name = "Artists", description = "Endpoints for managing artists."),
				@Tag(name = "Platforms", description = "Endpoints for managing platforms."),
				@Tag(name = "Albums", description = "Endpoints for managing albums as sub-resources of games."),
				@Tag(name = "Musics", description = "Endpoints for searching and managing musics.")
		})
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {

}
