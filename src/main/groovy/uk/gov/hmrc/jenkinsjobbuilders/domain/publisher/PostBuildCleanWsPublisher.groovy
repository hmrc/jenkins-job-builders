package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

class PostBuildCleanWsPublisher implements Publisher {

    static Publisher postBuildCleanWsPublisher() {
        new PostBuildCleanWsPublisher()
    }

    @Override
    Closure toDsl() {
        return {
            cleanWs()
        }
    }
}
