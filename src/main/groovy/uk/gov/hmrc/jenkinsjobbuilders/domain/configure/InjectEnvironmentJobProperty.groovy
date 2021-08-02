package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class InjectEnvironmentJobProperty implements Configure {

    private InjectEnvironmentJobProperty() {}

    private String  propertiesContent= ''
    private String  propertiesFilePath = ''
    private String  scriptFilePath = ''
    private String  scriptContent = ''

    InjectEnvironmentJobProperty withPropertiesContent(String propertiesContent) {
        this.propertiesContent = propertiesContent
        this
    }

    InjectEnvironmentJobProperty withPropertiesFilePath(String propertiesFilePath) {
        this.propertiesFilePath = propertiesFilePath
        this
    }

    InjectEnvironmentJobProperty withScriptFilePath(String scriptFilePath) {
        this.scriptFilePath = scriptFilePath
        this
    }

    InjectEnvironmentJobProperty withScriptContent(String scriptContent) {
        this.scriptContent = scriptContent
        this
    }

    Closure toDsl() {
        return {
            it / 'properties' << 'EnvInjectJobProperty' {
                info {
                  loadFilesFromMaster false
                  propertiesContent this.propertiesContent
                  propertiesFilePath this.propertiesFilePath
                  scriptFilePath this.scriptFilePath
                  scriptContent this.scriptContent
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