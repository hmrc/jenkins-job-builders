package uk.gov.hmrc.jenkinsjobbuilders.domain.step;

import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractBuildDescriptionStep

final class BuildDescriptionStep extends AbstractBuildDescriptionStep implements Step {

    protected BuildDescriptionStep(String regex, String description) {
        super(regex, description)
    }

    static BuildDescriptionStep buildDescriptionByTextStep(String description) {
        return new BuildDescriptionStep('', description)
    }

    static BuildDescriptionStep buildDescriptionByRegexStep(String regex) {
        return new BuildDescriptionStep(regex, '')
    }
}
