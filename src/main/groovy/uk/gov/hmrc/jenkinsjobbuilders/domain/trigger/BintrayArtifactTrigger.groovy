package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger;

public class BintrayArtifactTrigger implements Trigger {
    private final String cronSchedule
    private final String subject
    private final String repo
    private final List<String> packagesToPoll

    private BintrayArtifactTrigger(String cronSchedule, String subject, String repo, List<String> packagesToPoll) {
        this.cronSchedule = cronSchedule
        this.subject = subject
        this.repo = repo
        this.packagesToPoll = packagesToPoll
    }

    static BintrayArtifactTrigger bintrayArtifactTrigger(String cron, String subject, String repo, List<String> packagesToPoll) {
        new BintrayArtifactTrigger(cron, subject, repo, packagesToPoll)
    }

    @Override
    Closure toDsl() {
        return {
            urlTrigger{
                cron(cronSchedule)

                packagesToPoll.each{
                    url(String.format("https://api.bintray.com/packages/%s/%s/%s/", subject, repo, it)) {
                        inspection('json'){
                            path('latest_version')
                        }
                    }
                }
            }
        }
    }
}
