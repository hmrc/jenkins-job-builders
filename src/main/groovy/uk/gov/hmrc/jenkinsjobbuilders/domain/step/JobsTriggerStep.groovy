package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import static java.util.Collections.emptyMap

final class JobsTriggerStep implements Step {
    private final def name
    private final def parameters = new HashMap()
    private final String filePattern
    private final String noFilesFoundAction
    private final String buildStepFailureThreshold
    private final String failureThreshold
    private final String unstableThreshold

    private JobsTriggerStep(String name,
                            Map<String, String> parameters,
                            String filePattern,
                            String noFilesFoundAction,
                            String buildStepFailureThreshold,
                            String failureThreshold,
                            String unstableThreshold) {
        this.name = name
        this.parameters.putAll(parameters)
        this.filePattern = filePattern
        this.noFilesFoundAction = noFilesFoundAction
        this.buildStepFailureThreshold = buildStepFailureThreshold
        this.failureThreshold = failureThreshold
        this.unstableThreshold = unstableThreshold
    }

    static Step jobsTriggerStep(String name,
                                Map<String, String> parameters = emptyMap(),
                                String filePattern,
                                String noFilesFoundAction = 'SKIP',
                                String buildStepFailureThreshold = 'FAILURE',
                                String failureThreshold = 'FAILURE',
                                String unstableThreshold = 'UNSTABLE') {
        new JobsTriggerStep(name, parameters, filePattern, noFilesFoundAction,
                buildStepFailureThreshold, failureThreshold, unstableThreshold)
    }

    @Override
    Closure toDsl() {
        return {
            downstreamParameterized {
                trigger(name, 'ALWAYS', parameters.isEmpty()) {
                    block {
                        buildStepFailure(buildStepFailureThreshold)
                        failure(failureThreshold)
                        unstable(unstableThreshold)
                    }
                    predefinedProps(parameters)
                    if (filePattern) {
                        parameterFactories {
                            forMatchingFiles(filePattern, noFilesFoundAction)
                        }
                    }
                }
            }
        }
    }
}