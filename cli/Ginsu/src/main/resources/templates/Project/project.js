#import "@PATH_TO_GINSU@/js/bootstrap.js"

//declare any settings
GINSU.settings.logLevel = GINSU.logLevels.LOG_DEBUG;

var @GLOBAL_OBJECT@ = {};

//load the tests and their respective views
#import "tests/tests_inc.js"
#import "views/views_inc.js"
#import "suites/SuiteHandler.js"

