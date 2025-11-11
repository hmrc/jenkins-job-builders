package uk.gov.hmrc.jenkinsjobbuilders.domain

abstract class AbstractBuildDescriptionStep implements Setting {
    protected final String description
    protected final String regex

    protected AbstractBuildDescriptionStep(String regex, String description) {
        this.regex = regex
        this.description = description
    }

    Closure toDsl() {
        return {
            buildDescription(regex, description)
        }
    }
}