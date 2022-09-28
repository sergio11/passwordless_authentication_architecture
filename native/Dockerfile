# ---- Building phase ----
FROM ghcr.io/graalvm/native-image AS builder
COPY --chown=gradle:gradle .. /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew clean nativeCompile --no-daemon --debug
RUN pwd
RUN ls -al
# ---- Release ----
FROM gcr.io/distroless/base AS release
COPY --from=builder build/native-image/mfa_graalvm_service app
EXPOSE 8080
ENTRYPOINT ["/app"]
