package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.View
import javaposse.jobdsl.dsl.views.BuildMonitorView
import javaposse.jobdsl.dsl.views.jobfilter.MatchType
import javaposse.jobdsl.dsl.views.jobfilter.RegexMatchValue


final class BuildMonitorViewBuilder implements Builder<View> {

    private final String name
    private String [] jobs
    private String includeRegex

    BuildMonitorViewBuilder(String nameOrTeam, String name = '') {

        // nameOrTeam is there for backwards compatibility, where the name was the first parameter
        if(name == '') {
            this.name = name
        } else {
            this.name = "$nameOrTeam/$name"
        }
    }

    BuildMonitorViewBuilder withJobs(String ... jobs) {
        this.jobs = jobs
        this
    }

    BuildMonitorViewBuilder withJobsMatching(String includeRegex) {
        this.includeRegex = includeRegex
        this
    }


    @Override
    View build(DslFactory dslFactory) {
        dslFactory.buildMonitorView(this.name) {
            jobs {
                names(this.jobs)
                if(includeRegex != null) {
                    regex(includeRegex)
                }
            }
        }
    }
}
