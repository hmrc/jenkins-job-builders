package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

class MavenStep implements Step {

    private final Step step

    private MavenStep(String ... commands) {
        this.step = shellStep(commands.inject('mkdir -p \${WORKSPACE}/tmp') {
            string, command -> string + "\n/opt/apache-maven-3.2.1/bin/mvn $command -Djava.io.tmpdir=\"\${WORKSPACE}/tmp\""
        })
    }

    static Step mavenStep(String ... commands) {
        new MavenStep(commands)
    }

    @Override
    Closure toDsl() {
        step.toDsl()
    }
}
