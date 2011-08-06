//
//  EventViewController.m
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

#import "EventViewController.h"
static NSString* kLoremIpsum = @"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do\
eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud\
exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";


@implementation EventViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
	if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
		self.title = @"Events";
		self.variableHeightRows = YES;
		
		// This demonstrates how to create a table with standard table "fields".  Many of these
		// fields with URLs that will be visited when the row is selected
		
		
		
		self.dataSource = [TTSectionedDataSource dataSourceWithObjects:
						   @"Event Listings",
						   [TTTableTextItem itemWithText:@"Mint events" URL:@"http://www.mint.com" accessoryURL:@"http://www.mint.com"],
						   [TTTableTextItem itemWithText:@"Intuit Events" URL:@"http://www.intuit.com" accessoryURL:@"http://www.intuit.com"],
						   @"Today",
						   [TTTableMessageItem itemWithTitle:@"Brad Smith" caption:@"Cafe 11"
														text:kLoremIpsum timestamp:[NSDate date]
													imageURL:@"http://about.intuit.com/about_intuit/images/executives/brad_smith_lg.jpg" URL:nil],
						   @"Venues",
						   [TTTableSubtextItem itemWithText:@"Cafe 11"
													caption:kLoremIpsum],
						   [TTTableSubtextItem itemWithText:@"Gymnasium"
													caption:kLoremIpsum],
						   [TTTableSubtextItem itemWithText:@"Mobile Lab"
													caption:kLoremIpsum],
						   [TTTableActivityItem itemWithText:@"Refresh"],
						   nil];
	}
	return self;
}


@end
