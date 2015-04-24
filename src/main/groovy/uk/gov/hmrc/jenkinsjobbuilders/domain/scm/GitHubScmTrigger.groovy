package uk.gov.hmrc.jenkinsjobbuilders.domain.scm


final class GitHubScmTrigger implements ScmTrigger {

    private GitHubScmTrigger() {
    }

    static GitHubScmTrigger gitHubScmTrigger() {
        new GitHubScmTrigger()
    }

    @Override
    Closure toDsl() {
        return {
            githubPush()
        }
    }
}