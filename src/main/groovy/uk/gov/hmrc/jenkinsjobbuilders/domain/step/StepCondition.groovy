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

    StepCondition andIfFailure(EvaluationFailureBehaviour behaviour) {
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

    enum EvaluationFailureBehaviour {
        RUN("Run"),
        RUN_UNSTABLE("RunUnstable"),
        DONT_RUN("DontRun"),
        FAIL("Fail"),
        UNSTABLE("Unstable")

        private final String value

        EvaluationFailureBehaviour(String value) {
            this.value = value
        }

        String getValue() {
            this.value
        }
    }

    enum Cause {
        UPSTREAM_CAUSE("UPSTREAM_CAUSE"),
        USER_CAUSE("USER_CAUSE"),
        CLI_CAUSE("CLI_CAUSE"),
        REMOTE_CAUSE("REMOTE_CAUSE"),
        SCM_TRIGGER("SCM_CAUSE"),
        TIMER_TRIGGER("TIMER_CAUSE"),
        FS_TRIGGER("FS_CAUSE"),
        URL_TRIGGER("URL_CAUSE"),
        IVY_TRIGGER("IVY_CAUSE"),
        SCRIPT_TRIGGER("SCRIPT_CAUSE"),
        BUILD_RESULT_TRIGGER("BUILDRESULT_CAUSE")

        private final String value

        Cause(String value) {
            this.value = value
        }

        String getValue() {
            this.value
        }
    }
}

