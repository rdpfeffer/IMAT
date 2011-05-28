//declare the platform this suite will be testing
var IMAT_TARGET_PLATFORM = "ios";

#import "../project.js"

//run the tests
IMAT.suiteRunner.previewAllRunnableTests();
IMAT.suiteRunner.runTests(new @GLOBAL_OBJECT@.SuiteHandler());
