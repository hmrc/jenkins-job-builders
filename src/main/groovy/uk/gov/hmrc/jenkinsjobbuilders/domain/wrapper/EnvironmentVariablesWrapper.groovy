package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.variable.EnvironmentVariable

class EnvironmentVariablesWrapper implements Wrapper {
    private final String variablesFile
    private final List<EnvironmentVariable> variables

    private EnvironmentVariablesWrapper(String variablesFile, List<EnvironmentVariable> variables) {
        this.variablesFile = variablesFile
        this.variables = variables
    }

    static Wrapper environmentVariablesWrapper(String variablesFile, List<EnvironmentVariable> variables) {
        new EnvironmentVariablesWrapper(variablesFile, variables)
    }

    @Override
    Closure toDsl() {
        return {
            environmentVariables {
                variables.each { variable ->
                    env(variable.name, variable.value)
                }
                propertiesFile(variablesFile)
            }

        }
    }
}
