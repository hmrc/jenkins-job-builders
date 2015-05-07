package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

final class DescriptionPublisher implements Publisher {
    final String description

    private DescriptionPublisher(String description) {
        this.description = description
    }

    static DescriptionPublisher descriptionPublisher(String description) {
        new DescriptionPublisher(description)
    }

    @Override
    Closure toDsl() {
        return {
            buildDescription('', this.description)
        }
    }
}
