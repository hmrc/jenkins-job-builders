package uk.gov.hmrc.jenkinsjobbuilders.domain.step


class MavenStep implements Step {
    private final String goals
    private final String installation

    private MavenStep(String goals, String installation) {
        this.goals = goals
        this.installation = installation
    }

    static Step mavenStep(String installation) {
        mavenStep('clean verify', installation)
    }

    static Step mavenStep(String goals, String installation) {
        new MavenStep(goals, installation)
    }

    @Override
    Closure toDsl() {
        return {
            maven {
                goals(this.goals)
                mavenInstallation(this.installation)
            }
        }
    }
}
