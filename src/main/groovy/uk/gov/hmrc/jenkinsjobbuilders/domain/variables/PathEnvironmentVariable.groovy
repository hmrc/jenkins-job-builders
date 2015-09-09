package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.StringEnvironmentVariable.*

class PathEnvironmentVariable implements EnvironmentVariable {
    private final EnvironmentVariable environmentVariable

    private PathEnvironmentVariable(JdkEnvironmentVariable jdk) {
        this.environmentVariable = stringEnvironmentVariable('PATH', "${jdk.getValue()}/bin:/opt/sbt/bin:\$PATH")
    }

    static EnvironmentVariable pathEnvironmentVariable(JdkEnvironmentVariable jdk) {
        new PathEnvironmentVariable(jdk)
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
