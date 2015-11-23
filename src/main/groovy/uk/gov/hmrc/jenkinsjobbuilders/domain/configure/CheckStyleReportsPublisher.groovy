package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class CheckStyleReportsPublisher implements Configure {

    private CheckStyleReportsPublisher() {}

    static CheckStyleReportsPublisher checkStyleReportsPublisher() {
        new CheckStyleReportsPublisher()
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