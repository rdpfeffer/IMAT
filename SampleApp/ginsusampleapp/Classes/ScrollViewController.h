//
//  ScrollViewController.h
//  ginsusampleapp
//
//  Created by Benjamin, Sargon on 4/27/11.
//  Copyright 2011 Intuit. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Three20/Three20.h>
#import "Three20UI/TTView.h"
#import "Three20UI/UIViewAdditions.h"
#import <AudioToolbox/AudioToolbox.h>

@interface ScrollViewController : TTViewController <TTScrollViewDataSource, TTScrollViewDelegate> {

	TTScrollView* _scrollView;
	TTPageControl* _pageControl;
	NSArray* _colors;
	SystemSoundID _pewPewSound;
	
}

@end
