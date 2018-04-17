package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder


import static XvfbBuildWrapper.parallelXvfbBuildWrapper
import static XvfbBuildWrapper.xvfbBuildWrapper

class XvfbBuildWrapperSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(xvfbBuildWrapper())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.switch.text() == 'on'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.parallelBuild.text() == 'false'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.displayName.isEmpty()
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.displayNameOffset.text() == '1'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.screen.text() == '1920x1080x24'
        }
    }

    void 'test parallel XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(parallelXvfbBuildWrapper())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.parallelBuild.text() == 'true'
        }
    }
}
