package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import static java.util.Collections.emptyMap

final class BlockingJobsTriggerStep implements Step {
    private final String name
    private final Map<String, String> parameters = new HashMap()
    private static final blockingThresholds = [buildStepFailure: 'FAILURE', failure: 'FAILURE', unstable: 'UNSTABLE']

    private BlockingJobsTriggerStep(String name, Map<String, String> parameters) {
        this.name = name
        this.parameters.putAll(parameters)
    }

    static BlockingJobsTriggerStep jobsTriggerStep(String projects, Map<String, String> parameters = emptyMap()) {
        new BlockingJobsTriggerStep(projects, parameters)
    }

    @Override
    Closure toDsl() {
        return {
            downstreamParameterized {
                trigger(name, 'ALWAYS', parameters.isEmpty(),blockingThresholds) {
                    predefinedProps(parameters)
                }
            }
        }
    }
}