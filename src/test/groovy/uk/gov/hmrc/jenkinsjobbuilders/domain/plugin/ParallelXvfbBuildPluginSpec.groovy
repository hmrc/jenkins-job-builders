package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.ParallelXvfbBuildPlugin.xvfbBuildPlugin

@Mixin(JobParents)
class ParallelXvfbBuildPluginSpec extends Specification {

    void 'test parallel Xvfb plugin'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPlugins(xvfbBuildPlugin())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        job.name == 'test-job'

        with(job.node) {
            name() == 'project'
            description.text() == 'test-job-description'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.switch.text() == 'on'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.parallelBuild.text() == 'true'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.displayName.isEmpty()
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.displayNameOffset.text() == '1'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.screen.text() == '1920x1080x24'
        }
    }
}
