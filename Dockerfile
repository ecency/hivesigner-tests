#Step 0: Choose base
FROM openjdk:11
#Step 1 : Install the pre-requisite
RUN apt-get update
RUN apt-get install -y curl
RUN apt-get install -y p7zip \
    p7zip-full \
    unace \
    zip \
    unzip \
    bzip2

#Step 2: Install Gradle
USER root
ARG GRADLE_VERSION=7.2

RUN wget -q https://services.gradle.org/distributions/gradle-7.2-bin.zip \
    && unzip gradle-7.2-bin.zip -d /opt \
    && rm gradle-7.2-bin.zip

# Set Gradle in the environment variables
ENV GRADLE_HOME /opt/gradle-7.2
ENV PATH $PATH:/opt/gradle-7.2/bin

#Step 3: Install Chrome
#============================================
# Google Chrome
#============================================
# can specify versions by CHROME_VERSION;
#  e.g. google-chrome-stable=53.0.2785.101-1
#       google-chrome-beta=53.0.2785.92-1
#       google-chrome-unstable=54.0.2840.14-1
#       latest (equivalent to google-chrome-stable)
#       google-chrome-beta  (pull latest beta)
#============================================
ARG CHROME_VERSION="google-chrome-stable"
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
  && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
  && apt-get update -qqy \
  && apt-get -qqy install \
    ${CHROME_VERSION:-google-chrome-stable} \
  && rm /etc/apt/sources.list.d/google-chrome.list \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

#Step 4: Copy our project
COPY . /app

#Making our working directory as /app
WORKDIR /app

RUN source env.sh

RUN echo "Run gradlew test task"

CMD gradle test