package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class PreBuildCleanupWrapper implements Wrapper {

    private PreBuildCleanupWrapper() {
    }

    static Wrapper preBuildCleanUpWrapper() {
        new PreBuildCleanupWrapper()
    }

    @Override
    Closure toDsl() {
        return {
            preBuildCleanup()
        }
    }
}
