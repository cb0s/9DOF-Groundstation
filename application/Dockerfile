FROM openjdk:16.0

ENV WORK_DIR /usr/telestion

EXPOSE 9870

COPY conf/config.json $WORK_DIR/config.json
COPY build/distributions/9DOF-Groundstation.tar $WORK_DIR/
COPY docker/exec.sh $WORK_DIR/

WORKDIR $WORK_DIR
ENTRYPOINT ["sh", "-c"]

CMD ["chmod +x ./exec.sh && ./exec.sh"]
