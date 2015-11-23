package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class SCoverageReportsPublisher implements Configure {

    private SCoverageReportsPublisher() {}

    Closure toDsl() {
        return {
            it / 'publishers' / 'org.jenkinsci.plugins.scoverage.ScoveragePublisher' {
                'reportDir'('target/scala-2.11/scoverage-report')
                'reportFile'('scoverage.xml')
            }
        }
    }

    static SCoverageReportsPublisher sCoverageReportsPublisher() {
        new SCoverageReportsPublisher()
    }
}