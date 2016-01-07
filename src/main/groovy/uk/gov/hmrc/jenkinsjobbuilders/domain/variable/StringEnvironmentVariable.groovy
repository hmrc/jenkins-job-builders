package uk.gov.hmrc.jenkinsjobbuilders.domain.variable

class StringEnvironmentVariable implements EnvironmentVariable {

    private final String name
    private final String value

    private StringEnvironmentVariable(String name, String value) {
        this.name = name
        this.value = value
    }

    static StringEnvironmentVariable stringEnvironmentVariable(String name, String value) {
        new StringEnvironmentVariable(name, value)
    }

    @Override
    String getName() {
        return name
    }

    @Override
    String getValue() {
        return value
    }
}
