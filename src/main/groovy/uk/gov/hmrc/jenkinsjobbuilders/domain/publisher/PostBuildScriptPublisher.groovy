package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import javaposse.jobdsl.dsl.DslFactory
import uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.Publisher

final class PostBuildScriptPublisher implements Publisher {
    private final ArrayList<Result> results
    private final String script

    private PostBuildScriptPublisher(String script, ArrayList<Result> results) {
        this.script = script
        this.results = results
    }

    static PostBuildScriptPublisher postBuildScriptPublisher(String script, ArrayList<Result> results) {
        new PostBuildScriptPublisher(script, results)
    }

    @Override
    Closure toDsl() {
        return {
            postBuildScript {
                buildSteps {
                    postBuildStep {
                        results(results*.name())
                        buildSteps {
                            shell {
                                command(script)
                            }
                        }
                    }
                }
                markBuildUnstable(false)
            }
        }
    }
}

enum Result {
    SUCCESS,
    UNSTABLE,
    FAILURE,
    NOT_BUILT,
    ABORTED
}
