package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class TimestampsWrapper implements Wrapper {

    private TimestampsWrapper() {
    }

    static Wrapper timestampsWrapper() {
        new TimestampsWrapper()
    }

    @Override
    Closure toDsl() {
        return {
            timestamps()
        }
    }
}
