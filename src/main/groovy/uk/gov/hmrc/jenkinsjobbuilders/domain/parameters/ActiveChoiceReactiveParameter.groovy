package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters

final class ActiveChoiceReactiveParameter implements Parameter {

    final String name
    final String choiceType
    final String description
    final String referencedParameters
    final String script
    final String fallbackScript
    final boolean filterable

    private ActiveChoiceReactiveParameter(String name,
                                          String choiceType,
                                          String referencedParameters,
                                          String script,
                                          String fallbackScript,
                                          boolean filterable,
                                          String description) {
        this.name = name
        this.choiceType = choiceType
        this.description = description
        this.filterable = filterable
        this.script = script
        this.fallbackScript = fallbackScript
        this.referencedParameters = referencedParameters
    }

    static ActiveChoiceReactiveParameter activeChoiceReactiveParameter(String name,
                                                                       String choiceType,
                                                                       String referencedParameters,
                                                                       String script,
                                                                       String fallbackScript = null,
                                                                       boolean filterable = false,
                                                                       String description = null) {
        new ActiveChoiceReactiveParameter(name, choiceType, referencedParameters, script, fallbackScript, filterable, description)
    }

    @Override
    Closure toDsl() {
        return {
            activeChoiceReactiveParam(this.name) {
                description(this.description)
                filterable(this.filterable)
                choiceType(this.choiceType)
                groovyScript {
                    script(this.script)
                    fallbackScript(this.fallbackScript)
                }
                referencedParameter(this.referencedParameters)
            }
        }
    }
}
