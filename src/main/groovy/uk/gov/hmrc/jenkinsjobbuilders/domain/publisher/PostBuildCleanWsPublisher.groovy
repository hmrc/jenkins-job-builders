package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

class PostBuildCleanWsPublisher implements Publisher {

    private final boolean cleanAbortedBuild
    private final boolean cleanFailBuild
    private final boolean cleanNotBuilt
    private final boolean cleanSuccessfulBuild
    private final boolean cleanUnstableBuild
    private final boolean notFailBuild

    private PostBuildCleanWsPublisher(boolean cleanAbortedBuild, boolean cleanFailBuild, boolean cleanNotBuilt, boolean cleanSuccessfulBuild, boolean cleanUnstableBuild, boolean notFailBuild) {
            this.cleanAbortedBuild = cleanAbortedBuild
            this.cleanFailBuild = cleanFailBuild
            this.cleanNotBuilt = cleanNotBuilt
            this.cleanSuccessfulBuild = cleanSuccessfulBuild
            this.cleanUnstableBuild = cleanUnstableBuild
            this.notFailBuild = notFailBuild
    }

    static Publisher postBuildCleanWsPublisher(boolean cleanAbortedBuild = true, boolean cleanFailBuild = true, boolean cleanNotBuilt = true, boolean cleanSuccessfulBuild = true, boolean cleanUnstableBuild = true, boolean notFailBuild = true) {
        new PostBuildCleanWsPublisher(cleanAbortedBuild, cleanFailBuild, cleanNotBuilt, cleanSuccessfulBuild, cleanUnstableBuild, notFailBuild)
    }

    @Override
    Closure toDsl() {
        return {
            cleanWs {
                cleanWhenAborted(this.cleanAbortedBuild)
                cleanWhenFailure(this.cleanFailBuild)
                cleanWhenNotBuilt(this.cleanNotBuilt)
                cleanWhenSuccess(this.cleanSuccessfulBuild)
                cleanWhenUnstable(this.cleanUnstableBuild)
                notFailBuild(this.notFailBuild)
            }
        }
    }
}