package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class InjectEnvironmentJobProperty implements Configure {

    private InjectEnvironmentJobProperty() {}

    private String propertiesContent = ''
    private String propertiesFilePath = ''
    private String scriptFilePath = ''
    private String scriptContent = ''

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
                  if ( this.propertiesContent != '' ) {
                    propertiesContent this.propertiesContent
                  }
                  if ( this.propertiesFilePath != '' ) {
                    propertiesFilePath this.propertiesFilePath
                  }
                  if ( this.scriptFilePath != '' ) {
                    scriptFilePath this.scriptFilePath
                  }
                  if ( this.scriptContent != '' ) {
                    scriptContent this.scriptContent
                  }
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