package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.View

import static javaposse.jobdsl.dsl.ViewType.ListView

final class ListViewBuilder implements Builder<View> {

    private final String name
    private String jobsRegex
    private boolean scoverageReports = false

    ListViewBuilder(String name) {
        this.name = name
    }

    ListViewBuilder withJobsRegex(String jobsRegex) {
        this.jobsRegex = jobsRegex
        this
    }

    @Override
    View build(DslFactory dslFactory) {
        dslFactory.view(type: ListView) {
            name(this.name)
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
