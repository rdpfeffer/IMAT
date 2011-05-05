//
//  ginsusampleappAppDelegate.h
//  ginsusampleapp
//
//  Created by Benjamin, Sargon on 4/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
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

@interface ginsusampleappAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
	LauncherViewController *lvc;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet LauncherViewController *lvc;

@end

