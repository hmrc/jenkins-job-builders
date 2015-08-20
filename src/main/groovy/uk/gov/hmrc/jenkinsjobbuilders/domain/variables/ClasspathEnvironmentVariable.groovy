package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.StringEnvironmentVariable.stringEnvironmentVariable

class ClasspathEnvironmentVariable implements EnvironmentVariable {
    private final EnvironmentVariable environmentVariable = stringEnvironmentVariable('CLASSPATH', '${CLASSPATH}:/opt/sbt/bin')

    private ClasspathEnvironmentVariable() {}

    static EnvironmentVariable classpathEnvironmentVariable() {
        new ClasspathEnvironmentVariable()
    }

    @Override
    Closure toDsl() {
        return environmentVariable.toDsl()
    }
}
