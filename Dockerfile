FROM dockerhub.tax.service.gov.uk/eclipse-temurin:21

ENV ARTIFACTORY_URI=https://artefacts.tax.service.gov.uk/artifactory

WORKDIR /app

COPY . /app/

CMD ["/bin/bash", "-c", "./gradlew clean test build -info"]