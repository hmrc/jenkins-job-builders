package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class SCoverageReportsPublisher implements Configure {

    private final String scalaVersion

    private SCoverageReportsPublisher(final String scalaVersion) {
        this.scalaVersion = scalaVersion
    }

    Closure toDsl() {
        return {
            it / 'publishers' / 'org.jenkinsci.plugins.scoverage.ScoveragePublisher' {
                'reportDir'("target/scala-${scalaVersion}/scoverage-report")
                'reportFile'('scoverage.xml')
            }
        }
    }

    static SCoverageReportsPublisher sCoverageReportsPublisher() {
        new SCoverageReportsPublisher("2.11")
    }

    static SCoverageReportsPublisher sCoverageReportsPublisher(final String scalaVersion) {
        new SCoverageReportsPublisher(scalaVersion)
    }
}