package uk.gov.hmrc.jenkinsjobbuilders.domain.scm

final class GitHubComScm implements Scm {
    private final Scm scm

    private GitHubComScm(String repository) {
        this.scm = GitHubScm.gitHubScm('github.com', repository, 'master', 'ssh')
    }

    static GitHubComScm gitHubComScm(String repository) {
        new GitHubComScm(repository)
    }

    @Override
    Closure toDsl() {
        scm.toDsl()
    }
}