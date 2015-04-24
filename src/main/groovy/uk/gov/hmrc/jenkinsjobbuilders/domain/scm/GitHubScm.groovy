package uk.gov.hmrc.jenkinsjobbuilders.domain.scm

final class GitHubScm implements Scm {
    private final String repository
    private final String protocol
    private final String host
    private final String branch

    private GitHubScm(String host, String repository, String branch, String protocol) {
        this.branch = branch
        this.host = host
        this.protocol = protocol
        this.repository = repository
    }

    static GitHubScm gitHubScm(String host, String repository, String branch, String protocol) {
        new GitHubScm(host, repository, branch, protocol)
    }

    @Override
    Closure toDsl() {
        return {
            git {
                remote {
                    github(repository, protocol, host)
                }
                branch(this.branch)
            }
        }
    }
}