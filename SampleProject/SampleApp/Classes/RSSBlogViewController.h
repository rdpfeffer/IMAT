//
//  RSSBlogViewController.h
//  sampleApp
//
//  Created by Benjamin, Sargon on 4/18/11.
//  Copyright 2011 Intuit. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Three20/Three20.h>


@interface RSSBlogViewController : TTTableViewController<NSXMLParserDelegate> {

	NSXMLParser * rssParser;
	
	NSMutableArray * stories;
	
	
	// a temporary item; added to the "stories" array one at a time, and cleared for the next one
	NSMutableDictionary * item;
	
	// it parses through the document, from top to bottom...
	// we collect and cache each sub-element value, and then save each item to our array.
	// we use these to track each current item, until it's ready to be added to the "stories" array
	NSString * currentElement;
	NSMutableString * currentTitle, * currentDate, * currentSummary, * currentLink;
}

@end
