package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class GatlingReportsPublisher implements Plugin {

    private GatlingReportsPublisher() {}

    Closure toDsl() {
        return {
            it / 'publishers' / 'io.gatling.jenkins.GatlingPublisher' {
                'enabled'('true')
            }
        }
    }

    static GatlingReportsPublisher gatlingReportPlugin() {
        new GatlingReportsPublisher()
    }
}