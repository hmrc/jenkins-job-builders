package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

final class PostBuildTaskPublisher implements Publisher {
    private final String logText
    private final String script

    private PostBuildTaskPublisher(String logText, String script) {
        this.logText = logText
        this.script = script
    }

    static PostBuildTaskPublisher postBuildTaskPublisher(String logText, String script) {
        new PostBuildTaskPublisher(logText, script)
    }

    @Override
    Closure toDsl() {
        return {
            postBuildTask {
                task(this.logText, this.script, true, false)
            }
        }
    }
}
