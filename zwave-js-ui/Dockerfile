# Global ARGs — must be before any FROM for multi-stage scope
ARG ZWAVE_JS_UI_VERSION=11.15.1
ARG BUILD_FROM=ghcr.io/hassio-addons/base:20.0.2

# Upstream zwave-js-ui for the application files
FROM zwavejs/zwave-js-ui:${ZWAVE_JS_UI_VERSION} AS upstream

# HA add-on base
FROM ${BUILD_FROM}

ENV S6_KILL_GRACETIME=30000 \
    S6_SERVICES_GRACETIME=30000

# Install runtime deps only
RUN apk add --no-cache \
    eudev \
    libusb \
    nginx \
    nodejs

# Copy the application from upstream image
COPY --from=upstream /usr/src/app /opt/zwave-js-ui

# Copy rootfs (s6 services, nginx config)
COPY rootfs /

HEALTHCHECK CMD curl --fail http://127.0.0.1:8099/health/zwave || exit 1

ARG BUILD_ARCH
ARG BUILD_DATE
ARG BUILD_DESCRIPTION
ARG BUILD_NAME
ARG BUILD_REF
ARG BUILD_REPOSITORY
ARG BUILD_VERSION

LABEL \
    io.hass.name="${BUILD_NAME}" \
    io.hass.description="${BUILD_DESCRIPTION}" \
    io.hass.arch="${BUILD_ARCH}" \
    io.hass.type="addon" \
    io.hass.version=${BUILD_VERSION}
