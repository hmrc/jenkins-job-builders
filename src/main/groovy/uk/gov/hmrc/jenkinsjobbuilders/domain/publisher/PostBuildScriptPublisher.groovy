package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

final class PostBuildScriptPublisher implements Publisher {
    private final Boolean triggerOnFailure
    private final String script

    private PostBuildScriptPublisher(String script, String triggerOnFailure = true) {
        this.script = script
        this.triggerOnFailure = triggerOnFailure
    }

    static PostBuildScriptPublisher postBuildScriptPublisher(String script, String triggerOnFailure = true) {
        new PostBuildScriptPublisher(script, triggerOnFailure)
    }

    @Override
    Closure toDsl() {
        return {
            postBuildScripts {
                steps {
                    shell(script)
                }
                onlyIfBuildSucceeds(!triggerOnFailure)
                onlyIfBuildFails(triggerOnFailure)
            }
        }
    }
}
