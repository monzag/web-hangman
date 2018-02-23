package com.codecool.webhangman.service.permissionsmanagementservice;

import com.codecool.webhangman.exceptions.IllegalActionException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class DefaultRouteLoader {

    Map<String, String> loadDefaultRoutes(Set<Class<?>> loadedClasses) {
        Function<Set<Class<?>>, Set<Class<?>>> of = PermissionsLoader.ofPlainFilteredClassesByWebDirectory();
        Set<Class<?>> webDirectoryClasses = of.apply(loadedClasses);
        List<ClassContainer> classesAsClassContainers = mapClassesToClassContainers(webDirectoryClasses);

        Map<String, String> accumulator = new HashMap<>();
        for (ClassContainer classContainer : classesAsClassContainers) {
            if (!doDirectoriesContainerStoreOneDefaultRoute(classContainer.getFields())) {
                throw new RuntimeException("Expect ONE default route");
            }

            try {
                Field defaultRoute = findDefaultRoute(classContainer.getFields());
                WebDirectory webDirectory = (WebDirectory) defaultRoute.get(classContainer.get_class());
                accumulator.put(webDirectory.getPathsContainerIdentity(), webDirectory.getPath());
            } catch (IllegalAccessException e) {
                throw new IllegalActionException();
            }
        }

        return accumulator;
    }

    private List<ClassContainer> mapClassesToClassContainers(Set<Class<?>> containers) {
        Function<Class<?>, ClassContainer> classConverterToContainer = c -> ClassContainer.create(c, c.getFields());
        return containers.stream()
                         .map(classConverterToContainer)
                         .collect(Collectors.toList());
    }

    private Field findDefaultRoute(Field[] fields) {
        return Arrays.stream(fields)
                     .filter(f -> f.isAnnotationPresent(DefaultRoute.class))
                     .findFirst()
                     .orElse(null);
    }

    private boolean doDirectoriesContainerStoreOneDefaultRoute(Field[] fields) {
        long amountOfDefaultRoutes = Arrays.stream(fields)
                                           .filter(_class -> _class.isAnnotationPresent(DefaultRoute.class))
                                           .count();

        return amountOfDefaultRoutes == 1;
    }
}
