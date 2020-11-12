#!/usr/bin/env python3

import argparse
import json
import requests
import os
import logging

logging.basicConfig(
    level=logging.INFO, format="%(asctime)s %(levelname)s - %(message)s"
)


class BintrayDistribution:
    def __init__(self, source, destination, package, version):
        self.artifactory_repo = source
        self.bintray_repo = destination
        self.package = package
        self.version = self.parse_version(version)

        try:
            self.artifactory_uri = os.environ["ARTIFACTORY_URI"]
            self.bintray_uri = os.environ["BINTRAY_URI"]
            self.bintray_username = os.environ["BINTRAY_USERNAME"]
            self.bintray_api_key = os.environ["BINTRAY_API_KEY"]
        except KeyError:
            logging.exception("Environment variable not set.")
            raise

    def parse_version(self, version):
        if version.startswith("release/"):
            return version.replace("release/", "")
        return version

    def get_file_list_from_artifactory(self):
        url = f"{self.artifactory_uri}/api/storage/{self.artifactory_repo}/uk/gov/hmrc/{self.package}/{self.version}/"
        logging.info(
            f"Retrieving files to be distributed from the following location {url}..."
        )
        response = requests.get(url)
        expected_response_code = 200
        if response.status_code == expected_response_code:
            result = [
                child["uri"][1:]
                for child in json.loads(response.content).get("children")
            ]
            if len(result) > 0:
                logging.info(f"Retrieved the following files {result}")
                return result
            else:
                logging.critical(f"Did not retrieve any files from {url}")
        else:
            logging.critical(
                f"Did not recieve the expected response code of {expected_response_code} got {response.status_code} from {url}"
            )
            raise Exception

    def get_file_from_artifactory(self, filename):
        url = f"{self.artifactory_uri}/{self.artifactory_repo}/uk/gov/hmrc/{self.package}/{self.version}/{filename}"
        logging.info(f"Downloading {filename} from {url}...")
        response = requests.get(url)
        expected_response_code = 200
        if response.status_code == expected_response_code:
            return response.content
        else:
            logging.critical(
                f"Did not recieve the expected response code of {expected_response_code} got {response.status_code} from {url}"
            )
            raise Exception

    def upload_file_to_bintray(self, payload, filename):
        url = f"{self.bintray_uri}/{self.bintray_repo}/{self.package}/{self.version}/uk/gov/hmrc/{self.package}/{self.version}/{filename}"
        logging.info(f"Uploading {filename} to {url}...")
        response = requests.put(
            url=url,
            data=payload,
            auth=requests.auth.HTTPBasicAuth(
                self.bintray_username, self.bintray_api_key
            ),
        )
        expected_response_code = 201
        if response.status_code == expected_response_code:
            logging.info(f"Successfully uploaded {filename} to {url}")
            return response.status_code
        elif response.status_code == 409:
            logging.error(
                f"Received status code 409. Cannot upload {filename} to {url} as it already exists and is published."
            )
            return response.status_code
        else:
            logging.critical(
                f"Did not recieve the expected response code of {expected_response_code} got {response.status_code} from {url}"
            )
            return response.status_code

    def publish_package_on_bintray(self):
        url = f"{self.bintray_uri}/{self.bintray_repo}/{self.package}/{self.version}/publish"
        logging.info(f"Publishing {self.package} {self.version}...")
        response = requests.post(
            url,
            auth=requests.auth.HTTPBasicAuth(
                self.bintray_username, self.bintray_api_key
            ),
        )
        expected_response_code = 200
        if response.status_code == expected_response_code:
            logging.info(f"Successfully published {self.package} {self.version}.")
            return response.status_code
        else:
            logging.critical(
                f"Did not recieve the expected response code of {expected_response_code} got {response.status_code} from {url}"
            )
            return response.status_code


if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="Distribute and publish a package to Bintray from Artifactory."
    )
    parser.add_argument(
        "--source",
        help="The name for the source Artifactory repository",
        required=True,
    )
    parser.add_argument(
        "--destination",
        required=True,
        help="The name of the destination Bintray repository",
    )
    parser.add_argument(
        "--package", required=True, help="The name of the package to be distributed"
    )
    parser.add_argument(
        "--version", required=True, help="The version of the package to be distributed"
    )
    args = parser.parse_args()

    bintray_distribution = BintrayDistribution(
        source=args.source,
        destination=args.destination,
        package=args.package,
        version=args.version,
    )

    file_list = bintray_distribution.get_file_list_from_artifactory()

    bintray_upload_response_codes = []
    for filename in file_list:
        payload = bintray_distribution.get_file_from_artifactory(filename)
        bintray_upload_response_codes.append(
            bintray_distribution.upload_file_to_bintray(payload, filename)
        )

    if not all(status_code == 201 for status_code in bintray_upload_response_codes):
        logging.critical(
            f"Looks like there was an issue while uploading files to Bintray, skipping publish step."
        )
        raise Exception
    else:
        bintray_distribution.publish_package_on_bintray()
