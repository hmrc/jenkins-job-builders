package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher


final class ClaimBrokenBuildsPublisher implements Publisher {

    private ClaimBrokenBuildsPublisher() {}

    static ClaimBrokenBuildsPublisher claimBrokenBuildsPublisher() {
        new ClaimBrokenBuildsPublisher()
    }

    @Override
    Closure toDsl() {
        return {
            allowBrokenBuildClaiming()
        }
    }
}