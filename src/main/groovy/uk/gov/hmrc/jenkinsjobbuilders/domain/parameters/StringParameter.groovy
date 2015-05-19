package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters


class StringParameter implements Parameter {

    private final String name
    private final String defaultValue
    private final String description

    private StringParameter(String name, String defaultValue, String description) {
        this.description = description
        this.defaultValue = defaultValue
        this.name = name
    }

    static StringParameter stringParameter(String name, String defaultValue, String description = null) {
        new StringParameter(name, defaultValue, description)
    }

    @Override
    Closure toDsl() {
        return {
            stringParam(name, defaultValue, description)
        }
    }
}
