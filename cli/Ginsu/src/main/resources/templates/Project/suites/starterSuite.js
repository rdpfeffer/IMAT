//declare the platform this suite will be testing
var GINSU_TARGET_PLATFORM = "ios";

#import "../project.js"

//run the tests
GINSU.suiteRunner.previewAllRunnableTests();
GINSU.suiteRunner.runTests(new @GLOBAL_OBJECT@.SuiteHandler());
