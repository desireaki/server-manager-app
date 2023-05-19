package io.getarrays.server.resource;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Response;
import io.getarrays.server.model.Server;
import io.getarrays.server.service.ServerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
    @Autowired
    private final ServerServiceImpl serverService;
    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("servers", serverService.listServers(50)))
                        .message("Servers retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(
            @PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("servers", serverService.listServers(50)))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed" )
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(
            @RequestBody
            @Valid
            Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("servers", serverService.create(server)))
                        .message("")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(
            @PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server", serverService.getServer(id)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("deleted", serverService.deleteServer(id)))
                        .message("Server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(
            path = "/image/{fileName}",
            produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(
            @PathVariable("fileName") String fileName) throws IOException {
        String fileLocation = System.getProperty("user.home") +
                Paths.get("/Downloads/image/" + fileName);
        Path path = Paths.get(fileLocation);
        System.out.println("TEST " + path.toAbsolutePath());
        return Files.readAllBytes(path.toAbsolutePath());
    }
}
