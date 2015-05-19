package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters


class ChoiceParameter implements Parameter {

    private final String name
    private final List<String> values
    private final String description

    private ChoiceParameter(String name, List<String> values, String description) {
        this.description = description
        this.values = values
        this.name = name
    }

    static ChoiceParameter choiceParameter(String name, List<String> values, String description = null) {
        new ChoiceParameter(name, values, description)
    }

    @Override
    Closure toDsl() {
        return {
            choiceParam(name, values, description)
        }
    }
}
