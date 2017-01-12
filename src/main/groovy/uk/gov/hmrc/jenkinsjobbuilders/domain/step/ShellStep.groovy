package uk.gov.hmrc.jenkinsjobbuilders.domain.step


class ShellStep implements Step {
    final String command

    ShellStep(String command) {
        this.command = command
    }

    static Step shellStep(String command) {
        new ShellStep(command)
    }

    @Override
    Closure toDsl() {
        return {
            shell(command)
        }
    }
}
