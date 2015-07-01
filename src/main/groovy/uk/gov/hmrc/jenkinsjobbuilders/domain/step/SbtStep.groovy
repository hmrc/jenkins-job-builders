package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

class SbtStep implements Step {

    private final Step step

    private SbtStep(String ... commands) {
        this.step = shellStep(commands.inject('mkdir -p \${WORKSPACE}/tmp') {
                                mkdir, item -> mkdir + "\nsbt $item -Djava.io.tmpdir=\${WORKSPACE}/tmp"
                              })
    }

    static Step sbtStep(String ... commands) {
        new SbtStep(commands)
    }

    @Override
    Closure toDsl() {
        step.toDsl()
    }
}
