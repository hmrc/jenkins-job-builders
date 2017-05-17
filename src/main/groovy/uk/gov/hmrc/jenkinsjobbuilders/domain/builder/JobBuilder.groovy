package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission
import uk.gov.hmrc.jenkinsjobbuilders.domain.configure.Configure
import uk.gov.hmrc.jenkinsjobbuilders.domain.parameters.Parameter
import uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.Publisher
import uk.gov.hmrc.jenkinsjobbuilders.domain.scm.Scm
import uk.gov.hmrc.jenkinsjobbuilders.domain.throttle.ThrottleConfiguration
import uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.Trigger
import uk.gov.hmrc.jenkinsjobbuilders.domain.step.Step
import uk.gov.hmrc.jenkinsjobbuilders.domain.variable.EnvironmentVariable
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.Wrapper

import static java.util.Arrays.asList
import static uk.gov.hmrc.jenkinsjobbuilders.domain.throttle.ThrottleConfiguration.throttleConfiguration
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.EnvironmentVariablesWrapper.environmentVariablesWrapper

final class JobBuilder implements Builder<Job> {
    private final String name
    private final String description
    private final List<Parameter> parameters = []
    private final List<EnvironmentVariable> environmentVariables = []
    private final List<Trigger> triggers = []
    private final List<Step> steps = []
    private final List<Publisher> publishers = []
    private final List<Configure> configures = []
    private final List<Wrapper> wrappers = []
    private Scm scm
    private int daysToKeep = -1
    private int numToKeep = -1
    private String labelExpression
    private String environmentVariablesFile
    private boolean concurrentBuilds = false
    private boolean disabled = false
    private final List<Permission> permissions = []
    private ThrottleConfiguration throttle

    JobBuilder(String name, String description) {
        this.name = name
        this.description = description
    }

    JobBuilder withLogRotator(int daysToKeep, int numToKeep) {
        this.daysToKeep = daysToKeep
        this.numToKeep = numToKeep
        this
    }

    JobBuilder withScm(Scm scm) {
        this.scm = scm
        this
    }

    JobBuilder withTriggers(Trigger ... triggers) {
        this.triggers.addAll(triggers)
        this
    }

    JobBuilder withConcurrentBuilds() {
        this.concurrentBuilds = true
        this
    }

    JobBuilder withDisabled() {
        this.disabled = true
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

    JobBuilder withEnvironmentVariablesFile(String environmentVariablesFile) {
        this.environmentVariablesFile = environmentVariablesFile
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

    JobBuilder withConfigures(Configure ... configures) {
        withConfigures(asList(configures))
    }

    JobBuilder withConfigures(List<Configure> configures) {
        this.configures.addAll(configures)
        this
    }

    JobBuilder withWrappers(Wrapper ... wrappers) {
        withWrappers(asList(wrappers))
    }

    JobBuilder withWrappers(List<Wrapper> wrappers) {
        this.wrappers.addAll(wrappers)
        this
    }

    JobBuilder withPermissions(Permission ... permissions) {
        this.permissions.addAll(permissions)
        this
    }

    JobBuilder withPermissions(List<Permission> permissions) {
        this.permissions.addAll(permissions)
        this
    }

    JobBuilder withThrottle(List<String> categories, int maxConcurrentPerNode, int maxConcurrentTotal, boolean throttleDisabled) {
        this.throttle = throttleConfiguration(categories, maxConcurrentPerNode, maxConcurrentTotal, throttleDisabled)
        this
    }

    @Override
    Job build(DslFactory dslFactory) {
        if (!this.environmentVariables.isEmpty()) {
            this.wrappers.add(0, environmentVariablesWrapper(environmentVariablesFile, environmentVariables))
        }

        dslFactory.job {
            it.name this.name
            it.description this.description
            logRotator(daysToKeep, numToKeep)
            concurrentBuild(concurrentBuilds)
            disabled(this.disabled)

            this.parameters.each {
                parameters(it.toDsl())
            }

            if (labelExpression != null) {
                label labelExpression
            }

            if (scm != null) {
                scm(scm.toDsl())
            }

            this.triggers.each {
                triggers(it.toDsl())
            }

            this.wrappers.each {
                wrappers(it.toDsl())
            }

            this.steps.each {
                steps(it.toDsl())
            }

            this.publishers.each {
                publishers(it.toDsl())
            }

            this.configures.each {
                configure(it.toDsl())
            }
            this.permissions.each {
                permission(it.permissionEnum, it.ldapIdentifier)
            }
            if (this.throttle != null) {
                throttleConcurrentBuilds(this.throttle.toDsl())
            }
        }
    }
}