package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.StringEnvironmentVariable.*

class PathEnvironmentVariable implements EnvironmentVariable {
    private final EnvironmentVariable environmentVariable

    private PathEnvironmentVariable(JavaHomeEnvironmentVariable javaHomeEnvironmentVariable) {
        this.environmentVariable = stringEnvironmentVariable('PATH', "${javaHomeEnvironmentVariable.getValue()}/bin:/opt/sbt/bin:\$PATH")
    }

    static EnvironmentVariable pathEnvironmentVariable(JavaHomeEnvironmentVariable javaHomeEnvironmentVariable) {
        new PathEnvironmentVariable(javaHomeEnvironmentVariable)
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
