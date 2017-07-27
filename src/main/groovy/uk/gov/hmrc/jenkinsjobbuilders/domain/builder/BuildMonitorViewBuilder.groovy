package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.View
import javaposse.jobdsl.dsl.views.BuildMonitorView

final class BuildMonitorViewBuilder implements Builder<View> {

    private final String name
    private String [] jobs

    BuildMonitorViewBuilder(String name) {
        this.name = name
    }

    BuildMonitorViewBuilder withJobs(String ... jobs) {
        this.jobs = jobs
        this
    }

    @Override
    View build(DslFactory dslFactory) {
        dslFactory.view(type: BuildMonitorView) {
            name(this.name)
            jobs {
                names(this.jobs)
            }
        }
    }
}
