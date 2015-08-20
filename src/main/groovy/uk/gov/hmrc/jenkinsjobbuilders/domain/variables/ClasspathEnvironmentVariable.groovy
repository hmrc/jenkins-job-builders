package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.StringEnvironmentVariable.stringEnvironmentVariable

class ClasspathEnvironmentVariable implements EnvironmentVariable {
    private final EnvironmentVariable environmentVariable = stringEnvironmentVariable('CLASSPATH', '${CLASSPATH}:/opt/sbt/bin')

    private ClasspathEnvironmentVariable() {}

    static ClasspathEnvironmentVariable classpathEnvironmentVariable() {
        new ClasspathEnvironmentVariable()
    }

    @Override
    String getName() {
        environmentVariable.name
    }

    @Override
    String getValue() {
        environmentVariable.value
    }
}
