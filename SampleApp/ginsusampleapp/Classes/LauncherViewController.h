//
//  LauncherViewController.h
//  sampleApp
//
//  Created by Benjamin, Sargon on 4/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Three20/Three20.h>
#import <Three20Style/Three20Style.h>

@interface LauncherViewController : TTViewController <TTLauncherViewDelegate, UITextFieldDelegate> {
	TTLauncherView* _launcherView;
	NSString* customStatusString;
	NSString* tempStatusString;
}

@property (nonatomic, retain) NSString *customStatusString;
@property (nonatomic, retain) NSString *tempStatusString;

@end

