package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.StringEnvironmentVariable.*

class PathEnvironmentVariable implements EnvironmentVariable {
    private final EnvironmentVariable environmentVariable = stringEnvironmentVariable('PATH', '$JAVA_HOME/bin:/opt/sbt/bin:$PATH')

    private PathEnvironmentVariable() {}

    static EnvironmentVariable pathEnvironmentVariable() {
        new PathEnvironmentVariable()
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
