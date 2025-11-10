package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractBuildDescription

final class BuildDescriptionPublisher extends AbstractBuildDescription implements Publisher {

    private BuildDescriptionPublisher(String regex, String description, String regularExpressionForFailed = '', String descriptionForFailed = '') {
        super(regex, description, regularExpressionForFailed, descriptionForFailed)
    }

    static BuildDescriptionPublisher buildDescriptionByTextPublisher(String description) {
        buildDescriptionPublisher('', description)
    }

    static BuildDescriptionPublisher buildDescriptionByRegexPublisher(String regex, String regularExpressionForFailed='', String descriptionForFailed='') {
        buildDescriptionPublisher(regex, '', regularExpressionForFailed, descriptionForFailed)
    }

    private static BuildDescriptionPublisher buildDescriptionPublisher(String regex, String description, String regularExpressionForFailed = '', String descriptionForFailed = '') {
        new BuildDescriptionPublisher(regex, description, regularExpressionForFailed, descriptionForFailed)
    }
}
