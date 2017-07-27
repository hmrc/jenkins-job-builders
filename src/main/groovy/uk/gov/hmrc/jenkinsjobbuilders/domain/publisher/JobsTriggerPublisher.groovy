package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import static java.util.Collections.emptyMap


final class JobsTriggerPublisher implements Publisher {
    private final def name
    private final def condition
    private final def params = new HashMap()

    private JobsTriggerPublisher(String name, String condition, Map<String, String> params) {
        this.name = name
        this.condition = condition
        this.params.putAll(params)
    }

    static JobsTriggerPublisher jobsTriggerPublisher(String name, String condition = 'SUCCESS', Map<String, String> parameters = emptyMap()) {
        new JobsTriggerPublisher(name, condition, parameters)
    }

    @Override
    Closure toDsl() {
        return {
            downstreamParameterized {
                trigger(name) {
                    condition(condition)
                    triggerWithNoParameters(params.isEmpty())
                    parameters {
                        predefinedProps(params)
                    }

                }
            }
        }
    }
}

