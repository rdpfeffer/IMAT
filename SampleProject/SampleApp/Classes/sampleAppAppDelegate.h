//
//  sampleAppAppDelegate.h
//  sampleApp
//
//  Copyright (c) 2011 Intuit, Inc.
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  which accompanies this distribution, and is available at
//  http://www.opensource.org/licenses/eclipse-1.0.php
//  
//  Contributors:
//     Intuit, Inc - initial API and implementation
//  Author(s):
//      Benjamin, Sargon
//

#import <UIKit/UIKit.h>
#import "EventViewController.h"
#import "LauncherViewController.h"
#import <Three20/Three20.h>
#import <Three20Style/Three20Style.h>
#import "StyleSheet.h"
#import "ScrollViewController.h"
#import "NewsTableViewController.h"
#import "RSSBlogViewController.h"
#import "SettingsViewController.h"

@interface sampleAppAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
	LauncherViewController *lvc;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet LauncherViewController *lvc;

@end

