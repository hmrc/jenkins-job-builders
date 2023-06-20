package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.variable.EnvironmentVariable

class EnvironmentVariablesWrapper implements Wrapper {
    private final String variablesFile
    private final String scriptContent
    private final String groovyScriptContent
    private final List<EnvironmentVariable> variables

    private EnvironmentVariablesWrapper(String variablesFile, List<EnvironmentVariable> variables,
                                        String scriptContent, String groovyScriptContent) {
        this.variablesFile = variablesFile
        this.variables = variables
        this.scriptContent = scriptContent
        this.groovyScriptContent = groovyScriptContent
    }

    static Wrapper environmentVariablesWrapper(String variablesFile, List<EnvironmentVariable> variables,
                                               String scriptContent = '', String groovyScriptContent = '') {
        new EnvironmentVariablesWrapper(variablesFile, variables, scriptContent, groovyScriptContent)
    }

    @Override
    Closure toDsl() {
        return {
            environmentVariables {
                variables.each { variable ->
                    env(variable.getName(), variable.getValue())
                }
                propertiesFile(variablesFile)
                script(scriptContent)
                groovy(groovyScriptContent)
            }

        }
    }
}
