package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.step.Step

class PreScmStepsWrapper implements Wrapper {
    private final List<Step> steps

    private PreScmStepsWrapper(List<Step> steps) {
        this.steps = steps
    }

    static Wrapper preScmStepsWrapper(List<Step> steps) {
        new PreScmStepsWrapper(steps)
    }

    @Override
    Closure toDsl() {
        return {
            preScmSteps {
                this.steps.each {
                    steps(it.toDsl())
                }
            }
        }
    }
}
