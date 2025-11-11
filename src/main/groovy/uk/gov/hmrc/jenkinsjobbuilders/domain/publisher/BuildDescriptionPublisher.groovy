package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractBuildDescriptionPublisher

final class BuildDescriptionPublisher extends AbstractBuildDescriptionPublisher implements Publisher {


    private BuildDescriptionPublisher(String regex, String description, String regularExpressionForFailed = '', String descriptionForFailed = '') {
        super(regex, description, regularExpressionForFailed, descriptionForFailed)
    }

    static BuildDescriptionPublisher buildDescriptionByTextPublisher(String description, String regularExpressionForFailed = '', String descriptionForFailed = '') {
        buildDescriptionPublisher('', description, regularExpressionForFailed, descriptionForFailed)
    }

    static BuildDescriptionPublisher buildDescriptionByRegexPublisher(String regex, String regularExpressionForFailed = '', String descriptionForFailed = '') {
        buildDescriptionPublisher(regex, '', regularExpressionForFailed, descriptionForFailed)
    }

    private static BuildDescriptionPublisher buildDescriptionPublisher(String regex, String description, String regularExpressionForFailed = '', String descriptionForFailed = '') {
        new BuildDescriptionPublisher(regex, description, regularExpressionForFailed, descriptionForFailed)
    }
}
