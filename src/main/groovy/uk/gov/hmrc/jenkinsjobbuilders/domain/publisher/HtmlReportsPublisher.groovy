package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher


final class HtmlReportsPublisher implements Publisher {
    private final Map<String,String> htmlReportDirs
    private boolean keep

    private HtmlReportsPublisher(Map<String, String> htmlReportDirs, boolean keep = false) {
        this.htmlReportDirs = htmlReportDirs
        this.keep = keep
    }

    static HtmlReportsPublisher htmlReportsPublisher(Map<String, String> htmlReportDirs, boolean keep = false) {
        new HtmlReportsPublisher(htmlReportDirs, keep)
    }

    @Override
    Closure toDsl() {
        return {
            publishHtml {
                htmlReportDirs.each { dir, name ->
                    report(dir) {
                        reportName(name)
                        allowMissing(true)
                        keepAll(keep)
                    }
                }
            }
        }
    }
}