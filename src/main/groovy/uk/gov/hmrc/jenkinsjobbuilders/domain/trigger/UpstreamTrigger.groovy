package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger

import uk.gov.hmrc.jenkinsjobbuilders.domain.Condition

class UpstreamTrigger implements Trigger {
    String upstreamJobs
    Condition condition

    private UpstreamTrigger(String upstreamJobs, Condition condition) {
        this.upstreamJobs = upstreamJobs
        this.condition = condition
    }

    /**
     * Creates a {@link jenkins.triggers.ReverseBuildTrigger} based on the successful completion of any of a list of upstream jobs.
     * @param upstreamJobs String with a comma separated list of jobs
     * @param {@link uk.gov.hmrc.jenkinsjobbuilders.domain.Condition} one of SUCCESS, UNSTABLE, FAILURE, defaults to SUCCESS
     * @return UpstreamTrigger
     */
    static UpstreamTrigger upstreamTrigger(String upstreamJobs, Condition condition = Condition.SUCCESS) {
        new UpstreamTrigger(upstreamJobs, condition)
    }

    @Override
    Closure toDsl() {
        return {
            upstream(upstreamJobs, condition.name())
        }
    }
}
