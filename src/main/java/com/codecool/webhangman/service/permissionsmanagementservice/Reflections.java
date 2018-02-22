package com.codecool.webhangman.service.permissionsmanagementservice;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class Reflections {

    public Set<Class<?>> reflectPackage(String packageName) {
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
        final Set<BeanDefinition> classes = provider.findCandidateComponents(packageName);

        Set<Class<?>> tempStorage = new HashSet<>();
        try {
            for (BeanDefinition bean: classes) {
                Class<?> loadedClass = Class.forName(bean.getBeanClassName());
                tempStorage.add(loadedClass);
            }

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Couldn't reflect package for gathering classes.");
        }

        return tempStorage;
    }
}
