package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.View
import javaposse.jobdsl.dsl.views.ListView

final class ListViewBuilder implements Builder<View> {

    private final String name
    private String jobsRegex
    private boolean scoverageReports = false
    private Boolean recurseSubFolders = false

    ListViewBuilder(String name) {
        this.name = name
    }

    ListViewBuilder withJobsRegex(String jobsRegex) {
        this.jobsRegex = jobsRegex
        this
    }

    ListViewBuilder withRecurseSubFolders() {
        this.recurseSubFolders = true
        this
    }

    @Override
    View build(DslFactory dslFactory) {
        dslFactory.listView(this.name) {
            recurse(this.recurseSubFolders)
            jobs {
                regex(this.jobsRegex)
            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}
