FROM centos:8
RUN yum install -y java-17-openjdk
WORKDIR /app
# 将您的 jar 包添加到工作目录,比如 target/yuoj-backend-user-service-0.0.1-SNAPSHOT.jar
ADD target/his-systemmanagement-0.0.1-SNAPSHOT.jar .
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "/app/his-systemmanagement-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]