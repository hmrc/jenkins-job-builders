package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class XvfbBuildWrapper implements Plugin {

    private final boolean parallel

    private XvfbBuildWrapper(boolean parallel) {
        this.parallel = parallel
    }

    Closure toDsl() {
        return {
            it / 'buildWrappers' / 'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper' {
                'switch'('on')
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

    static XvfbBuildWrapper xvfbBuildPlugin() {
        new XvfbBuildWrapper(false)
    }

    static XvfbBuildWrapper parallelXvfbBuildPlugin() {
        new XvfbBuildWrapper(true)
    }
}