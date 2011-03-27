//declare the platform
var GINSU_TARGET_PLATFORM = "ios";

#import "../../ginsu/bootstrap.js"

//declare any settings
GINSU.settings.logLevel = GINSU.logLevels.LOG_DEBUG;

var ${GLOBAL_OBJECT} = {};

//load the tests
#import "../tests/StarterTests.js"
#import "../views/BaseView.js"
#import "../views/SplitViewView.js"
#import "SuiteHandler.js"

//run the tests
GINSU.suiteRunner.previewAllRunnableTests();
GINSU.suiteRunner.runTests(new ${GLOBAL_OBJECT}.SuiteHandler());
