package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher


final class HtmlReportsPublisher implements Publisher {
    private final Map<String,String> htmlReportDirs
    private boolean keepAll

    private HtmlReportsPublisher(Map<String, String> htmlReportDirs, boolean keepAll = false) {
        this.htmlReportDirs = htmlReportDirs
        this.keepAll = keepAll
    }

    static HtmlReportsPublisher htmlReportsPublisher(Map<String, String> htmlReportDirs) {
        new HtmlReportsPublisher(htmlReportDirs)
    }

    @Override
    Closure toDsl() {
        return {
            publishHtml {
                htmlReportDirs.each { dir, name ->
                    report(dir) {
                        reportName(name)
                        allowMissing(true)
                        keepAll(keepAll)
                    }
                }
            }
        }
    }
}