FROM anhem/docker-urchin-deps

RUN mkdir /workspace && \
    git clone https://github.com/anhem/urchin /workspace

ADD entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

EXPOSE 8080

