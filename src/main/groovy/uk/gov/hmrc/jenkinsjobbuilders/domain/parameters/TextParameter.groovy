package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters


class TextParameter implements Parameter {

    private final String name
    private final String defaultValue
    private final String description

    private TextParameter(String name, String defaultValue, String description) {
        this.description = description
        this.defaultValue = defaultValue
        this.name = name
    }

    static TextParameter textParameter(String name, String defaultValue, String description = null) {
        new TextParameter(name, defaultValue, description)
    }

    @Override
    Closure toDsl() {
        return {
            textParam(name, defaultValue, description)
        }
    }
}
