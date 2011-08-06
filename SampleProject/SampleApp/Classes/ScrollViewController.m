//
//  ScrollViewController.m
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

#import "ScrollViewController.h"

static NSString* one = @"\nThe Intuit Mobile Autoamtion Toolkit is simple to Install. In Fact, it plugs right in without project modification. Here are the Steps on how to get started... \n\n\n1. Download the archive. \n2. Extract it somewhere on your system. \n3. Add the imat executable to your path.\n\n Its that easy! \n\nRun...\n  \"imat help\" \n\f...for steps on where to go from there.";
static NSString* two = @"\nFollow the Pattern : \n\nThe Intuit Mobile Automation Toolkit allows you to write tests in a consistent way that reduces maintenance.\n\n Check out the automation for this sample app and you will see that it is quite easy to write automation from the top level test case business definitions all the way down to the view map.";
static NSString* three = @"\nSpend Less time in triage.\n\n Automated tests are no good if it takes you days to triage tests. Along with consistency comes the ability to know exactly where and why your tests are failing without having to re-run tests to figure out if the app is the problem or the test script. \n\nThis is time better spent writing more automation!";

@implementation ScrollViewController

NSArray *myArray = nil;

- (void)dealloc {
	_scrollView.delegate = nil;
	_scrollView.dataSource = nil;
	myArray = nil;
	TT_RELEASE_SAFELY(_scrollView);
	TT_RELEASE_SAFELY(_pageControl);
	TT_RELEASE_SAFELY(_colors);
	[super dealloc];
}



- (id) initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
	self = [super initWithNibName:nil bundle:nil];
	if ( self ){
		
		
		
		self.title = @"Features";
		self.navigationItem.rightBarButtonItem
		= [[[UIBarButtonItem alloc] initWithTitle:@"Details" style:UIBarButtonItemStyleBordered
										   target:self action:@selector(showDetails)] autorelease];
		
		_colors = [[NSArray arrayWithObjects:
					[UIImage imageNamed:@"lego.jpg"],
					[UIImage imageNamed:@"pattern.jpg"],
					[UIImage imageNamed:@"time.jpg"],
					nil]retain];
		
		
		if (!myArray) {
			NSLog(@"array doesn't exist");
			myArray = [NSArray arrayWithObjects:
					   one, 
					   two, 
					   three, 
					   nil];
			
			//the array wil be autoreleased unless we retain it
			[myArray retain];
		}
	}
	return self;
}

-(void)showDetails {
	UIView *modalView = [[[UIView alloc] initWithFrame:[[UIScreen mainScreen] bounds]] autorelease];
	//UIScrollView *modalView = [[[UIScrollView alloc] initWithFrame:[[UIScreen mainScreen] bounds]] autorelease];
	modalView.height = 600;
	modalView.userInteractionEnabled = YES;
	//modalView.scrollEnabled = YES;
	//modalView.alwaysBounceVertical = YES;
	
	//modalView.scrollsToTop = YES;
	//modalView.pagingEnabled = YES;
	
	//modalView.alwaysBounceVertical = YES;
	modalView.opaque = NO;
	modalView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.75f];
	
	//UILabel *label = [[[UILabel alloc] init] autorelease];
	//UITextView *label = [[[UITextView alloc] initWithFrame:CGRectZero] autorelease];
	UITextView *label = [[[UITextView alloc] init] autorelease];
	label.width = 313;
	[ label setFont: [ UIFont systemFontOfSize: 16 ]];
	label.height = 400;
	label.editable = NO;
	label.scrollEnabled = YES;
	NSLog(@"current page is %d",_pageControl.currentPage);
	
	//label.numberOfLines = 0;
	label.text = [myArray objectAtIndex:_pageControl.currentPage];
	//NSRange range = NSMakeRange(label.text.length - 1, 1);
	//[label scrollRangeToVisible:range];
	
	label.textColor = [UIColor whiteColor];
	label.backgroundColor = [UIColor clearColor];
	label.opaque = NO;
	//[label sizeToFit];
	[modalView addSubview:label];
	
	[self.view addSubview:modalView];
	
	self.navigationItem.rightBarButtonItem
	= [[[UIBarButtonItem alloc] initWithTitle:@"Close" style:UIBarButtonItemStyleBordered
									   target:self action:@selector(closeModalView)] autorelease];
}


-(void)closeModalView {
	int viewNumber = [[self.view subviews] count] -1;
	UIView *modalView = [[self.view subviews] objectAtIndex:viewNumber];
	[modalView removeFromSuperview];
	
	self.navigationItem.rightBarButtonItem
	= [[[UIBarButtonItem alloc] initWithTitle:@"Details" style:UIBarButtonItemStyleBordered
									   target:self action:@selector(showDetails)] autorelease];
}

- (void)loadView {
	CGRect appFrame = [UIScreen mainScreen].applicationFrame;
	CGRect frame = CGRectMake(0, 0, appFrame.size.width, appFrame.size.height - 44);
	self.view = [[[UIView alloc] initWithFrame:frame] autorelease];
	
	
	
	_pageControl = [[TTPageControl alloc] initWithFrame:CGRectMake(0,0, self.view.bounds.size.width, 20)];
	//_pageControl = [[TTPageControl alloc] initWithFrame:CGRectMake(0,0, 300, 20)];
	_pageControl.autoresizingMask = UIViewAutoresizingFlexibleWidth;
	_pageControl.backgroundColor = [UIColor grayColor];
	_pageControl.currentPage = 0;
	_pageControl.numberOfPages = [_colors count];
	[_pageControl addTarget:self action:@selector(changePage:) forControlEvents:UIControlEventValueChanged];
	[self.view addSubview:_pageControl];
	
	
	_scrollView = [[TTScrollView alloc] initWithFrame:CGRectMake(0, _pageControl.bottom, self.view.bounds.size.width, self.view.bounds.size.height - _pageControl.bounds.size.height - 5.f)];
	// _scrollView = [[TTScrollView alloc] initWithFrame:CGRectMake(0,_pageControl.bottom, self.view.width, self.view.height - _pageControl.height - 5.f)];
	//_scrollView = [[TTScrollView alloc] initWithFrame:CGRectMake(0,5, 300, 450 - 430 - 5.f)];
	_scrollView.autoresizingMask = UIViewAutoresizingFlexibleWidth;
	_scrollView.dataSource = self;
	_scrollView.delegate = self;
	//_scrollView.backgroundColor = [UIColor whiteColor];
	
	//UIView *backgroundView = [[UIView alloc] initWithFrame:self.view.frame];
	//backgroundView.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"background_grayed.png"]];
	
	//self.view..backgroundView = backgroundView;
	//_scrollView.backgroundColor = [UIColor clearColor];
	_scrollView.backgroundColor = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	[self.view addSubview:_scrollView];
	
	
}

///////////////////////////////////////////////////////////////////////////////////////////////////
// TTScrollViewDataSource

- (NSInteger)numberOfPagesInScrollView:(TTScrollView*)scrollView {
	NSLog(@"size is %d", _colors.count);
	return _colors.count;
}

- (UIView*)scrollView:(TTScrollView*)scrollView pageAtIndex:(NSInteger)pageIndex {
	TTView* pageView = nil;
	if (!pageView) {
		pageView = [[[TTView alloc] init] autorelease];
		//pageView.background = [UIImage imageNamed:@"splashDefault.png"];
		//pageView.backgroundColor = [UIColor clearColor];
		
		pageView.userInteractionEnabled = NO;
		//pageView.contentMode = UIViewContentModeLeft;
	}

	UIImage *image = [_colors objectAtIndex:pageIndex];
	//UIImage *image = 
	UIImageView *imageView = [[UIImageView alloc] initWithImage:image ];
	
	return imageView;
	
}

- (CGSize)scrollView:(TTScrollView*)scrollView sizeOfPageAtIndex:(NSInteger)pageIndex {
	return CGSizeMake(320, 416);
}

#pragma mark -
#pragma mark TTScrollViewDelegate

- (void)scrollView:(TTScrollView*)scrollView didMoveToPageAtIndex:(NSInteger)pageIndex {
	_pageControl.currentPage = pageIndex;
	NSString *pewPewPath = [[NSBundle mainBundle] 
							pathForResource:@"switch" ofType:@"aiff"];
	NSURL *pewPewURL = [NSURL fileURLWithPath:pewPewPath];
	AudioServicesCreateSystemSoundID((CFURLRef)pewPewURL, &_pewPewSound);
	AudioServicesPlaySystemSound(_pewPewSound);
}

#pragma mark -
#pragma mark UIViewController overrides
- (BOOL) shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation {
	return YES;
}

- (void) didRotateFromInterfaceOrientation:(UIInterfaceOrientation)fromInterfaceOrientation {
	[super didRotateFromInterfaceOrientation:fromInterfaceOrientation];
}

#pragma mark -
#pragma mark TTPageControl

- (IBAction)changePage:(id)sender {
	int page = _pageControl.currentPage;
	[_scrollView setCenterPageIndex:page];
}

@end

