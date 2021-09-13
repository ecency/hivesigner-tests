# hivesigner-tests

###To run tests on local machine you need to:
* clone the project from git to your local machine;
* if IDEA will suggest ==> import gradle project;
* if not ==> run `gradlew wrapper` command in terminal to install gradle from the project (current version 7.2);
* wait until all dependencies downloaded;

###To configure tests
* create `resource` folder in `./src/test/` path and add `conf.properties` file to it;

###To run tests
* Run `gradlew test` command in terminal
* You also may run all tests from `src/test/java/selenium/tests` folder using `junit` with `Run` command in IDEA context menu

###Structure
* `chromedriver` folder contains Google Chrome selenium webdriver to run tests with Google Chrome;
* `gradlew` folder contains gradlew v7.2 jar file and wrapper file;
* `pages` folder contains classes which describe each web page with elements and methods to work with them;
* `handlers` folder contains classes which make work with project easier;
* `tests` folder contains tests only.

###conf.properties file structure
getStartedPageUrl = https://your.site.url/

importPageUrl = https://your.site.url/import?redirect=accounts

loginPageUrl = https://your.site.url/login?redirect=accounts

accountsPageUrl = https://your.site.url/accounts

authoritiesPageUrl = https://your.site.url/auths

oauth2Url = https://your.site.url/oauth2/authorize

userName = 

privateKey = 

userNameAlt1 = 

privateKeyAlt1 = 

userNameAlt2 = 

privateKeyAlt2 = 

localPassword = 

localPasswordAlt =

`Chromedriver for Windows environment`:
chromedriver=..//hivesigner-tests/chromedriver/chromedriver.exe

`Chromedriver for Linux environment. Uncomment to use`
//chromedriver=/usr/bin/chromedriver

screenShotPath=..//hivesigner-tests/Screenshots/

options.addArguments=--headless

//leave `=''` if you don't want to run tests in headless mode

ownerPrivateKey = 

activePrivateKey = 

postingPrivateKey = 

memoPrivateKey = 