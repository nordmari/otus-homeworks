package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.util.ReflectionHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Map<Class<?>, Object> appComponentsByClass = new HashMap<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List<Method> sortedComponentMethods = Arrays.stream(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(
                        comparingInt(method -> method.getAnnotation(AppComponent.class).order())
                )
                .collect(toList());

        Object configClassInst = ReflectionHelper.instantiate(configClass);

        for (Method method : sortedComponentMethods) {
            Parameter[] componentInitParameters = method.getParameters();
            Object[] params = new Object[componentInitParameters.length];
            for (int i = 0, componentInitParametersLength = componentInitParameters.length; i < componentInitParametersLength; i++) {
                Parameter expectedParam = componentInitParameters[i];
                params[i] = getAppComponent(expectedParam.getType());
            }
            Object createdComponent = ReflectionHelper.callMethod(configClassInst, method, params);
            appComponentsByClass.put(createdComponent.getClass(), createdComponent);
            for (Class<?> componentInterface : createdComponent.getClass().getInterfaces()) {
                appComponentsByClass.put(componentInterface, createdComponent);
            }

            String componentName = method.getAnnotation(AppComponent.class).name();
            appComponentsByName.put(componentName, createdComponent);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        Object component = appComponentsByClass.get(componentClass);
        if (component == null) {
            throw new IllegalArgumentException(String.format("Component with class '%s' not found", componentClass.getName()));
        }
        return (C) component;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new IllegalArgumentException(String.format("Component with name '%s' not found", componentName));
        }
        return (C) getAppComponent(component.getClass());
    }
}
