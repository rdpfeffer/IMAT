#!/bin/bash

RESOURCES_DIR=`pwd`
IMAT_DIR=`dirname \`which imat\``
AUTOMATION_RESULTS="$IMAT_DIR/logs/runs/Run 1/Automation Results.plist"
TEMPLATE=$RESOURCES_DIR/AutomationProject.tracetemplate
APP=$RESOURCES_DIR/sampleApp.app
pushd automationProjects
CWD=`pwd`
FOLDERS=$CWD/*

echo "Starting new report generation..." > $RESOURCES_DIR/generator.log

for DIR in $FOLDERS
do
	pushd $DIR >> $RESOURCES_DIR/generator.log
	CURRENT_PROJ=${PWD##*/}
	TARGET_SUITE=`cat TARGET | head -n 1`
	TARGET_PLIST=$RESOURCES_DIR/automationResults/${CURRENT_PROJ}.plist
	# 	echo "+++++++++++++++++++++++++++++++++++"
	# 	echo "current project:	$CURRENT_PROJ"
	# 	echo "target suite:		$TARGET_SUITE"
	# 	echo "template:		$TEMPLATE"
	# 	echo "App:			$APP"
	# 	echo "target plist		$TARGET_PLIST"
	rm -rf "env" >> $RESOURCES_DIR/generator.log
	imat init-env >> $RESOURCES_DIR/generator.log
	rm -rf instrumentscli*.trace
	echo "imat run-tests -t $TEMPLATE -a $APP -s suites/$TARGET_SUITE"
	imat run-tests -t $TEMPLATE -a $APP -s suites/$TARGET_SUITE >> $RESOURCES_DIR/generator.log
	echo "cp $AUTOMATION_RESULTS $TARGET_PLIST"
	cp "${AUTOMATION_RESULTS}" $TARGET_PLIST
	popd >> $RESOURCES_DIR/generator.log #leaving $DIR
done

popd #leaving automationProjects