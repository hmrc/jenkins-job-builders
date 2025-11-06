package uk.gov.hmrc.jenkinsjobbuilders.domain

abstract class AbstractBuildDescription implements Setting {
    protected final String description
    protected final String regex
    protected final String regularExpressionForFailed
    protected final String descriptionForFailed

    protected AbstractBuildDescription(String regex, String description, String regularExpressionForFailed, String descriptionForFailed) {
        this.regex = regex
        this.description = description
        this.regularExpressionForFailed = regularExpressionForFailed
        this.descriptionForFailed = descriptionForFailed
    }

    Closure toDsl() {
        return {
            buildDescription(regex, description, regularExpressionForFailed, descriptionForFailed)
        }
    }
}