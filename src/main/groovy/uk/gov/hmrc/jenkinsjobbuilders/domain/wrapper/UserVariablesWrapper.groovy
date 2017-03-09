package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class UserVariablesWrapper implements Wrapper {

    private UserVariablesWrapper() {
    }

    static Wrapper userVariablesWrapper() {
        new UserVariablesWrapper()
    }

    @Override
    Closure toDsl() {
        return {
            buildUserVars()
        }
    }
}
