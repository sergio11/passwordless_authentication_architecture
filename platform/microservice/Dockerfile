# ---- Building phase ----
FROM ghcr.io/graalvm/native-image:22.2.0 AS builder
RUN mkdir -p /tmp/export/lib64 \
    && cp /usr/lib64/libstdc++.so.6.0.25 /tmp/export/lib64/libstdc++.so.6 \
    && cp /usr/lib64/libz.so.1 /tmp/export/lib64/libz.so.1
COPY --chown=gradle:gradle .. /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew clean nativeCompile --no-daemon --debug

# ---- Release ----
FROM gcr.io/distroless/base AS release
COPY --from=builder /tmp/export/lib64 /lib64
COPY --from=builder /home/gradle/src/build/native/nativeCompile/otp_graalvm_service app
ENV LD_LIBRARY_PATH /lib64
EXPOSE 8080
ENTRYPOINT ["/app"]
