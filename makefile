SHELL:=/bin/bash -O globstar  			# tell make to use bash.

.DEFAULT_GOAL := compile-run

.PHONY: all test clean              # phony target - is not name of a file, it is a name for a recipe to be executed when you make an explicit request.

 # --------- clean ---------

clean:
	./mvnw clean

clean-client:
	./mvnw clean -pl client

clean-server:
	./mvnw clean -pl server

# --------- compile ---------

compile:
	./mvnw compile

compile-client:
	./mvnw compile -pl client

compile-server:
	./mvnw compile -pl server

# --------- execute ---------

run-client:
	./mvnw exec:exec -pl client

run-server:
	./mvnw exec:exec -pl server

# --------- default ---------

run: run-client run-server

compile-run: clean compile run
