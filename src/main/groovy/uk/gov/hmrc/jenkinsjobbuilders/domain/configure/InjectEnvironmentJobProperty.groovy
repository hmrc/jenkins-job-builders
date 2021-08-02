package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class InjectEnvironmentJobProperty implements Configure {

    private InjectEnvironmentJobProperty() {}

    private String  propertiesContent= ''
    private String  propertiesFile = ''
    private String  scriptFile = ''
    private String  script = ''

    InjectEnvironmentJobProperty withPropertiesContent(String propertiesContent) {
        this.propertiesContent = propertiesContent
        this
    }

    InjectEnvironmentJobProperty withPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile
        this
    }

    InjectEnvironmentJobProperty withScriptFile(String scriptFile) {
        this.scriptFile = scriptFile
        this
    }

    InjectEnvironmentJobProperty withScript(String script) {
        this.script = script
        this
    }

    Closure toDsl() {
        return {
            it / 'properties' << 'envInjectJobProperty' {
                info {
                  loadFilesFromMaster false
                  propertiesContent this.propertiesContent
                  propertiesFile this.propertiesFile
                  scriptFile this.scriptFile
                  script this.script
                  }
                keepBuildVariables true
                keepJenkinsSystemVariables true
                overrideBuildParameters false
                on true
            }
        }
    }


    static InjectEnvironmentJobProperty injectEnvironmentJobProperty() {
        new InjectEnvironmentJobProperty()
    }

}