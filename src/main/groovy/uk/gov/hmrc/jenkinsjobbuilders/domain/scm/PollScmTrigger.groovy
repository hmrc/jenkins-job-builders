package uk.gov.hmrc.jenkinsjobbuilders.domain.scm


final class PollScmTrigger implements ScmTrigger {
    private final String pollPattern

    private PollScmTrigger(String pollPattern) {
        this.pollPattern = pollPattern
    }

    static PollScmTrigger pollScmTrigger(String pollPattern) {
        new PollScmTrigger(pollPattern)
    }

    @Override
    Closure toDsl() {
        return {
            scm(pollPattern)
        }
    }
}