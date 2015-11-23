package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class GatlingReportsPublisher implements Configure {

    private GatlingReportsPublisher() {}

    Closure toDsl() {
        return {
            it / 'publishers' / 'io.gatling.jenkins.GatlingPublisher' {
                'enabled'('true')
            }
        }
    }

    static GatlingReportsPublisher gatlingReportsPublisher() {
        new GatlingReportsPublisher()
    }
}