package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.PostBuildTaskPublisher.postBuildTaskPublisher


final class CleanXvfbPostBuildTaskPublisher implements Publisher {
    private final Publisher publisher

    private CleanXvfbPostBuildTaskPublisher() {
        this.publisher = postBuildTaskPublisher('Xvfb starting(.*)', """\
                                                                     |#!/bin/bash
                                                                     |ps cax | grep Xvfb > /dev/null
                                                                     |if [ \$? -eq 0 ]; then
                                                                     |  echo "Cleaning up Xvfb"
                                                                     |  pkill Xvfb
                                                                     |  if [ -e /tmp/.X99-lock ]
                                                                     |  then
                                                                     |    unlink /tmp/.X99-lock
                                                                     |  fi
                                                                     |fi
                                                                     """.stripMargin())
    }

    static Publisher cleanXvfbPostBuildTaskPublisher() {
        new CleanXvfbPostBuildTaskPublisher()
    }

    @Override
    Closure toDsl() {
        return publisher.toDsl()
    }
}