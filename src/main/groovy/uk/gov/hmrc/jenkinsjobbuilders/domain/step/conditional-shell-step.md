###How to use a ConditionalShellStep
 
 Given a `StepCondition`, a `shellStep` can be run or not depending on the cause that triggered the job.
 If a `StepCondition` is defined as:
 
 ```groovy
import uk.gov.hmrc.jenkinsjobbuilders.domain.step.StepCondition
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.StepCondition.runCondition
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.StepCondition.Behaviour.DONT_RUN
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.StepCondition.Cause.UPSTREAM_CAUSE

StepCondition condition = runCondition().causedBy(UPSTREAM_CAUSE).andIfFailure(DONT_RUN)
```

a `ConditionalShellStep` will generate a step that will run only if the cause that triggered the job was an upstream job, and if Jenkins cannot determine the cause (in case of failure) as default it will not run the step.
For example, given the previous condition:

```groovy
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ConditionalShellStep.conditionalShellStep

if(condition.isSet)
    conditionalShellStep("echo 'This job was triggered by an upstream job'", condition)
```

A `StepCondition` can also be negated (if the evaluation exception behaviour is not specified, the `RUN` will be applied), and a construct is provided:
```groovy
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.StepCondition.Cause.TIMER_TRIGGER
StepCondition condition = runCondition().isNot().causedBy(TIMER_TRIGGER)
```