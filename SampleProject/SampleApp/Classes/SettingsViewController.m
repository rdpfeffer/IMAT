//
//  SettingsViewController.m
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

#import "SettingsViewController.h"


@implementation SettingsViewController


///////////////////////////////////////////////////////////////////////////////////////////////////
// NSObject

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
	if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
		self.tableViewStyle = UITableViewStyleGrouped;
		self.autoresizesForKeyboard = YES;
		self.variableHeightRows = YES;
		
		UITextField* textField = [[[UITextField alloc] init] autorelease];
		textField.placeholder = @"UITextField";
		textField.font = TTSTYLEVAR(font);
		
		UITextField* textField2 = [[[UITextField alloc] init] autorelease];
		textField2.font = TTSTYLEVAR(font);
		textField2.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
		TTTableControlItem* textFieldItem = [TTTableControlItem itemWithCaption:@"TTTableControlItem"
																		control:textField2];
		
		UITextView* textView = [[[UITextView alloc] init] autorelease];
		textView.text = @"UITextView";
		textView.font = TTSTYLEVAR(font);
		
		TTTextEditor* editor = [[[TTTextEditor alloc] init] autorelease];
		editor.font = TTSTYLEVAR(font);
		editor.backgroundColor = TTSTYLEVAR(backgroundColor);
		editor.autoresizesToText = NO;
		editor.minNumberOfLines = 3;
		editor.placeholder = @"TTTextEditor";
		
		UISwitch* switchy = [[[UISwitch alloc] init] autorelease];
		TTTableControlItem* switchItem = [TTTableControlItem itemWithCaption:@"UISwitch" control:switchy];
		
		UISlider* slider = [[[UISlider alloc] init] autorelease];
		TTTableControlItem* sliderItem = [TTTableControlItem itemWithCaption:@"UISlider" control:slider];
		
		self.dataSource = [TTListDataSource dataSourceWithObjects:
						   textField,
						   editor,
						   textView,
						   textFieldItem,
						   switchItem,
						   sliderItem,
						   nil];
	}
	return self;
}


@end
