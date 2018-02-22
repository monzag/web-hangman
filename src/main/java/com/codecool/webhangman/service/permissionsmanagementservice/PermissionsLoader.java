package com.codecool.webhangman.service.permissionsmanagementservice;

import com.codecool.webhangman.exceptions.RepeatedUrlPathException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
final class PermissionsLoader {

     Set<WebDirectory[]> loadRolesPermissions(Set<Class<?>> loadedClasses) {
        Function<Set<Class<?>>, Set<WebDirectory[]>> webDirectoriesCollector = ofWebDirectories();
        Set<WebDirectory[]> webDirectories = webDirectoriesCollector.apply(loadedClasses);

        if (doWebDirectoriesContainsDuplicates(webDirectories)) {
            throw new RepeatedUrlPathException();
        }

        return webDirectories;
    }

    private boolean doWebDirectoriesContainsDuplicates(Set<WebDirectory[]> webDirectories) {
        Function<Set<WebDirectory[]>, List<String>> retrievePathsFromWebDirectories = onWebDirectoriesGetPaths();
        List<String> collectedPaths = retrievePathsFromWebDirectories.apply(webDirectories);

        List<String> withoutDuplicates = collectedPaths.stream()
                                                        .distinct()
                                                        .collect(Collectors.toList());

        return !collectedPaths.equals(withoutDuplicates);
    }

    private Function<Set<WebDirectory[]>, List<String>>  onWebDirectoriesGetPaths() {
        Function<WebDirectory, String> transformWebDirectoriesToPaths = WebDirectory::getPath;
        return collection -> unpackSetOfArraysToStrings(transformWebDirectoriesToPaths).apply(collection);
    }

    private <T> Function<Set<T[]>, List<String>> unpackSetOfArraysToStrings(Function<T, String> mapper) {
        return collection -> collection.stream()
                                        .flatMap(Stream::of)
                                        .map(mapper)
                                        .collect(Collectors.toList());
    }


    private Function<Set<Class<?>>, Set<WebDirectory[]>> ofWebDirectories() {
        return collection -> ofPlainFilteredClassesByWebDirectory()
                                .andThen(mapClassesToWebDirectories())
                                .apply(collection);
    }

    private Function<Set<Class<?>>, Set<WebDirectory[]>> mapClassesToWebDirectories() {
        return collection -> collection.stream()
                                        .map(Class::getEnumConstants)
                                        .map(c -> (WebDirectory[]) c)
                                        .collect(Collectors.toSet());
    }

    static Function<Set<Class<?>>, Set<Class<?>>> ofPlainFilteredClassesByWebDirectory() {
        Predicate<Class<?>> byFilters = webDirectoriesFilters();
        return collection -> collection.stream()
                                        .filter(byFilters)
                                        .collect(Collectors.toSet());
    }

    private static Predicate<Class<?>> webDirectoriesFilters() {
        return checkWhetherIsEnum()
                .and(containsUrlPathAnnotation())
                .and(containsWebDirectoriesInterface());
    }

    private static Predicate<Class<?>> checkWhetherIsEnum( ) {
        return Class::isEnum;
    }

    private static Predicate<Class<?>> containsUrlPathAnnotation() {
        return _class -> _class.isAnnotationPresent(UrlPaths.class);
    }

    private static Predicate<Class<?>> containsWebDirectoriesInterface() {
        return _class -> containsInterface(WebDirectory.class).test(_class);
    }

    private static Predicate<Class<?>> containsInterface(Class<?> interfaceClass) {
        return _class -> {
            Class<?>[] classInterfaces = _class.getInterfaces();
            return Arrays.stream(classInterfaces)
                         .anyMatch(c -> c.equals(interfaceClass));
        };
    }
}
