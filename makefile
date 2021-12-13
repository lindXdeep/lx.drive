SHELL:=/bin/bash -O globstar  			# tell make to use bash.

.DEFAULT_GOAL := compile-run

.PHONY: all test clean              # phony target - is not name of a file, it is a name for a recipe to be executed when you make an explicit request.

compile:
	./mvnw compile -pl client,server

run:
	echo "execution run not set yet"

compile-run: compile run
