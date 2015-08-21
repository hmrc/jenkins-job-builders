package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.StringEnvironmentVariable.stringEnvironmentVariable

enum JdkEnvironmentVariable implements EnvironmentVariable {
    JDK7('/usr/lib/jvm/jdk1.7.0_51'),
    JDK8('/usr/lib/jvm/jdk1.8.0_40')

    private final EnvironmentVariable environmentVariable

    private JdkEnvironmentVariable(String path) {
        this.environmentVariable = stringEnvironmentVariable('JAVA_HOME', path)
    }

    @Override
    String getName() {
        environmentVariable.name
    }

    @Override
    String getValue() {
        environmentVariable.value
    }

    boolean isJdk8() {
        return this == JDK8
    }
}
