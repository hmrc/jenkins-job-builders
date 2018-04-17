package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import static java.util.Collections.emptyMap


final class JobsTriggerPublisher implements Publisher {
    private final def name
    private final def condition
    private final def parameters = new HashMap()

    private JobsTriggerPublisher(String name, String condition, Map<String, String> parameters) {
        this.name = name
        this.condition = condition
        this.parameters.putAll(parameters)
    }

    static JobsTriggerPublisher jobsTriggerPublisher(String name, String condition = 'SUCCESS', Map<String, String> parameters = emptyMap()) {
        new JobsTriggerPublisher(name, condition, parameters)
    }

    @Override
    Closure toDsl() {
        return {
            downstreamParameterized {
                trigger(name) {
                    condition(this.condition)
                    if(parameters.isEmpty()) {
                        triggerWithNoParameters()
                    }
                    else {
                        parameters {
                            predefinedProps(this.parameters)
                        }
                    }
                }
            }
        }
    }
}
