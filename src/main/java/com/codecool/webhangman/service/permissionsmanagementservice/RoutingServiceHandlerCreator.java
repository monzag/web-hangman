package com.codecool.webhangman.service.permissionsmanagementservice;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
final class RoutingServiceHandlerCreator {
    private DefaultRouteLoader defaultRouteLoader;
    private PermissionsLoader permissionsLoader;
    private ResourceLoader resourceLoader;

    public RoutingServiceHandlerCreator(DefaultRouteLoader defaultRouteLoader,
                                        PermissionsLoader permissionsLoader,
                                        ResourceLoader resourceLoader) {

        this.defaultRouteLoader = defaultRouteLoader;
        this.permissionsLoader = permissionsLoader;
        this.resourceLoader = resourceLoader;
    }

     PermissionsDataTemporaryStorage load() {
        Function<Set<WebDirectory[]>, Map<String, Set<String>>>
                createDispatcherConnectors = sortDirectoriesByTheirIdentity();

        Set<Class<?>> loadedClasses = this.resourceLoader.loadUrlPathsContainers();

        Map<String, String> defaultRoutes = this.defaultRouteLoader.loadDefaultRoutes(loadedClasses);

        Map<String, Set<String>> map = createDispatcherConnectors.apply(this.permissionsLoader.loadRolesPermissions(loadedClasses));

        return new PermissionsDataTemporaryStorage(map, defaultRoutes, map.keySet());
    }

    final static class PermissionsDataTemporaryStorage {
        private Map<String, Set<String>> urlDispatcher;
        private Map<String, String> defaultRoutes;
        private Set<String> keySet;

        private PermissionsDataTemporaryStorage(Map<String, Set<String>> urlDispatcher, Map<String, String> defaultRoutes, Set<String> keySet) {
            this.urlDispatcher = urlDispatcher;
            this.defaultRoutes = defaultRoutes;
            this.keySet = keySet;
        }

        public Map<String, Set<String>> getUrlDispatcher( ) {
            return urlDispatcher;
        }

        public Map<String, String> getDefaultRoutes( ) {
            return defaultRoutes;
        }

        public Set<String> getKeySet( ) {
            return keySet;
        }
    }

    private Function<Set<WebDirectory[]>, Map<String, Set<String>>> sortDirectoriesByTheirIdentity() {
        return collection -> collection.stream()
                                        .flatMap(Stream::of)
                                        .collect(Collectors.groupingBy(WebDirectory::getPathsContainerIdentity,
                                                 Collectors.mapping(WebDirectory::getPath, Collectors.toSet())));
    }
}
