package uk.gov.hmrc.jenkinsjobbuilders.domain

final class Parameters implements Setting {

    private final Map<String, String> parameters = new HashMap()

    private Parameters(Map<String, String> parameters) {
        this.parameters.putAll(parameters)
    }

    static Parameters parameters(Map<String, String> parameters) {
        new Parameters(parameters)
    }

    @Override
    Closure toDsl() {
        return {
            parameters.each { parameter ->
                stringParam(parameter.key, parameter.value)
            }
        }
    }
}
