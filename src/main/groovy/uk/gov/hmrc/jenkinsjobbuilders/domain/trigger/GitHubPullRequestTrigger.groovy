package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger


final class GitHubPullRequestTrigger implements Trigger {
    private final String orgWhitelist 
    private final String checkName 

    private GitHubPullRequestTrigger(String checkName, String orgWhitelist) {
        this.orgWhitelist = orgWhitelist
        this.checkName = checkName
    }

    static GitHubPullRequestTrigger gitHubPullRequestTrigger(String checkName, String orgWhitelist) {
        new GitHubPullRequestTrigger(checkName, orgWhitelist)
    }

    @Override
    Closure toDsl() {
        return {
            githubPullRequest {
                admin("")
                cron("")
                orgWhitelist(this.orgWhitelist)
                useGitHubHooks()
                extensions {
                    commitStatus {
                        context(this.checkName) 
                    }
                }
            }
        }
    }
}
