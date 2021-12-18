SHELL:=/bin/bash -O globstar  			# tell make to use bash.

.DEFAULT_GOAL := compile-exec

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

# --------- test ---------

tests:
	./mvnw test

test-client:
	./mvnw test -pl client

test-server:
	./mvnw test -pl server

# --------- package ---------

jars:
	./mvnw jar:jar

jar-client:
	./mvnw jar:jar -pl client

jar-server:
	./mvnw jar:jar -pl server

# --------- execute ---------

exec-client:
	./mvnw exec:exec -pl client

exec-server:
	./mvnw exec:exec -pl server

# --------- execute jar ---------

run-all-jars:
	java -jar client/bin/drive-client.jar & java -jar server/out/drive-server.jar

run-client-jar:
	java -jar client/bin/drive-client.jar

run-server-jar:
	java -jar server/bin/drive-server.jar 

# --------- default ---------

exec: exec-client exec-server

compile-exec: clean compile tests exec
