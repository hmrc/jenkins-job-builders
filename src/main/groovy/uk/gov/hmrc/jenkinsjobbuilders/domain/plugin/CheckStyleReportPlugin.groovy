package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

final class CheckStyleReportPlugin implements Plugin {

    private CheckStyleReportPlugin() {}

    static CheckStyleReportPlugin checkStyleReportPlugin() {
        new CheckStyleReportPlugin()
    }

    Closure toDsl() {
        return {
            it / 'publishers' / 'hudson.plugins.checkstyle.CheckStylePublisher' {
                'pluginName'('[CHECKSTYLE]')
                'thresholdLimit'('low')
                'canRunOnFailed'('false')
                'usePreviousBuildAsReference'('false')
                'useStableBuildAsReference'('false')
                'useDeltaValues'('false')
                'shouldDetectModules'('false')
                'dontComputeNew'('true')
                'doNotResolveRelativePaths'('true')
                'pattern'('**/scalastyle-result.xml')
            }
        }
    }
}