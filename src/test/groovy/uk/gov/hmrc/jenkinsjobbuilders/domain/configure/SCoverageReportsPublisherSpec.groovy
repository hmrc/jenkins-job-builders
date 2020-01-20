package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.DslScriptException
import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder


import static SCoverageReportsPublisher.sCoverageReportsPublisher

class SCoverageReportsPublisherSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(sCoverageReportsPublisher())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            publishers.'org.jenkinsci.plugins.scoverage.ScoveragePublisher'.reportDir.text() == 'target/scala-2.11/scoverage-report'
        }
    }

    void 'test XML output with scala-2.12'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withConfigures(sCoverageReportsPublisher("2.12"))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            publishers.'org.jenkinsci.plugins.scoverage.ScoveragePublisher'.reportDir.text() == 'target/scala-2.12/scoverage-report'
        }
    }

    void 'test invalid scala version' () {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')

        when:
        jobBuilder.withConfigures(sCoverageReportsPublisher("2.09"))

        then:
        DslScriptException e = thrown()
        e.message.contains("Could not use the Scala version 2.09. The only supported Scala versions are 2.10,2.11,2.12")
    }

}
