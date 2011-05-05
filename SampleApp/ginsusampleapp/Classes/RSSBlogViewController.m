//
//  RSSBlogViewController.m
//  ginsusampleapp
//
//  Created by Benjamin, Sargon on 4/18/11.
//  Copyright 2011 Intuit. All rights reserved.
//

#import "RSSBlogViewController.h"


@implementation RSSBlogViewController


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
	if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
		self.title = @"Mint Blog";
		self.variableHeightRows = YES;
	}
	return self;
}

- (void)viewDidAppear:(BOOL)animated {
	[super viewDidAppear:animated];
	
	if ([stories count] == 0) {
		//NSString * path = @"http://feeds.feedburner.com/TheAppleBlog";
		NSString * path = @"http://www.mint.com/blog/feed/rss/";
		[self parseXMLFileAtURL:path];
		
	}
	
	NSAutoreleasePool* localPool = [[NSAutoreleasePool alloc] init];
	NSMutableArray* items = [[NSMutableArray alloc] init];
	NSMutableArray* sections = [[NSMutableArray alloc] init];
	
	// Styles Section
	[sections addObject:NSLocalizedString(@"Recent Posts", @"Recent Posts")];
	NSMutableArray* itemsRow = [[NSMutableArray alloc] init];
	NSString *urlString;
	for (int i = 0; i< [stories count]; i++) {
		urlString = (NSString *)[[stories objectAtIndex:i] objectForKey: @"link"];
		// clean up the link - get rid of spaces, returns, and tabs...
		urlString = [urlString stringByReplacingOccurrencesOfString:@" " withString:@""];
		urlString = [urlString stringByReplacingOccurrencesOfString:@"\n" withString:@""];
		urlString = [urlString stringByReplacingOccurrencesOfString:@"	" withString:@""];
	
		[itemsRow addObject:[TTTableMessageItem itemWithTitle:[[stories objectAtIndex:i] objectForKey: @"title"] caption:nil
														 text:[[stories objectAtIndex:i] objectForKey: @"summary"] timestamp:nil
													 imageURL:@"bundle://mintsmallicon.png" URL:urlString ]];
		NSLog(@"link is %@ and %@", [[stories objectAtIndex:i] objectForKey:@"link"], urlString);
	}
	
	
	
	[items addObject:itemsRow];
	TT_RELEASE_SAFELY(itemsRow);
	
	self.dataSource = [[TTSectionedDataSource alloc] initWithItems:items sections:sections];
	
	TT_RELEASE_SAFELY(items);
	TT_RELEASE_SAFELY(sections);
	[localPool drain];
}



- (void)parserDidStartDocument:(NSXMLParser *)parser{	
	NSLog(@"found file and started parsing");
	
}

- (void)parseXMLFileAtURL:(NSString *)URL
{	
	stories = [[NSMutableArray alloc] init];
	
    //you must then convert the path to a proper NSURL or it won't work
    NSURL *xmlURL = [NSURL URLWithString:URL];
	
    // here, for some reason you have to use NSClassFromString when trying to alloc NSXMLParser, otherwise you will get an object not found error
    // this may be necessary only for the toolchain
    rssParser = [[NSXMLParser alloc] initWithContentsOfURL:xmlURL];
	
    // Set self as the delegate of the parser so that it will receive the parser delegate methods callbacks.
    [rssParser setDelegate:self];
	
    // Depending on the XML document you're parsing, you may want to enable these features of NSXMLParser.
    [rssParser setShouldProcessNamespaces:NO];
    [rssParser setShouldReportNamespacePrefixes:NO];
    [rssParser setShouldResolveExternalEntities:NO];
	
    [rssParser parse];
	
}

- (void)parser:(NSXMLParser *)parser parseErrorOccurred:(NSError *)parseError {
	NSString * errorString = [NSString stringWithFormat:@"Unable to download story feed from web site (Error code %i )", [parseError code]];
	NSLog(@"error parsing XML: %@", errorString);
	
	UIAlertView * errorAlert = [[UIAlertView alloc] initWithTitle:@"Error loading content" message:errorString delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
	[errorAlert show];
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict{			
    //NSLog(@"found this element: %@", elementName);
	currentElement = [elementName copy];
	if ([elementName isEqualToString:@"item"]) {
		// clear out our story item caches...
		item = [[NSMutableDictionary alloc] init];
		currentTitle = [[NSMutableString alloc] init];
		currentDate = [[NSMutableString alloc] init];
		currentSummary = [[NSMutableString alloc] init];
		currentLink = [[NSMutableString alloc] init];
	}
	
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName{     
	//NSLog(@"ended element: %@", elementName);
	if ([elementName isEqualToString:@"item"]) {
		// save values to an item, then store that item into the array...
		[item setObject:currentTitle forKey:@"title"];
		[item setObject:currentLink forKey:@"link"];
		[item setObject:currentSummary forKey:@"summary"];
		[item setObject:currentDate forKey:@"date"];
		
		[stories addObject:[item copy]];
		NSLog(@"adding story: %@ with url %@", currentTitle, currentLink);
	}
	
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string{
	//NSLog(@"found characters: %@", string);
	// save the characters for the current item...
	if ([currentElement isEqualToString:@"title"]) {
		[currentTitle appendString:string];
	} else if ([currentElement isEqualToString:@"link"]) {
		[currentLink appendString:string];
	} else if ([currentElement isEqualToString:@"description"]) {
		[currentSummary appendString:string];
	} else if ([currentElement isEqualToString:@"pubDate"]) {
		[currentDate appendString:string];
	}
	
}

- (void)parserDidEndDocument:(NSXMLParser *)parser {
	
	//[activityIndicator stopAnimating];
	//[activityIndicator removeFromSuperview];
	
	NSLog(@"all done!");
	NSLog(@"stories array has %d items", [stories count]);
	//[newsTable reloadData];
}



@end
