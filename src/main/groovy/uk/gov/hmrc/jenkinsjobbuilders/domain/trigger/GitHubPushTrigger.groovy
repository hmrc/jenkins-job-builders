package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger


final class GitHubPushTrigger implements Trigger {

    private GitHubPushTrigger() {
    }

    static GitHubPushTrigger gitHubPushTrigger() {
        new GitHubPushTrigger()
    }

    @Override
    Closure toDsl() {
        return {
            githubPush()
        }
    }
}