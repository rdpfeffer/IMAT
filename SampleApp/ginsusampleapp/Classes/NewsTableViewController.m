//
//  NewsTableViewController.m
//  sampleApp
//
//  Created by Benjamin, Sargon on 4/27/11.
//  Copyright 2011 Intuit. All rights reserved.
//

#import "NewsTableViewController.h"


@implementation NewsTableViewController
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
	if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
		self.title = @"News";
		self.variableHeightRows = YES;
		
		/*
		[[TTNavigator navigator].URLMap from:@"tt://compose?to=(composeTo:)"
					   toModalViewController:self selector:@selector(composeTo:)];
		*/
		 
		self.dataSource = [TTSectionedDataSource dataSourceWithObjects:
						   @"Social Media",
						   [TTTableMessageItem itemWithTitle:@"Intuit on Facebook" caption:nil
														text:@"Check out Intuit on Facebook, like it, and tell your friends!" timestamp:nil
													imageURL:@"bundle://facebookicon.png" URL:@"http://www.facebook.com/intuit" ],
						   [TTTableMessageItem itemWithTitle:@"Intuit on Twitter" caption:nil
														text:@"Small Busines. Rejoice!" timestamp:nil
													imageURL:@"bundle://twittericon.png" URL:@"http://mobile.twitter.com/Intuit" ],
						   
						   [TTTableMessageItem itemWithTitle:@"Intuit in the Press" caption:nil
														text:@"Keep up on the latest news from Intuit." timestamp:nil
													imageURL:@"bundle://rssicon.png"  URL:@"tt://rssBlogViewController"],
						   [TTTableMessageItem itemWithTitle:@"Feedback" caption:nil
														text:@"Run into an issue or want to see an update? Let us know" timestamp:nil
													imageURL:@"bundle://emailicon.png" URL:nil ],
						   nil];
		
		
	}
	return self;
}
/*
- (UIViewController*)composeTo:(NSString*)recipient {
	TTTableTextItem* item = [TTTableTextItem itemWithText:recipient URL:nil];
	
	TTMessageController* controller =
    [[[TTMessageController alloc] initWithRecipients:[NSArray arrayWithObject:item]] autorelease];
	controller.dataSource = [[[MockSearchDataSource alloc] init] autorelease];
	controller.delegate = self;
	
	return controller;
}
*/

- (void)dealloc {
	[[TTNavigator navigator].URLMap removeURL:@"tt://compose?to=(composeTo:)"];
    [super dealloc];
}

@end
