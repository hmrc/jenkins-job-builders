package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

class PostBuildCleanWsPublisher implements Publisher {

    private final boolean cleanAbortedBuild
    private final boolean cleanFailBuild
    private final boolean cleanNotBuilt
    private final boolean cleanSuccessfulBuild
    private final boolean cleanUnstableBuild
    private final boolean deleteWorkspaceDirs
    private final boolean notFailBuild
    private final boolean disableDeferredWipeout

    static Publisher postBuildCleanWsPublisher(boolean cleanAbortedBuild = true, boolean cleanFailBuild = true, boolean cleanNotBuilt = true, boolean cleanSuccessfulBuild = true, boolean cleanUnstableBuild = true, boolean deleteWorkspaceDirs = true, boolean notFailBuild = true, boolean disableDeferredWipeout = true) {
        new PostBuildCleanWsPublisher(cleanAbortedBuild, cleanFailBuild, cleanNotBuilt, cleanSuccessfulBuild, cleanUnstableBuild, deleteWorkspaceDirs, notFailBuild, disableDeferredWipeout)
    }

    @Override
    Closure toDsl() {
        return {
            cleanWs{
                cleanWhenAborted(this.cleanAbortedBuild)
                cleanWhenFailure(this.cleanFailBuild)
                cleanWhenNotBuilt(this.cleanNotBuilt)
                cleanWhenSuccess(this.cleanSuccessfulBuild)
                cleanWhenUnstable(this.cleanUnstableBuild)
                deleteDirs(this.deleteWorkspaceDirs)
                notFailBuild(this.notFailBuild)
                disableDeferredWipeout(this.disableDeferredWipeout)
            }
        }
    }
}
