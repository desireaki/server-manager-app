package io.getarrays.server;

import io.getarrays.server.model.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.getarrays.server.repo.ServerRepo;

import static io.getarrays.server.enumeration.Status.SERVER_UP;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepo serverRepo) {
		return args -> {
			serverRepo.save(
					new Server(null,"192.168.1.160", "Ubuntu Linux", "16 GB",
							"Personal PC", "http://localhost:8080/server/image/server1.png", SERVER_UP));

			serverRepo.save(
					new Server(null,"192.168.1.161", "Fedora Linux", "8 GB",
							"Dell Tower", "http://localhost:8080/server/image/server1.png", SERVER_UP));

			serverRepo.save(
					new Server(null,"192.168.1.162", "MS 2008", "32 GB",
							"Web Server", "http://localhost:8080/server/image/server1.png", SERVER_UP));

			serverRepo.save(
					new Server(null,"192.168.1.163", "Red Hat Enterprise Linux", "16GB",
							"Mail Server", "http://localhost:8080/server/image/server1.png", SERVER_UP));
		};
	}
}
