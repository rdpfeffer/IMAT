//
//  StyleSheet.m
//  stancofair
//
//  Created by Benjamin, Sargon on 3/20/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "StyleSheet.h"


@implementation StyleSheet
// Style for TTLauncherItems
- (TTStyle*)launcherButton:(UIControlState)state {
	return
    [TTPartStyle styleWithName:@"image" style:TTSTYLESTATE(launcherButtonImage:, state) next:
	 [TTTextStyle styleWithFont:[UIFont boldSystemFontOfSize:11] color:RGBCOLOR(0, 0, 0)
				minimumFontSize:11 shadowColor:nil
				   shadowOffset:CGSizeZero next:nil]];
}

- (UIColor*)navigationBarTintColor {
	return RGBCOLOR(15, 5, 0);
}

@end
