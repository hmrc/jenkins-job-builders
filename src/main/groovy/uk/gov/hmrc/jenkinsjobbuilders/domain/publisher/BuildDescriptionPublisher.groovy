package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

final class BuildDescriptionPublisher implements Publisher {
    private final String description
    private final String regex

    private BuildDescriptionPublisher(String regex, String description) {
        this.regex = regex
        this.description = description
    }

    static BuildDescriptionPublisher buildDescriptionByTextPublisher(String description) {
        buildDescriptionPublisher('', description)
    }

    static BuildDescriptionPublisher buildDescriptionByRegexPublisher(String regex) {
        buildDescriptionPublisher(regex, '')
    }

    private static BuildDescriptionPublisher buildDescriptionPublisher(String regex, String description) {
        new BuildDescriptionPublisher(regex, description)
    }

    @Override
    Closure toDsl() {
        return {
            buildDescription(regex, description)
        }
    }
}
