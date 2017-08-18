package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.JobsTriggerStep.Threshold.FAILURE
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.JobsTriggerStep.Threshold.NEVER

final class JobsTriggerStep implements Configure {

    enum Threshold {
        /**
         * We don't actually use 'NEVER' but it
         * seemed confusing not to provide it as an option.
         */
        NEVER('', -1, '', true),
        SUCCESS('SUCCESS', 0, 'BLUE', true),
        UNSTABLE('UNSTABLE', 1, 'YELLOW', true),
        FAILURE('FAILURE', 2, 'RED', true)

        Threshold(String name,
                  int ordinal,
                  String color,
                  boolean completeBuild) {
            this.name = name
            this.ordinal = ordinal
            this.color = color
            this.completeBuild = completeBuild
        }

        private final String name
        private final int ordinal
        private final String color
        private final String completeBuild
    }

    private final List<String> projectsToBuild
    private final String filePattern
    private final String noFilesFoundAction
    private final Threshold buildStepFailureThreshold
    private final Threshold failureThreshold
    private final Threshold unstableThreshold

    private JobsTriggerStep(List<String> projectsToBuild,
                            String filePattern,
                            String noFilesFoundAction,
                            Threshold buildStepFailureThreshold,
                            Threshold failureThreshold,
                            Threshold unstableThreshold) {
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
                                     Threshold buildStepFailureThreshold = FAILURE,
                                     Threshold failureThreshold = FAILURE,
                                     Threshold unstableThreshold = FAILURE) {
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
                if(this?.buildStepFailureThreshold != NEVER) {
                    delegate.buildStepFailureThreshold {
                        name(this.buildStepFailureThreshold.name)
                        ordinal(this.buildStepFailureThreshold.ordinal)
                        color(this.buildStepFailureThreshold.color)
                        completeBuild(this.buildStepFailureThreshold.completeBuild)
                    }
                }
                if(this?.failureThreshold != NEVER) {
                    delegate.failureThreshold {
                        name(this.failureThreshold.name)
                        ordinal(this.failureThreshold.ordinal)
                        color(this.failureThreshold.color)
                        completeBuild(this.failureThreshold.completeBuild)
                    }
                }
                if(this?.unstableThreshold != NEVER) {
                    delegate.unstableThreshold {
                        name(this.unstableThreshold.name)
                        ordinal(this.unstableThreshold.ordinal)
                        color(this.unstableThreshold.color)
                        completeBuild(this.unstableThreshold.completeBuild)
                    }
                }
            }
        }
    }
}
