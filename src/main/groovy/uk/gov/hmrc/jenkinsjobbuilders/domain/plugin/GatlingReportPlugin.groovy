package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

final class GatlingReportPlugin implements Plugin {

    private GatlingReportPlugin() {}

    Closure toDsl() {
        return {
            it / 'publishers' / 'io.gatling.jenkins.GatlingPublisher' {
                'enabled'('true')
            }
        }
    }

    static GatlingReportPlugin gatlingReportPlugin() {
        new GatlingReportPlugin()
    }
}