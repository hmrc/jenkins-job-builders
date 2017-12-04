package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class JobsTriggerStep implements Configure {
    private final List<String> projectsToBuild
    private final String filePattern
    private final String noFilesFoundAction
    private final String buildStepFailureThreshold
    private final String failureThreshold
    private final String unstableThreshold

    private JobsTriggerStep(List<String> projectsToBuild,
                            String filePattern,
                            String noFilesFoundAction,
                            String buildStepFailureThreshold,
                            String failureThreshold,
                            String unstableThreshold) {
        this.projectsToBuild = projectsToBuild
        this.filePattern = filePattern
        this.noFilesFoundAction = noFilesFoundAction
        this.buildStepFailureThreshold = buildStepFailureThreshold
        this.failureThreshold = failureThreshold
        this.unstableThreshold = unstableThreshold
    }

    static Configure jobsTriggerStep(List<String> projectsToBuild,
                                String filePattern,
                                String noFilesFoundAction = 'SKIP',
                                String buildStepFailureThreshold = 'FAILURE',
                                String failureThreshold = 'FAILURE',
                                String unstableThreshold = 'UNSTABLE') {
        new JobsTriggerStep(projectsToBuild, filePattern, noFilesFoundAction,
                buildStepFailureThreshold, failureThreshold, unstableThreshold)
    }

    @Override
    Closure toDsl() {
        return {
            def triggerConfig = it / builders /
                    'hudson.plugins.parameterizedtrigger.TriggerBuilder' /
                    configs /
                    'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig'
            triggerConfig / projects(this.projectsToBuild.join(','))
            if (this.filePattern) {
                triggerConfig / configFactories /
                        'hudson.plugins.parameterizedtrigger.FileBuildParameterFactory' {
                            filePattern(this.filePattern)
                            noFilesFoundAction(this.noFilesFoundAction)
                        }
            }
            triggerConfig / block {
                buildStepFailureThreshold { name(this.buildStepFailureThreshold) }
                failureThreshold { name(this.failureThreshold) }
                unstableThreshold { name(this.unstableThreshold) }
            }
        }
    }
}
