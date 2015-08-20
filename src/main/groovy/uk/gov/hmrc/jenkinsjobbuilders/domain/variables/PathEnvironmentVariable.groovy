package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

class PathEnvironmentVariable implements EnvironmentVariable {
    private final EnvironmentVariable environmentVariable = StringEnvironmentVariable.stringEnvironmentVariable('PATH', '$JAVA_HOME/bin:/opt/sbt/bin:$PATH')

    private PathEnvironmentVariable() {}

    static EnvironmentVariable pathEnvironmentVariable() {
        new PathEnvironmentVariable()
    }

    @Override
    Closure toDsl() {
        return environmentVariable.toDsl()
    }
}
