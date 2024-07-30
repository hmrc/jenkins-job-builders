package uk.gov.hmrc.jenkinsjobbuilders.domain.scm

final class GitHubScm implements Scm {
    private final String repository
    private final String protocol
    private final String host
    private final String branch
    private final String refspec
    private final String credentials
    private final String name
    private final int depth
    private final boolean honorRefspec
    private final boolean pullTags
    private final int timeout

    private GitHubScm(String host, String repository, String branch, String protocol, String refspec, String credentials, String name, int depth, boolean honorRefspec, boolean pullTags, int timeout = 0) {
        this.branch = branch
        this.host = host
        this.protocol = protocol
        this.repository = repository
        this.refspec = refspec
        this.credentials = credentials
        this.name = name
        this.depth = depth
        this.honorRefspec = honorRefspec
        this.pullTags = pullTags
        this.timeout = timeout

    }

    static GitHubScm gitHubScm(String host, String repository, String branch, String protocol) {
        gitHubScm(host, repository, branch, protocol, null, null, null)
    }

    static GitHubScm gitHubScm(String host, String repository, String branch, String protocol, String refspec, String credentials, String name = null, int depth = 0, boolean honorRefspec = false, boolean pullTags = true) {
        new GitHubScm(host, repository, branch, protocol, refspec, credentials, name, depth, honorRefspec, pullTags)
    }

    static GitHubScm gitHubScmWithTimeout(String host, String repository, String branch, String protocol, String refspec, String credentials, String name = null, int depth = 0, boolean honorRefspec = false, boolean pullTags = true, int timeout) {
        new GitHubScm(host, repository, branch, protocol, refspec, credentials, name, depth, honorRefspec, pullTags, timeout)
    }

    @Override
    Closure toDsl() {
        return {
            git {
                remote {
                    github(repository, protocol, host)
                    if (this.refspec != null) {
                        refspec(this.refspec)
                    }
                    if (this.credentials != null) {
                        credentials(this.credentials)
                    }
                    if (this.name != null) {
                        name(this.name)
                    }
                    extensions {
                        cloneOptions {
                            honorRefspec(this.honorRefspec)
                            noTags(!this.pullTags)
                            if (this.depth > 0) {
                                depth(this.depth)
                                shallow(true)
                            }
                            if (this.timeout != 0) {
                                timeout(this.timeout)
                            }
                        }
                    }
                }
                branch(this.branch)
            }
        }
    }
}
