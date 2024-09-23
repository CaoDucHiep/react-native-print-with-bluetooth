
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNPrintWithBluetoothSpec.h"

@interface PrintWithBluetooth : NSObject <NativePrintWithBluetoothSpec>
#else
#import <React/RCTBridgeModule.h>

@interface PrintWithBluetooth : NSObject <RCTBridgeModule>
#endif

@end
