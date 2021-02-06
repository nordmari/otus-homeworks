package ru.nordmari.testrunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class HWTestRunnerStatistics {

    private final List<String> passedMethods = new ArrayList<>();
    private final List<String> failedMethods = new ArrayList<>();

    public void failed(Method method) {
        failedMethods.add(method.getName());
    }

    public void passed(Method method) {
        passedMethods.add(method.getName());
    }

    public String getResult() {
        int failedCount = failedMethods.size();
        int passedCount = passedMethods.size();
        int totalRun = failedCount + passedCount;

        return format("Tests run: %d, Passed: %d, Failures: %d", totalRun, passedCount, failedCount);
    }
}
