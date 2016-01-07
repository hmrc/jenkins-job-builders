package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.variable.EnvironmentVariable

class EnvironmentVariablesWrapper implements Wrapper {

    private final List<EnvironmentVariable> variables

    private EnvironmentVariablesWrapper(List<EnvironmentVariable> variables) {
        this.variables = variables
    }

    static Wrapper environmentVariablesWrapper(List<EnvironmentVariable> variables) {
        new EnvironmentVariablesWrapper(variables)
    }

    @Override
    Closure toDsl() {
        return {
            environmentVariables {
                variables.each { variable ->
                    env(variable.name, variable.value)
                }
            }

        }
    }
}
