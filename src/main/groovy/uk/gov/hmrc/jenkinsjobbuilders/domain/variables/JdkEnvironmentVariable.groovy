package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

class JdkEnvironmentVariable implements EnvironmentVariable {
    private final EnvironmentVariable environmentVariable

    private JdkEnvironmentVariable(String value) {
        environmentVariable = StringEnvironmentVariable.stringEnvironmentVariable('JAVA_HOME', value)
    }

    static JdkEnvironmentVariable jdk7EnvironmentVariable() {
        new JdkEnvironmentVariable('/usr/lib/jvm/1.7.0_51')
    }

    static JdkEnvironmentVariable jdk8EnvironmentVariable() {
        new JdkEnvironmentVariable('/usr/lib/jvm/jdk1.8.0_40')
    }

    @Override
    Closure toDsl() {
        return environmentVariable.toDsl()
    }
}
