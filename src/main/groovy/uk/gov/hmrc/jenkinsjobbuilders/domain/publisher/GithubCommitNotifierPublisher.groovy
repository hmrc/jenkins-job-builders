package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher


final class GithubCommitNotifierPublisher implements Publisher {

    private GithubCommitNotifierPublisher() { }

    static GithubCommitNotifierPublisher githubCommitStatusPublisher() {
        new GithubCommitNotifierPublisher()
    }

    @Override
    Closure toDsl() {
        return {
            githubCommitNotifier()
        }
    }
}
