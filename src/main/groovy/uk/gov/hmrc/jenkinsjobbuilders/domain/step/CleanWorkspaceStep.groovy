package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

class CleanWorkspaceStep implements Step {
    private final Step step

    private CleanWorkspaceStep() {
        this.step = shellStep("""
                              |cd \${WORKSPACE}
                              |find . -maxdepth 1 -not -path . -not -path '*dependencies*' -not -path '*target*' -not -path '*logs*' -type d | xargs rm -rf
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
