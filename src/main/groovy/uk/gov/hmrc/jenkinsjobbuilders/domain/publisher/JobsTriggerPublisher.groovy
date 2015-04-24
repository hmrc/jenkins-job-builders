package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import static java.util.Collections.emptyMap


final class JobsTriggerPublisher implements Publisher {
    private final String name
    private final Map<String, String> parameters = new HashMap()

    private JobsTriggerPublisher(String name, Map<String, String> parameters) {
        this.name = name
        this.parameters.putAll(parameters)
    }

    static JobsTriggerPublisher jobsTriggerPublisher(String name, Map<String, String> parameters = emptyMap()) {
        new JobsTriggerPublisher(name, parameters)
    }

    @Override
    Closure toDsl() {
        return {
            downstreamParameterized {
                trigger(name, 'SUCCESS', parameters.isEmpty()) {
                    predefinedProps(parameters)
                }
            }
        }
    }
}