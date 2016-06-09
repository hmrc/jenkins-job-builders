package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters


class FileParameter implements Parameter {

    private final String location
    private final String description

    private FileParameter(String location, String description) {
        this.description = description
        this.location = location
    }

    static FileParameter fileParameter(String location, String description = null) {
        new FileParameter(location, description)
    }

    @Override
    Closure toDsl() {
        return {
            fileParam(location, description)
        }
    }
}
