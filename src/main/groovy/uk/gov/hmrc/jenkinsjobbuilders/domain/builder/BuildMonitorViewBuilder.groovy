package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.View


final class BuildMonitorViewBuilder implements Builder<View> {

    private final String name
    private String [] jobs
    private String includeRegex
    private Boolean recurseSubFolders = false

    BuildMonitorViewBuilder(String nameOrTeam, String name = '') {

        // nameOrTeam is there for backwards compatibility, where the name was the first parameter
        if(name == '') {
            this.name = nameOrTeam
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

    BuildMonitorViewBuilder recurseSubFolders() {
        this.recurseSubFolders = true
        this
    }

    @Override
    View build(DslFactory dslFactory) {
        dslFactory.buildMonitorView(this.name) {
            recurse(recurseSubFolders)
            jobs {
                names(this.jobs)
                if(includeRegex != null) {
                    regex(includeRegex)
                }
            }
            configure { node ->
                node / config {
                    displayCommitters 'false'
                }
            }
        }
    }
}
