FROM gradle:7-jdk17-alpine
COPY . /src/fagin_backend
WORKDIR /src/fagin_backend

RUN gradle --no-daemon build
CMD gradle --no-daemon bootRun