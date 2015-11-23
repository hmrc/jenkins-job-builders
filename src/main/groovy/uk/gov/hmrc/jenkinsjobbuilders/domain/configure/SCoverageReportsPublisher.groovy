package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class SCoverageReportsPublisher implements Plugin {

    private SCoverageReportsPublisher() {}

    Closure toDsl() {
        return {
            it / 'publishers' / 'org.jenkinsci.plugins.scoverage.ScoveragePublisher' {
                'reportDir'('target/scala-2.11/scoverage-report')
                'reportFile'('scoverage.xml')
            }
        }
    }

    static SCoverageReportsPublisher sCoverageReportPlugin() {
        new SCoverageReportsPublisher()
    }
}