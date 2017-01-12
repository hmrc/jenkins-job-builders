package uk.gov.hmrc.jenkinsjobbuilders.domain.step

class ConditionalShellStep extends ShellStep {
    private final StepCondition stepCondition

    private ConditionalShellStep(String command, StepCondition stepCondition) {
        super(command)
        this.stepCondition = stepCondition
    }

    static Step conditionalShellStep(String command, StepCondition condition) {
        new ConditionalShellStep(command, condition)
    }

    @Override
    Closure toDsl() {
        stepCondition.isNegative() ? negativeCauseCondition() : affermativeCauseCondition()
    }

    Closure affermativeCauseCondition() {
        return {
            conditionalSteps {
                condition {
                    cause(stepCondition.getCause(), false)
                }
                runner(stepCondition.getBehaviour())
                shell(command)
            }
        }
    }

    Closure negativeCauseCondition() {
        return {
            conditionalSteps {
                condition {
                    not {
                        cause(stepCondition.getCause(), false)
                    }
                }
                runner(stepCondition.getBehaviour())
                shell(command)
            }
        }
    }
}
