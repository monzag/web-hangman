package com.codecool.webhangman.service.permissionsmanagementservice;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Scope("singleton")
@Service
class StaticPermissionsDataContainer {
    private Map<String, Set<String>> urlDispatcher;
    private Map<String, String> defaultRoutes;
    private Set<String> keySet;

    public StaticPermissionsDataContainer(RoutingServiceHandlerCreator routingServiceHandlerCreator) {
        RoutingServiceHandlerCreator.PermissionsDataTemporaryStorage temp = routingServiceHandlerCreator.load();

        this.urlDispatcher = Collections.unmodifiableMap(temp.getUrlDispatcher());
        this.defaultRoutes = Collections.unmodifiableMap(temp.getDefaultRoutes());
        this.keySet = Collections.unmodifiableSet(temp.getKeySet());
    }

    public boolean contains(String key, String path) {
        return this.urlDispatcher.containsKey(key) && this.urlDispatcher.get(key).contains(path);
    }

    public String getDefaultPath(String key) throws RuntimeException {
        return this.defaultRoutes.get(key);
    }

    public Set<String> getKeySet( ) {
        return keySet;
    }
}
