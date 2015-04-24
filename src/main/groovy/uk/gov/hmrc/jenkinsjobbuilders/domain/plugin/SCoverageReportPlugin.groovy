package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

final class SCoverageReportPlugin implements Plugin {

    private SCoverageReportPlugin() {}

    Closure toDsl() {
        return {
            it / 'publishers' / 'org.jenkinsci.plugins.scoverage.ScoveragePublisher' {
                'reportDir'('target/scala-2.11/scoverage-report')
                'reportFile'('scoverage.xml')
            }
        }
    }

    static SCoverageReportPlugin sCoverageReportPlugin() {
        new SCoverageReportPlugin()
    }
}