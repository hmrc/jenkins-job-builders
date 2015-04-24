package uk.gov.hmrc.jenkinsjobbuilders.domain.scm

import static GitHubScm.gitHubScm

final class EnterpriseGitHubScm implements Scm {
    private final Scm scm

    private EnterpriseGitHubScm(String repository, String branch) {
        this.scm = gitHubScm('github.tools.tax.service.gov.uk', repository, branch, 'ssh')
    }

    static EnterpriseGitHubScm enterpriseGitHubScm(String repository, String branch = 'master') {
        new EnterpriseGitHubScm(repository, branch)
    }

    @Override
    Closure toDsl() {
        scm.toDsl()
    }
}