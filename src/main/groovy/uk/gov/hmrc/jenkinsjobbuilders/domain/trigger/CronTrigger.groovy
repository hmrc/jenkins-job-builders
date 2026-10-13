package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger
import hudson.scheduler.CronTabList


final class CronTrigger implements Trigger {
    private final String cronPattern

    private CronTrigger(String cronPattern) {
        this.cronPattern = cronPattern
    }

    static CronTrigger cronTrigger(String cronPattern) {
        validate(cronPattern)
        new CronTrigger(cronPattern)
    }

    @Override
    Closure toDsl() {
        return {
            cron(cronPattern)
        }
    }

    private static void validate(String cronPattern) {
        try {
            CronTabList.create(cronPattern)
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid Jenkins cron spec '${cronPattern}': ${e.message}.",
                e
            )
        }
    }
}
