package uk.gov.hmrc.jenkinsjobbuilders.domain

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.Plugin
import uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.Publisher
import uk.gov.hmrc.jenkinsjobbuilders.domain.scm.Scm
import uk.gov.hmrc.jenkinsjobbuilders.domain.scm.ScmTrigger
import uk.gov.hmrc.jenkinsjobbuilders.domain.step.Step

import static java.util.Arrays.asList
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.ClaimBrokenBuildsPublisher.claimBrokenBuildsPublisher

final class JobBuilder implements Builder<Job> {
    private final String name
    private final String description
    private final int daysToKeep
    private final int numToKeep
    private final List<ScmTrigger> scmTriggers = new ArrayList()
    private final List<Step> steps = new ArrayList()
    private final List<Publisher> publishers = new ArrayList(asList(claimBrokenBuildsPublisher()))
    private final List<Plugin> plugins = new ArrayList()
    private Scm scm
    private String labelExpression
    private Parameters params

    JobBuilder(String name, String description, int daysToKeep, int numToKeep) {
        this.name = name
        this.description = description
        this.daysToKeep = daysToKeep
        this.numToKeep = numToKeep
    }

    JobBuilder withScm(Scm scm) {
        this.scm = scm
        this
    }

    JobBuilder withScmTriggers(ScmTrigger ... scmTriggers) {
        this.scmTriggers.addAll(scmTriggers)
        this
    }

    JobBuilder withSteps(Step ... steps) {
        this.steps.addAll(steps)
        this
    }

    JobBuilder withParameters(Parameters parameters) {
        this.params = parameters
        this
    }

    JobBuilder withLabel(String labelExpression) {
        this.labelExpression = labelExpression
        this
    }

    JobBuilder withPublishers(Publisher ... publishers) {
        withPublishers(asList(publishers))
    }

    JobBuilder withPublishers(List<Publisher> publishers) {
        this.publishers.addAll(publishers)
        this
    }

    JobBuilder withPlugins(Plugin ... plugins) {
        withPlugins(asList(plugins))
    }

    JobBuilder withPlugins(List<Plugin> plugins) {
        this.plugins.addAll(plugins)
        this
    }

    @Override
    Job build(DslFactory dslFactory) {
        dslFactory.job {
            it.name this.name
            it.description this.description
            logRotator(daysToKeep, numToKeep)

            if (this.params != null) {
                parameters(this.params.toDsl())
            }

            if (labelExpression != null) {
                label labelExpression
            }

            if (scm != null) {
                scm(scm.toDsl())
            }

            this.scmTriggers.each { scmTrigger ->
                triggers(scmTrigger.toDsl())
            }

            wrappers {
                colorizeOutput()
                preBuildCleanup()
            }

            this.steps.each { step ->
                steps(step.toDsl())
            }

            this.publishers.each { publisher ->
                publishers(publisher.toDsl())
            }

            this.plugins.each { plugin ->
                configure(plugin.toDsl())
            }
        }
    }
}