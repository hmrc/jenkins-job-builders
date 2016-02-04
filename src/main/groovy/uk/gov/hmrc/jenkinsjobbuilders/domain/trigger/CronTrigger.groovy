package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger


final class CronTrigger implements Trigger {
    private final String cronPattern

    private CronTrigger(String cronPattern) {
        this.cronPattern = cronPattern
    }

    static CronTrigger cronTrigger(String cronPattern) {
        new CronTrigger(cronPattern)
    }

    @Override
    Closure toDsl() {
        return {
            cron(cronPattern)
        }
    }
}