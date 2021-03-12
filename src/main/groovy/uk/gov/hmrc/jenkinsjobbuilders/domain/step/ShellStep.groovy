package uk.gov.hmrc.jenkinsjobbuilders.domain.step


class ShellStep implements Step {
    private final String command
    private final Integer unstableReturnCode

    private ShellStep(String command, Integer unstableReturnCode=null) {
        this.command = command
        this.unstableReturnCode = unstableReturnCode
    }

    /**
     * @param  command shell cmd to run
     */
    static Step shellStep(String command) {
        new ShellStep(command)
    }

    /**
     * @param  command shell cmd to run
     * @param  unstableReturnCode (optional) build will be marked as unstable if given return code set
     */
    static Step shellStep(String command, int unstableReturnCode) {
        new ShellStep(command, unstableReturnCode)
    }

    @Override
    Closure toDsl() {
        return {
            shell {
                command(this.command)
                unstableReturn(this.unstableReturnCode)
            }
        }
    }
}
