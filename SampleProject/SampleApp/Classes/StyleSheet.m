//
//  StyleSheet.m
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
