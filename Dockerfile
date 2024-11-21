FROM ubuntu:latest
LABEL authors="David Knight"

ENTRYPOINT ["top", "-b"]