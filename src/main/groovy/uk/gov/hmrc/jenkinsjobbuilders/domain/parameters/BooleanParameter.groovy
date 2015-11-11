package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters


class BooleanParameter implements Parameter {

    private final String name
    private final boolean defaultValue
    private final String description

    private BooleanParameter(String name, boolean defaultValue, String description) {
        this.description = description
        this.defaultValue = defaultValue
        this.name = name
    }

    static BooleanParameter booleanParameter(String name, boolean defaultValue, String description = null) {
        new BooleanParameter(name, defaultValue, description)
    }

    @Override
    Closure toDsl() {
        return {
            booleanParam(name, defaultValue, description)
        }
    }
}