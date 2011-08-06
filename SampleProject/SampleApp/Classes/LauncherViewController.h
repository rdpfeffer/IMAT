//
//  LauncherViewController.h
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

