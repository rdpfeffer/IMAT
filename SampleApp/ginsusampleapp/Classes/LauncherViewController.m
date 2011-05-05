//
//  LauncherViewController.m
//  ginsusampleapp
//
//  Created by Benjamin, Sargon on 4/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "LauncherViewController.h"


@implementation LauncherViewController
@synthesize customStatusString, tempStatusString;

///////////////////////////////////////////////////////////////////////////////////////////////////
// NSObject

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
  	
	
	if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
		self.title = @"Ginsu Sample App";
		
		self.navigationItem.rightBarButtonItem
		= [[[UIBarButtonItem alloc] initWithTitle:@"About" style:UIBarButtonItemStyleBordered
										   target:self action:@selector(showDetails)] autorelease];
	}
	
	return self;
}

- (void)dealloc {
	[super dealloc];
}

- (void) showDetails {
	UIAlertView *customMessageAlert = [[UIAlertView alloc] initWithTitle:@"Test Dialog" message:@"\n\n\n"
																delegate:self cancelButtonTitle:NSLocalizedString(@"Cancel",nil) 
													   otherButtonTitles:NSLocalizedString(@"Save",nil), nil];
	UILabel *customMessageLabel = [[UILabel alloc] initWithFrame:CGRectMake(12,40,260,25)];
	customMessageLabel.font = [UIFont systemFontOfSize:16];
	customMessageLabel.textColor = [UIColor whiteColor];
	customMessageLabel.backgroundColor = [UIColor clearColor];
	customMessageLabel.shadowColor = [UIColor blackColor];
	customMessageLabel.shadowOffset = CGSizeMake(0,-1);
	customMessageLabel.textAlignment = UITextAlignmentCenter;
	customMessageLabel.text = @"Sample text should be input here";
	[customMessageAlert addSubview:customMessageLabel];
	
	UIImageView *textImage = [[UIImageView alloc] initWithImage:[UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"customMessageField" ofType:@"png"]]];
	textImage.frame = CGRectMake(11,79,262,31);
	[customMessageAlert addSubview:textImage];
	
	UITextField *messageField = [[UITextField alloc] initWithFrame:CGRectMake(16,83,252,25)];
	messageField.font = [UIFont systemFontOfSize:14];
	messageField.backgroundColor = [UIColor whiteColor];
	messageField.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
	messageField.text = customStatusString;
	messageField.autocapitalizationType = UITextAutocapitalizationTypeNone;
	//messageField.secureTextEntry = YES;
	messageField.keyboardAppearance = UIKeyboardAppearanceAlert;
	//[messageField setReturnKeyType:UIReturnKeyDone];
	messageField.delegate = self;
	[messageField becomeFirstResponder];
	[customMessageAlert addSubview:messageField];
	
	[customMessageAlert setTag:2];
	[customMessageAlert show];
	
	[customMessageAlert release];
	[messageField release];
	[textImage release];
	[customMessageLabel release];
	
}

- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex {
    NSLog(@"alert view done");
	
	if ([alertView tag] == 2) {    // it's the custom message alert
        if (buttonIndex == 1) {     // and they clicked Save.
            // do stuff
			NSLog(@"Updating custom status with %@", tempStatusString);
			//set the customStatusString here??
			customStatusString = [tempStatusString copy];
		}
    }
	
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    NSLog(@"textFieldDidEndEditing and value is %@", textField.text);
	tempStatusString = textField.text;
	//[self updateThresholdValue:textField];
}

- (BOOL)textFieldShouldReturn:(UITextField*)textField {
    NSLog(@"textFieldShouldReturn and value is %@", textField.text);
	/*
	 if (theTextField == thresholdValue) {
	 [thresholdValue resignFirstResponder];
	 }
	 */
	[textField resignFirstResponder];
    return YES;
}

///////////////////////////////////////////////////////////////////////////////////////////////////
// UIViewController

- (void)loadView {
	[super loadView];
	
	_launcherView = [[TTLauncherView alloc] initWithFrame:self.view.bounds];
	_launcherView.backgroundColor = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	
	_launcherView.delegate = self;
	_launcherView.columnCount = 3;
	
	TTLauncherItem *l1Item = [[[TTLauncherItem alloc] initWithTitle:@"Events"
															  image:@"bundle://rssicon.png"
																URL:@"tt://eventView" canDelete:NO] autorelease];
	
	TTLauncherItem *l2Item = [[[TTLauncherItem alloc] initWithTitle:@"Info"
															  image:@"bundle://Icon.png"
																URL:@"tt://newsTableViewController" canDelete:YES] autorelease];
	
	TTLauncherItem *l3Item = [[[TTLauncherItem alloc] initWithTitle:@"Map"
															  image:@"bundle://rssicon.png"
																URL:nil canDelete:YES] autorelease];
	
	TTLauncherItem *l4Item = [[[TTLauncherItem alloc] initWithTitle:@"Developers"
															  image:@"bundle://developericon.png"
																URL:@"tt://scrollViewController" canDelete:NO] autorelease];
	
	TTLauncherItem *l5Item = [[[TTLauncherItem alloc] initWithTitle:@"Intuit"
															  image:@"bundle://Icon.png"
																URL:@"http://www.intuit.com" canDelete:YES] autorelease];
	
	TTLauncherItem *l6Item = [[[TTLauncherItem alloc] initWithTitle:@"Settings"
															  image:@"bundle://settings.png"
																URL:@"tt://settingsViewController" canDelete:YES] autorelease];
	
	
	TTLauncherItem *l7Item = [[[TTLauncherItem alloc] initWithTitle:@"Facebook"
															  image:@"bundle://Icon.png"
																URL:nil canDelete:YES] autorelease];
	
	TTLauncherItem *l8Item = [[[TTLauncherItem alloc] initWithTitle:@"Button 9"
															  image:@"bundle://Icon.png"
																URL:nil canDelete:YES] autorelease];
	
	_launcherView.pages = [NSArray arrayWithObjects:
						   [NSArray arrayWithObjects:
							l1Item, l2Item, l3Item, l4Item, l5Item, l6Item,
							nil],
						   [NSArray arrayWithObjects:
							l7Item, l8Item,
							nil],
						   nil
						   ];
	
	[self.view addSubview:_launcherView];

}

///////////////////////////////////////////////////////////////////////////////////////////////////
// TTLauncherViewDelegate

- (void)launcherView:(TTLauncherView*)launcher didSelectItem:(TTLauncherItem*)item {
	TTNavigator *navigator = [TTNavigator navigator];
	/*
	 TTURLMap* map = navigator.URLMap;
	 [map            from: @"tt://photoTest2"
	 parent: @"tt://catalog"
	 toViewController: [PhotoTest2Controller class]
	 selector: nil
	 transition: 0];
	 
	 */
    [navigator openURLAction:[[TTURLAction actionWithURLPath:item.URL] applyAnimated:YES]];
	//[navigator openURLAction:[TTURLAction actionWithURLPath:@"tt://photoTest2"]];
}

- (void)launcherViewDidBeginEditing:(TTLauncherView*)launcher {
	[self.navigationItem setRightBarButtonItem:[[[UIBarButtonItem alloc]
												 initWithBarButtonSystemItem:UIBarButtonSystemItemDone
												 target:_launcherView action:@selector(endEditing)] autorelease] animated:YES];
}

- (void)launcherViewDidEndEditing:(TTLauncherView*)launcher {
	[self.navigationItem setRightBarButtonItem:nil animated:YES];
}




@end
