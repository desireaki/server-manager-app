package io.getarrays.server.service;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.getarrays.server.repo.ServerRepo;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import static org.springframework.data.domain.PageRequest.of;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServerServiceImpl implements ServerService{
    private final ServerRepo serverRepo;
    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP: Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> listServers(int limit) {
        log.info("Fetching all Servers}");
        return serverRepo.findAll(of(0, limit, Sort.by("name"))).toList();
    }

    @Override
    public Server getServer(Long id) throws Exception {
        log.info("Fetching server by id: {}", id);
        Optional<Server> server = serverRepo.findById(id);
        //TODO Implement custom exception for server not found exception
        return server.orElseThrow(Exception::new);
    }

    @Override
    public Server updateServer(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean deleteServer(Long id) {
        log.info("Deleting server by id: {}", id);
        serverRepo.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + imageNames[new Random().nextInt(4)])
                .toUriString();
        return imageUrl;
    }
}
