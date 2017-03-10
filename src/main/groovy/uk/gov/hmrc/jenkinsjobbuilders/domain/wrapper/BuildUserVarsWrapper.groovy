package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class BuildUserVarsWrapper implements Wrapper {

    private final String version

    private BuildUserVarsWrapper(String version) {
        this.version = version
    }

    static Wrapper buildUserVarsWrapper() {
        new BuildUserVarsWrapper()
    }

    @Override
    Closure toDsl() {
        return {
            buildUserVars()
        }
    }
}
