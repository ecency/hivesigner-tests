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
GET_STARTED_PAGE = https://your.site.url/

IMPORT_PAGE = https://your.site.url/import?redirect=accounts

LOGIN_PAGE = https://your.site.url/login?redirect=accounts

ACCOUNTS_PAGE = https://your.site.url/accounts

AUTHORITIES_PAGE = https://your.site.url/auths

OAUTH2_URL = https://your.site.url/oauth2/authorize

USER_NAME = 

PRIVATE_KEY = 

USER_NAME_ALT1 = 

PRIVATE_KEY_ALT1 = 

USER_NAME_ALT2 = 

PRIVATE_KEY_ALT2 = 

LOCAL_PASSWORD = 

LOCAL_PASSWORD_ALT =

SCREENSHOT_PATH=..//hivesigner-tests/Screenshots/

BROWSER_HEADLESS_MODE=--headless

//leave `=''` if you don't want to run tests in headless mode

BROWSER_WINDOW_SIZE=--window-size=1920x1080

//leave `=''` if you don't want to run tests in headless mode

OWNER_PRIVATE_KEY = 

ACTIVE_PRIVATE_KEY = 

POSTING_PRIVATE_KEY = 

MEMO_PRIVATE_KEY = 
