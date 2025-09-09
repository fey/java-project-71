install:
	./gradlew installDist

build:
	./gradlew build

lint:
	./gradlew check

test:
	./gradlew test

run-dist:
	./build/install/app/bin/app src/test/resources/fixtures/file1.json src/test/resources/fixtures/file2.json --format=json

.PHONY: build