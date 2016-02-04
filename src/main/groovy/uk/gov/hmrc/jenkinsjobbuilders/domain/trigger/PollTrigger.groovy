package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger


final class PollTrigger implements Trigger {
    private final String pollPattern

    private PollTrigger(String pollPattern) {
        this.pollPattern = pollPattern
    }

    static PollTrigger pollTrigger(String pollPattern) {
        new PollTrigger(pollPattern)
    }

    @Override
    Closure toDsl() {
        return {
            scm(pollPattern)
        }
    }
}