package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import javaposse.jobdsl.dsl.DslFactory
import uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.Publisher

final class PostBuildScriptPublisher implements Publisher {
    private final ArrayList<Result> results
    private final String script
    private final Boolean stopOnFailure

    private PostBuildScriptPublisher(String script, ArrayList<Result> results, Boolean stopOnFailure) {
        this.script = script
        this.results = results
        this.stopOnFailure = stopOnFailure
    }

    static PostBuildScriptPublisher postBuildScriptPublisher(String script, ArrayList<Result> results, Boolean stopOnFailure = false) {
        new PostBuildScriptPublisher(script, results, stopOnFailure)
    }



    @Override
    Closure toDsl() {
        return {
            postBuildScript {
                buildSteps {
                    postBuildStep {
                        stopOnFailure(this.stopOnFailure)
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
