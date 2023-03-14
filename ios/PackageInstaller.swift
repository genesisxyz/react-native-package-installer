@objc(PackageInstaller)
class PackageInstaller: NSObject {

    @objc(install:withPackageName:withResolver:withRejecter:)
    func install(path: String, packageName: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
    resolve(false)
  }
}
