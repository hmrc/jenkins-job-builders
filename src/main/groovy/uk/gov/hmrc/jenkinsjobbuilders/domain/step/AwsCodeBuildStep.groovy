package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy
import uk.gov.hmrc.jenkinsjobbuilders.domain.configure.Configure

@Deprecated
final class AwsCodeBuildStep implements Configure {

    String credentialsType
    String credentialsId
    String projectName
    String proxyHost
    String proxyPort
    String awsAccessKey
    String awsSecretKey
    String region
    String sourceControlType
    String localSourcePath
    String workspaceSubdir
    String sourceVersion
    String sseAlgorithm
    String gitCloneDepthOverride
    String reportBuildStatusOverride
    String secondarySourcesOverride
    String secondarySourcesVersionOverride
    String artifactTypeOverride
    String artifactLocationOverride
    String artifactNameOverride
    String artifactNamespaceOverride
    String artifactPackagingOverride
    String artifactPathOverride
    String artifactEncryptionDisabledOverride
    String overrideArtifactName
    String secondaryArtifactsOverride
    String environmentTypeOverride
    String imageOverride
    String computeTypeOverride
    String certificateOverride
    String cacheTypeOverride
    String cacheLocationOverride
    String cloudWatchLogsStatusOverride
    String cloudWatchLogsGroupNameOverride
    String cloudWatchLogsStreamNameOverride
    String s3LogsStatusOverride
    String s3LogsLocationOverride
    String serviceRoleOverride
    String privilegedModeOverride
    String sourceTypeOverride
    String sourceLocationOverride
    String insecureSslOverride
    String envVariables
    String envParameters
    String buildSpecFile
    String buildTimeoutOverride
    String cwlStreamingDisabled

    @Override
    Closure toDsl() {
        return {
            it / 'builders' / 'CodeBuilder' {
                'credentialsType'(credentialsType ?: 'jenkins')
                'credentialsId'(credentialsId ?: '')
                'projectName'(projectName ?: '')
                'proxyHost'(proxyHost ?: '')
                'proxyPort'(proxyPort ?: '')
                'region'(region ?: 'eu-west-2')
                'awsAccessKey'(awsAccessKey ?: '')
                'awsSecretKey'(awsSecretKey ?: '')
                'sourceControlType'(sourceControlType ?: '')
                'localSourcePath'(localSourcePath ?: '')
                'workspaceSubdir'(workspaceSubdir ?: '')
                'sourceVersion'(sourceVersion ?: '')
                'sseAlgorithm'(sseAlgorithm ?: '')
                'gitCloneDepthOverride'(gitCloneDepthOverride ?: '')
                'reportBuildStatusOverride'(reportBuildStatusOverride ?: '')
                'secondarySourcesOverride'(secondarySourcesOverride ?: '')
                'secondarySourcesVersionOverride'(secondarySourcesVersionOverride ?: '')
                'artifactTypeOverride'(artifactTypeOverride ?: '')
                'artifactLocationOverride'(artifactLocationOverride ?: '')
                'artifactNameOverride'(artifactNameOverride ?: '')
                'artifactNamespaceOverride'(artifactNamespaceOverride ?: '')
                'artifactPackagingOverride'(artifactPackagingOverride ?: '')
                'artifactPathOverride'(artifactPathOverride ?: '')
                'artifactEncryptionDisabledOverride'(artifactEncryptionDisabledOverride ?: '')
                'overrideArtifactName'(overrideArtifactName ?: '')
                'secondaryArtifactsOverride'(secondaryArtifactsOverride ?: '')
                'environmentTypeOverride'(environmentTypeOverride ?: '')
                'imageOverride'(imageOverride ?: '')
                'computeTypeOverride'(computeTypeOverride ?: '')
                'certificateOverride'(certificateOverride ?: '')
                'cacheTypeOverride'(cacheTypeOverride ?: '')
                'cacheLocationOverride'(cacheLocationOverride ?: '')
                'cloudWatchLogsStatusOverride'(cloudWatchLogsStatusOverride ?: '')
                'cloudWatchLogsGroupNameOverride'(cloudWatchLogsGroupNameOverride ?: '')
                'cloudWatchLogsStreamNameOverride'(cloudWatchLogsStreamNameOverride ?: '')
                's3LogsStatusOverride'(s3LogsStatusOverride ?: '')
                's3LogsLocationOverride'(s3LogsLocationOverride ?: '')
                'serviceRoleOverride'(serviceRoleOverride ?: '')
                'privilegedModeOverride'(privilegedModeOverride ?: '')
                'sourceTypeOverride'(sourceTypeOverride ?: '')
                'sourceLocationOverride'(sourceLocationOverride ?: '')
                'insecureSslOverride'(insecureSslOverride ?: '')
                'envVariables'(envVariables ?: '')
                'envParameters'(envParameters ?: '')
                'buildSpecFile'(buildSpecFile ?: '')
                'buildTimeoutOverride'(buildTimeoutOverride ?: '')
                'cwlStreamingDisabled'(cwlStreamingDisabled ?: '')
            }
        }
    }
}

@Builder(forClass = AwsCodeBuildStep,
        builderStrategy = ExternalStrategy,
        prefix = "with")
class AwsCodeBuildStepBuilder { }
