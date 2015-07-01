package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class EnvironmentVariablesWrapper implements Wrapper {

    private final Map<String, String> variables

    private EnvironmentVariablesWrapper(Map<String, String> variables) {
        this.variables = variables
    }

    static Wrapper environmentVariablesWrapper(Map<String, String> variables) {
        new EnvironmentVariablesWrapper(variables)
    }

    @Override
    Closure toDsl() {
        return {
            environmentVariables {
                variables.each { entry ->
                    env(entry.key, entry.value)
                }
            }

        }
    }
}
