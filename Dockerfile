FROM dockerhub.tax.service.gov.uk/eclipse-temurin:21

ENV ARTIFACTORY_URI=https://artefacts.tax.service.gov.uk/artifactory

WORKDIR /app

COPY . /app/

# Using "--info" to see plugin versions, and errors
CMD ["/bin/bash", "-c", "./gradlew clean build --info --console=plain"]
