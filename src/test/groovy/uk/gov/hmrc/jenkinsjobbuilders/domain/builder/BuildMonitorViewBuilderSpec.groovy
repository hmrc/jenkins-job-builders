package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.View
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec

class BuildMonitorViewBuilderSpec extends AbstractJobSpec {

    def 'it should create a view with appropriate configuration'() {
        given:
        final BuildMonitorViewBuilder buildMonitorViewBuilder = new BuildMonitorViewBuilder("foo-team-name", "foo-view-name")
                .withJobs("foo-job-1-name", "foo-job-2-name")

        when:
        View buildMonitorView = buildMonitorViewBuilder.build(JOB_PARENT)

        then:
        with(buildMonitorView.node) {
            "false" == it.recurse.text()
             it.jobNames[0].text().contains("foo-job-1-name")
             it.jobNames[0].text().contains("foo-job-2-name")
        }
    }
}
