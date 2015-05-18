package uk.gov.hmrc.jenkinsjobbuilders.domain.step


class GradleStep implements Step {
    private final String command

    private GradleStep(String command) {
        this.command = command
    }

    static Step gradleStep(String command) {
        new GradleStep(command)
    }

    @Override
    Closure toDsl() {
        return {
            gradle(command, '', true) {
                fromRootBuildScriptDir('true')
            }

        }
    }
}
