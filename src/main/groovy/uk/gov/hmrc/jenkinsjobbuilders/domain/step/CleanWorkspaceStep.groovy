package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

class CleanWorkspaceStep implements Step {
    private final Step step

    private CleanWorkspaceStep() {
        this.step = shellStep("""
                              |cd \${WORKSPACE}
                              |find target -mindepth 2 -not -path '*report*' \\( -type f -o -type d -empty \\) -delete
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
