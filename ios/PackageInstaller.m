#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(PackageInstaller, NSObject)

RCT_EXTERN_METHOD(install:(NSString *)path withPackageName:(NSString *)packageName
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

@end
