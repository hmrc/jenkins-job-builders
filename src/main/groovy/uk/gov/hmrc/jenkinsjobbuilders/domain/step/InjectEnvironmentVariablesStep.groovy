package uk.gov.hmrc.jenkinsjobbuilders.domain.step


class InjectEnvironmentVariablesStep implements Step {
    private final String fileName

    private InjectEnvironmentVariablesStep(String fileName) {
        this.fileName = fileName
    }

    static Step injectEnvironmentVariablesStep(String fileName) {
        new InjectEnvironmentVariablesStep(fileName)
    }

    @Override
    Closure toDsl() {
        return {
            environmentVariables {
                propertiesFile(this.fileName)
            }
        }
    }
}
