package uk.gov.hmrc.jenkinsjobbuilders.domain.scm

final class GitHubComScm implements Scm {
    private final Scm scm

    private GitHubComScm(String repository, String credentials) {
        this.scm = GitHubScm.gitHubScm('github.com', repository, 'master', 'ssh', null, credentials)
    }

    static GitHubComScm gitHubComScm(String repository) {
        gitHubComScm(repository, null)
    }

    static GitHubComScm gitHubComScm(String repository, String credentials) {
        new GitHubComScm(repository, credentials)
    }

    @Override
    Closure toDsl() {
        scm.toDsl()
    }
}