package uk.gov.hmrc.jenkinsjobbuilders.domain.scm

import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubScm.gitHubScm

final class GitHubComScm implements Scm {
    private final Scm scm

    private GitHubComScm(String repository, String credentials, String branch, String refspec, int depth, String name = null) {
        this.scm = gitHubScm('github.com', repository, branch, 'ssh', refspec, credentials, name, depth)
    }

    static GitHubComScm gitHubComScm(String repository, String credentials) {
        gitHubComScm(repository, credentials, 'master')
    }

    static GitHubComScm gitHubComScm(String repository, String credentials, String branch, int depth = 0, String refspec = null) {
        new GitHubComScm(repository, credentials, branch, refspec, depth)
    }

    @Override
    Closure toDsl() {
        scm.toDsl()
    }
}