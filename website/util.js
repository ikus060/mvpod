var PLATFORM_OTHER = 0;
var PLATFORM_WINDOWS = 1;
var PLATFORM_LINUX = 2;
var PLATFORM_LINUX64 = 3;
var PLATFORM_MACOSX = 4;
var PLATFORM_MAC = 5;
var gPlatform = PLATFORM_WINDOWS;
if (navigator.platform.indexOf("Win32") != -1) {
    gPlatform = PLATFORM_WINDOWS
} else if (navigator.platform.indexOf("Linux") != -1) {
    gPlatform = PLATFORM_LINUX
    if(navigator.platform.indexOf("64")) {
        gPlatform = PLATFORM_LINUX64;
    }
} else if (navigator.userAgent.indexOf("Mac OS X") != -1) {
    gPlatform = PLATFORM_MACOSX
} else if (navigator.userAgent.indexOf("MSIE 5.2") != -1) {
    gPlatform = PLATFORM_MACOSX
} else if (navigator.platform.indexOf("Mac") != -1) {
    gPlatform = PLATFORM_MAC
} else {
    gPlatform = PLATFORM_OTHER
}
