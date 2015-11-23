package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static XvfbBuildWrapper.parallelXvfbBuildPlugin
import static XvfbBuildWrapper.xvfbBuildPlugin

@Mixin(JobParents)
class XvfbBuildWrapperSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPlugins(xvfbBuildPlugin())

        when:
        Job job = jobBuilder.build(jobParent())

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
                                               withPlugins(parallelXvfbBuildPlugin())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.parallelBuild.text() == 'true'
        }
    }
}
