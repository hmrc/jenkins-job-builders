package uk.gov.hmrc.jenkinsjobbuilders.domain

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.Plugin
import uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.Publisher
import uk.gov.hmrc.jenkinsjobbuilders.domain.scm.Scm
import uk.gov.hmrc.jenkinsjobbuilders.domain.scm.ScmTrigger

import static java.util.Arrays.asList
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.ClaimBrokenBuildsPublisher.claimBrokenBuildsPublisher

final class JobBuilder implements Builder<Job> {
    private final String name
    private final Scm scm
    private final String description
    private final int daysToKeep
    private final int numToKeep
    private final List<ScmTrigger> scmTriggers = new ArrayList()
    private final List<String> shellCommands = new ArrayList()
    private final List<Publisher> publishers = new ArrayList(asList(claimBrokenBuildsPublisher()))
    private final List<Plugin> plugins = new ArrayList()
    private String labelExpression
    private Parameters params

    JobBuilder(String name, String description, int daysToKeep, int numToKeep, Scm scm) {
        this.name = name
        this.description = description
        this.daysToKeep = daysToKeep
        this.numToKeep = numToKeep
        this.scm = scm
    }

    JobBuilder withScmTriggers(ScmTrigger ... scmTriggers) {
        this.scmTriggers.addAll(scmTriggers)
        this
    }

    JobBuilder withShellCommands(String ... shellCommands) {
        this.shellCommands.addAll(asList(shellCommands))
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

            scm(scm.toDsl())

            this.scmTriggers.each { scmTrigger ->
                triggers(scmTrigger.toDsl())
            }

            wrappers {
                colorizeOutput()
                preBuildCleanup()
            }

            if (!shellCommands.isEmpty()) {
                steps {
                    shellCommands.each { shellCommand ->
                        shell(shellCommand)
                    }
                }
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