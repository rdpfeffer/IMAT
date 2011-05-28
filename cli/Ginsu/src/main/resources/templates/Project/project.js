#import "env/env.js"

//declare any settings
IMAT.settings.logLevel = IMAT.logLevels.LOG_DEBUG;

var @GLOBAL_OBJECT@ = {};

//load the tests and their respective views
#import "tests/tests_inc.js"
#import "views/views_inc.js"
#import "suites/SuiteHandler.js"

