package uk.gov.hmrc.jenkinsjobbuilders.domain.step

class StepCondition {

    private boolean negative = false
    private String cause
    private String behaviour
    boolean isSet() {
        return (cause != null && behaviour != null)
    }

    static StepCondition runCondition() {
        return new StepCondition()
    }

    StepCondition not() {
        return runCondition().negate()
    }

    StepCondition negate() {
        this.negative = !this.negative
        this
    }

    static StepCondition noCondition() {
        return runCondition()
    }

    StepCondition whenCausedBy(Cause cause) {
        this.cause = cause.getValue()
        this
    }

    StepCondition andIfFailure(Behaviour behaviour) {
        this.behaviour = behaviour.getValue()
        this
    }

    String getCause() {
        return cause
    }

    String getBehaviour() {
        return behaviour
    }

    boolean isNegative() {
        return negative
    }

    enum Behaviour {
        RUN("Run"), DONT_RUN("DontRun")

        private final String value

        Behaviour(String value) {
            this.value = value
        }

        String getValue() {
            this.value
        }
    }

    enum Cause {
        UPSTREAM_CAUSE("UPSTREAM_CAUSE")

        private final String value

        Cause(String value) {
            this.value = value
        }

        String getValue() {
            this.value
        }
    }
}

