package io.getarrays.server.service;

import io.getarrays.server.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    Collection<Server> listServers(int limit);
    Server getServer(Long id) throws Exception;
    Server updateServer(Server server);
    Boolean deleteServer(Long id);

}
