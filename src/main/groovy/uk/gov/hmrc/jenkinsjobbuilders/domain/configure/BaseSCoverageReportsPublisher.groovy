package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

/**
 * Allows fine-grained control of setting the location of the generated coverage report
 * to be published.
 *
 * Since version 1.9.0 of `sbt-scoverage`, reports may be configured to be written to
 * arbitrary locations (which, as such, no longer imposes a `scalac` version specific
 * directory structure.)
 */
final class BaseSCoverageReportsPublisher implements Configure {

    private final String coverageReportDir
    private final String coverageReportFile

    BaseSCoverageReportsPublisher(String coverageReportDir, String coverageReportFile) {
        this.coverageReportDir = coverageReportDir
        this.coverageReportFile = coverageReportFile
    }

    @Override
    Closure toDsl() {
        return {
            it / 'publishers' / 'org.jenkinsci.plugins.scoverage.ScoveragePublisher' {
                'reportDir'(coverageReportDir)
                'reportFile'(coverageReportFile)
            }
        }
    }

    static final BaseSCoverageReportsPublisher versionAgnosticSCoverageReportsPublisher() {
        new BaseSCoverageReportsPublisher(
                "target/scoverage-report",
                "scoverage.xml"
        )
    }
}
