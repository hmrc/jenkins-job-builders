package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

class StringEnvironmentVariable implements EnvironmentVariable {

    private final String name
    private final String value

    private StringEnvironmentVariable(String name, String value) {
        this.name = name
        this.value = value
    }

    static EnvironmentVariable stringEnvironmentVariable(String name, String value) {
        new StringEnvironmentVariable(name, value)
    }

    @Override
    Closure toDsl() {
        return {
            env(name, value)
        }
    }
}
