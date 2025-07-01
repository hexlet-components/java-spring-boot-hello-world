.DEFAULT_GOAL := build-run

setup:
	./gradlew wrapper --gradle-version 8.14

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

run:
	./gradlew bootRun

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain

update-deps:
	./gradlew refreshVersions
	# ./gradlew dependencyUpdates -Drevision=release


build-run: build run

docker-build:
	docker build -t java-spring-boot-hello-world .

docker-run:
	docker run -p 3000:8080 --name spring-hello-world java-spring-boot-hello-world

docker-bash:
	docker run --rm -it -p 3000:8080 java-spring-boot-hello-world bash

.PHONY: build
