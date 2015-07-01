package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class NodeJsWrapper implements Wrapper {

    private final String version

    private NodeJsWrapper(String version) {
        this.version = version
    }

    static Wrapper nodeJsWrapper(String version = '0.10.28') {
        new NodeJsWrapper(version)
    }

    @Override
    Closure toDsl() {
        return {
            nodejs("node $version")
        }
    }
}
