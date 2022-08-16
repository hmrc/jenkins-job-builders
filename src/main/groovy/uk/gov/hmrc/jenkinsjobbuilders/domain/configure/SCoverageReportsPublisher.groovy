package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class SCoverageReportsPublisher implements Configure {

    private final String scalaVersion

    private SCoverageReportsPublisher(final String scalaVersion) {
        this.scalaVersion = scalaVersion
    }

    Closure toDsl() {
        new BaseSCoverageReportsPublisher(
                "target/scala-${scalaVersion}/scoverage-report",
                'scoverage.xml'
        ).toDsl()
    }

    static SCoverageReportsPublisher sCoverageReportsPublisher() {
        new SCoverageReportsPublisher("2.11")
    }

    static SCoverageReportsPublisher sCoverageReportsPublisher(final String scalaVersion) {
        new SCoverageReportsPublisher(scalaVersion)
    }
}