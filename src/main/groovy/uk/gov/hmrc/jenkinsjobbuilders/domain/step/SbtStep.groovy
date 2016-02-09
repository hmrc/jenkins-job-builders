package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

class SbtStep implements Step {

    private final Step step

    private SbtStep(List<String> commands, String tmpDir) {
        this.step = shellStep(commands.inject("mkdir -p $tmpDir") {
                                string, item -> string + "\nsbt $item -Djava.io.tmpdir=$tmpDir"
                              })
    }

    static Step sbtStep(List<String> commands, String tmpDir) {
        new SbtStep(commands, tmpDir)
    }

    @Override
    Closure toDsl() {
        step.toDsl()
    }
}
