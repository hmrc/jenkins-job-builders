package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

class PipelineJobBuilder implements Builder<Job> {
    private final String name

    PipelineJobBuilder(String name) {
        this.name = name
    }

    @Override
    Job build(DslFactory dslFactory) {
        return dslFactory.pipelineJob("$name-pipeline") {

            triggers {
                cron('*/2 * * * *')
            }

            definition {
                cpsScm {
                    scm {
                        github("hmrc/$name", 'master')
                    }
                    lightweight(false)
                }
            }
        }
    }
}
