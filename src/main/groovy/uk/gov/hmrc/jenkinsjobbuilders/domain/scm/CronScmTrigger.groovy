package uk.gov.hmrc.jenkinsjobbuilders.domain.scm


final class CronScmTrigger implements ScmTrigger {
    private final String cronPattern

    private CronScmTrigger(String cronPattern) {
        this.cronPattern = cronPattern
    }

    static CronScmTrigger cronScmTrigger(String cronPattern) {
        new CronScmTrigger(cronPattern)
    }

    @Override
    Closure toDsl() {
        return {
            cron(cronPattern)
        }
    }
}