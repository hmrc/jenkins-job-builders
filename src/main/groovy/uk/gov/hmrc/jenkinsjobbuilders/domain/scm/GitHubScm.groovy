package uk.gov.hmrc.jenkinsjobbuilders.domain.scm

final class GitHubScm implements Scm {
    private final String repository
    private final String protocol
    private final String host
    private final String branch
    private final String refspec
    private final String credentials

    private GitHubScm(String host, String repository, String branch, String protocol, String refspec, String credentials) {
        this.branch = branch
        this.host = host
        this.protocol = protocol
        this.repository = repository
        this.refspec = refspec
        this.credentials = credentials
    }

    static GitHubScm gitHubScm(String host, String repository, String branch, String protocol) {
        gitHubScm(host, repository, branch, protocol, null, null)
    }

    static GitHubScm gitHubScm(String host, String repository, String branch, String protocol, String refspec, String credentials) {
        new GitHubScm(host, repository, branch, protocol, refspec, credentials)
    }

    @Override
    Closure toDsl() {
        return {
            git {
                remote {
                    github(repository, protocol, host)
                }
                if (this.refspec != null) {
                    refspec(this.refspec)
                }
                if (this.credentials != null) {
                    credentials(this.credentials)
                }

                branch(this.branch)
            }
        }
    }
}