package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class AbsoluteTimeoutWrapper implements Wrapper {

    private final int minutes

    AbsoluteTimeoutWrapper(int minutes) {
        this.minutes = minutes
    }

    static AbsoluteTimeoutWrapper timeoutWrapper(int minutes) {
        new AbsoluteTimeoutWrapper(minutes)
    }

    @Override
    Closure toDsl() {
        return {
            timeout {
                absolute(minutes)
            }
        }
    }
}