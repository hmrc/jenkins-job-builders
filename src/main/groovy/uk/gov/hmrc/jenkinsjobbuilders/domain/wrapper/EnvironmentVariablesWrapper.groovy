package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.variable.EnvironmentVariable

class EnvironmentVariablesWrapper implements Wrapper {
    private final String variablesFile
    private final String scriptContent
    private final List<EnvironmentVariable> variables

    private EnvironmentVariablesWrapper(String variablesFile, List<EnvironmentVariable> variables, String scriptContent) {
        this.variablesFile = variablesFile
        this.variables = variables
        this.scriptContent = scriptContent
    }

    static Wrapper environmentVariablesWrapper(String variablesFile, List<EnvironmentVariable> variables, String scriptContent = '') {
        new EnvironmentVariablesWrapper(variablesFile, variables, scriptContent)
    }

    @Override
    Closure toDsl() {
        return {
            environmentVariables {
                variables.each { variable ->
                    env(variable.name, variable.value)
                }
                propertiesFile(variablesFile)
                script(scriptContent)
            }

        }
    }
}
