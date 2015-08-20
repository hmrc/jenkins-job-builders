package uk.gov.hmrc.jenkinsjobbuilders.domain

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.parameters.Parameter
import uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.Plugin
import uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.Publisher
import uk.gov.hmrc.jenkinsjobbuilders.domain.scm.Scm
import uk.gov.hmrc.jenkinsjobbuilders.domain.scm.ScmTrigger
import uk.gov.hmrc.jenkinsjobbuilders.domain.step.Step
import uk.gov.hmrc.jenkinsjobbuilders.domain.variables.EnvironmentVariable
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.Wrapper

import static java.util.Arrays.asList
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.ClaimBrokenBuildsPublisher.claimBrokenBuildsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.ColorizeOutputWrapper.colorizeOutputWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.EnvironmentVariablesWrapper.environmentVariablesWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.PreBuildCleanupWrapper.preBuildCleanUpWrapper

final class JobBuilder implements Builder<Job> {
    private final String name
    private final String description
    private final int daysToKeep
    private final int numToKeep
    private final List<Parameter> parameters = new ArrayList()
    private final List<EnvironmentVariable> environmentVariables = new ArrayList()
    private final List<ScmTrigger> scmTriggers = new ArrayList()
    private final List<Step> steps = new ArrayList()
    private final List<Publisher> publishers = new ArrayList(asList(claimBrokenBuildsPublisher()))
    private final List<Plugin> plugins = new ArrayList()
    private final List<Wrapper> wrappers = new ArrayList(asList(colorizeOutputWrapper(), preBuildCleanUpWrapper()))
    private Scm scm
    private String labelExpression

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
        withSteps(asList(steps))
    }

    JobBuilder withSteps(List<Step> steps) {
        this.steps.addAll(steps)
        this
    }

    JobBuilder withParameters(Parameter ... parameters) {
        withParameters(asList(parameters))
    }

    JobBuilder withParameters(List<Parameter> parameters) {
        this.parameters.addAll(parameters)
        this
    }

    JobBuilder withEnvironmentVariables(EnvironmentVariable ... environmentsVariables) {
        withEnvironmentVariables(asList(environmentsVariables))
    }

    JobBuilder withEnvironmentVariables(List<EnvironmentVariable> environmentVariables) {
        this.environmentVariables.addAll(environmentVariables)
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

    JobBuilder withWrappers(Wrapper ... wrappers) {
        withWrappers(asList(wrappers))
    }

    JobBuilder withWrappers(List<Wrapper> wrappers) {
        this.wrappers.addAll(wrappers)
        this
    }

    @Override
    Job build(DslFactory dslFactory) {
        if (!this.environmentVariables.isEmpty()) {
            this.wrappers.add(environmentVariablesWrapper(environmentVariables))
        }

        dslFactory.job {
            it.name this.name
            it.description this.description
            logRotator(daysToKeep, numToKeep)

            this.parameters.each { parameter ->
                parameters(parameter.toDsl())
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

            this.wrappers.each { wrapper ->
                wrappers(wrapper.toDsl())
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