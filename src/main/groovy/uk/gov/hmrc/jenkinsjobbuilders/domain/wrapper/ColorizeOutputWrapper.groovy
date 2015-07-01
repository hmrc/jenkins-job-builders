package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class ColorizeOutputWrapper implements Wrapper {

    private ColorizeOutputWrapper() {
    }

    static Wrapper colorizeOutputWrapper() {
        new ColorizeOutputWrapper()
    }

    @Override
    Closure toDsl() {
        return {
            colorizeOutput()
        }
    }
}
