package ru.nordmari.testrunner;

import ru.nordmari.ReflectionHelper;
import ru.nordmari.annotations.After;
import ru.nordmari.annotations.Before;
import ru.nordmari.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static ru.nordmari.ReflectionHelper.callMethod;

public class HWTestRunner {

    public static void run(Class<?> clazz) {
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(Before.class)) {
                beforeMethods.add(declaredMethod);
            }
            if (declaredMethod.isAnnotationPresent(After.class)) {
                afterMethods.add(declaredMethod);
            }
            if (declaredMethod.isAnnotationPresent(Test.class)) {
                testMethods.add(declaredMethod);
            }
        }

        var stat = new HWTestRunnerStatistics();

        for (Method testMethod : testMethods) {
            var testClassInstance = ReflectionHelper.instantiate(clazz);
            try {
                invokeMethods(beforeMethods, testClassInstance);
            } catch (Exception e) {
                invokeMethods(afterMethods, testClassInstance);
                throw new RuntimeException("Exception happened in @Before section", e);
            }

            try {
                callMethod(testClassInstance, testMethod.getName());
                stat.passed(testMethod);
                System.out.println(clazz.getSimpleName() + " > " + testMethod.getName() + " PASSED");
            } catch (Exception e) {
                stat.failed(testMethod);
                System.out.println(clazz.getSimpleName() + " > " + testMethod.getName() + " FAILED");
            }

            invokeMethods(afterMethods, testClassInstance);
        }

        System.out.println(stat.getResult());
    }

    private static void invokeMethods(List<Method> methods, Object classInstance) {
        methods.forEach(method -> callMethod(classInstance, method.getName()));
    }

    public static void main(String[] args) {

    }

}
