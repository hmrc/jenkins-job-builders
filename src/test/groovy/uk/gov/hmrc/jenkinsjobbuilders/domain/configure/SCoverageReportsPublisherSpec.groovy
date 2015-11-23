package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static SCoverageReportsPublisher.sCoverageReportsPublisher

@Mixin(JobParents)
class SCoverageReportsPublisherSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(sCoverageReportsPublisher())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            publishers.'org.jenkinsci.plugins.scoverage.ScoveragePublisher'.reportDir.text() == 'target/scala-2.11/scoverage-report'
        }
    }
}
