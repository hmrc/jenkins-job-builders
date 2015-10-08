package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

class CleanWorkspaceStep implements Step {
    private final Step step

    private CleanWorkspaceStep() {
        this.step = shellStep("""
                              |cd \${WORKSPACE}
                              |find target -maxdepth 1 -type d -not -name '*reports' -not -name 'target' | xargs rm -rf
                              |find . -maxdepth 1 -type d -not -name 'target' -not -name '.*' -not -name 'logs' | xargs rm -rf
                              """.stripMargin())
    }

    public static Step cleanWorkspace() {
        new CleanWorkspaceStep()
    }

    @Override
    Closure toDsl() {
        return step.toDsl()
    }
}
