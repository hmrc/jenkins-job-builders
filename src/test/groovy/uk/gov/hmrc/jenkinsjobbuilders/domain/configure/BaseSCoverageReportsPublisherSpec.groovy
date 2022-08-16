package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

class BaseSCoverageReportsPublisherSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        def coverageReportDir = "target/scoverage-report"
        def coverageReportFile = "scoverage.xml"
        def jobBuilder =
                new JobBuilder('test-job', 'test-job-description')
                        .withConfigures(new BaseSCoverageReportsPublisher(coverageReportDir, coverageReportFile))

        when:
        def job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            publishers.'org.jenkinsci.plugins.scoverage.ScoveragePublisher'.reportDir.text() == 'target/scoverage-report'
            publishers.'org.jenkinsci.plugins.scoverage.ScoveragePublisher'.reportFile.text() == 'scoverage.xml'
        }
    }
}
