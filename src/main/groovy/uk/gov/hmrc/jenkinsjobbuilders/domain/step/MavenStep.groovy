package uk.gov.hmrc.jenkinsjobbuilders.domain.step


class MavenStep implements Step {
    private final String goals
    private final String installation
    private final String rootPom

    private MavenStep(String goals, String installation, String rootPom) {
        this.goals = goals
        this.installation = installation
        this.rootPom = rootPom
    }

    static Step mavenStep(String goals, String installation, String rootPom = '') {
        new MavenStep(goals, installation, rootPom)
    }

    @Override
    Closure toDsl() {
        return {
            maven {
                goals(this.goals)
                mavenInstallation(this.installation)
                rootPOM(this.rootPom)
            }
        }
    }
}
