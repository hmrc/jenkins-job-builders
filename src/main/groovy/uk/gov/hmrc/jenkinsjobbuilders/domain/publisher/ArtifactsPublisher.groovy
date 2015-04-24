package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher


final class ArtifactsPublisher implements Publisher {
    private final String artifactsPattern

    private ArtifactsPublisher(String artifactsPattern) {
        this.artifactsPattern = artifactsPattern
    }

    static ArtifactsPublisher artifactsPublisher(String artifactsPattern) {
        new ArtifactsPublisher(artifactsPattern)
    }

    @Override
    Closure toDsl() {
        return {
            archiveArtifacts {
                pattern(artifactsPattern)
                latestOnly(false)
                allowEmpty(true)
            }
        }
    }
}