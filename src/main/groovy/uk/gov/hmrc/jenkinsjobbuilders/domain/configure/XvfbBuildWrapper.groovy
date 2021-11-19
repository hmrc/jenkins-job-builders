package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class XvfbBuildWrapper implements Configure {

    private final boolean parallel

    private XvfbBuildWrapper(boolean parallel) {
        this.parallel = parallel
    }

    Closure toDsl() {
        return {
            it / 'buildWrappers' / 'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper' {
                'installationName'('default')
                'screen'('1920x1080x24')
                'displayNameOffset'('1')
                'debug'('true')
                'timeout'('0')
                'parallelBuild'("$parallel")
                'shutdownWithBuild'('true')
            }
        }
    }

    static XvfbBuildWrapper xvfbBuildWrapper() {
        new XvfbBuildWrapper(false)
    }

    static XvfbBuildWrapper parallelXvfbBuildWrapper() {
        new XvfbBuildWrapper(true)
    }
}