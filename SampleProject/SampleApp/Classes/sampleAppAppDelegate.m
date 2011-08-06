//
//  sampleAppAppDelegate.m
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

#import "sampleAppAppDelegate.h"

@implementation sampleAppAppDelegate

@synthesize window, lvc;


#pragma mark -
#pragma mark Application lifecycle

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {    
    
    // Override point for customization after application launch.
    TTNavigator* navigator = [TTNavigator navigator];
	navigator.supportsShakeToReload = YES;
	navigator.persistenceMode = TTNavigatorPersistenceModeAll;
    navigator.window = window;
	
	//set the stylesheet so that we can have black text underneath each launcher icon
	[TTStyleSheet setGlobalStyleSheet:[[[StyleSheet alloc] init] autorelease]];
	
	TTURLMap* map = navigator.URLMap;
	[map from:@"*" toViewController:[TTWebController class]];
	
	[map            from: @"tt://launcherView"
				  parent: @"tt://catalog"
		toViewController: [LauncherViewController class]
				selector: nil
			  transition: 0];
	
	[map            from: @"tt://eventView"
				  parent: @"tt://launcherView"
		toViewController: [EventViewController class]
				selector: nil
			  transition: 0];
	
	[map            from: @"tt://scrollViewController"
				  parent: @"tt://launcherView"
		toViewController: [ScrollViewController class]
				selector: nil
			  transition: 0];
	
	[map            from: @"tt://settingsViewController"
				  parent: @"tt://launcherView"
		toViewController: [SettingsViewController class]
				selector: nil
			  transition: 0];
	
	[map            from: @"tt://newsTableViewController"
				  parent: @"tt://launcherView"
		toViewController: [NewsTableViewController class]
				selector: nil
			  transition: 0];
	
	[map            from: @"tt://rssBlogViewController"
				  parent: @"tt://newsTableViewController"
		toViewController: [RSSBlogViewController class]
				selector: nil
			  transition: 0];
	
    [navigator openURLAction:[TTURLAction actionWithURLPath:@"tt://launcherView"]];
    [self.window makeKeyAndVisible];
    
    return YES;
}


- (void)applicationWillResignActive:(UIApplication *)application {
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}


- (void)applicationDidEnterBackground:(UIApplication *)application {
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, called instead of applicationWillTerminate: when the user quits.
     */
}


- (void)applicationWillEnterForeground:(UIApplication *)application {
    /*
     Called as part of  transition from the background to the inactive state: here you can undo many of the changes made on entering the background.
     */
}


- (void)applicationDidBecomeActive:(UIApplication *)application {
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}


- (void)applicationWillTerminate:(UIApplication *)application {
    /*
     Called when the application is about to terminate.
     See also applicationDidEnterBackground:.
     */
}


#pragma mark -
#pragma mark Memory management

- (void)applicationDidReceiveMemoryWarning:(UIApplication *)application {
    /*
     Free up as much memory as possible by purging cached data objects that can be recreated (or reloaded from disk) later.
     */
}


- (void)dealloc {
    [window release];
    [super dealloc];
}


@end
