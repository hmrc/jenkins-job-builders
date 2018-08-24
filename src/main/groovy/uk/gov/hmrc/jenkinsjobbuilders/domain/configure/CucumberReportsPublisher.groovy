package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class CucumberReportsPublisher implements Configure {

    private CucumberReportsPublisher() {}

    private String jsonReportDirectory = 'target'
    private String fileIncludePattern = 'cucumber.json'
    private String fileExcludePattern = ''

    CucumberReportsPublisher withJsonReportDirectory(String jsonReportDirectory) {
        this.jsonReportDirectory = jsonReportDirectory
        this
    }

    CucumberReportsPublisher withFileIncludePattern(String fileIncludePattern) {
        this.fileIncludePattern = fileIncludePattern
        this
    }

    CucumberReportsPublisher withFileExludePattern(String fileExcludePattern) {
        this.fileExcludePattern = fileExcludePattern
        this
    }

    Closure toDsl() {
        return {
            it / 'publishers' / 'net.masterthought.jenkins.CucumberReportPublisher' {
                'jsonReportDirectory'(jsonReportDirectory)
                'fileIncludePattern'(fileIncludePattern)
                'fileExcludePattern'(fileExcludePattern)
                'pluginUrlPath'('')
                'skippedFails'('false')
                'undefinedFails'('false')
                'noFlashCharts'('false')
                'ignoreFailedTests'('false')
            }
        }
    }

    static CucumberReportsPublisher cucumberReportsPublisher() {
        new CucumberReportsPublisher()
    }

}