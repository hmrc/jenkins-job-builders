package uk.gov.hmrc.jenkinsjobbuilders.domain

abstract class AbstractBuildDescription implements Setting {
    protected final String description
    protected final String regex

    protected AbstractBuildDescription(String regex, String description) {
        this.regex = regex
        this.description = description
    }

    Closure toDsl() {
        return {
            buildDescription(regex, description)
        }
    }
}