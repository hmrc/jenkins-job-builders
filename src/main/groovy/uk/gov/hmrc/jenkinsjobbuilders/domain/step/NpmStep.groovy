package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

class NpmStep implements Step {

    private static final String EOL = System.getProperty("line.separator")
    private final Step step

    private NpmStep(String bashScript, List<String> npmCommands) {
        this.step = shellStep(sbtCommands.inject(bashScript + EOL) {
            string, item -> string + EOL + "npm run $item"
        })
    }

    static Step npmStep(List<String> commands) {
        npmStep("", commands)
    }

    static Step npmStep(String bashScript, List<String> commands) {
        new NpmStep(bashScript, commands)
    }

    @Override
    Closure toDsl() {
        step.toDsl()
    }
}
