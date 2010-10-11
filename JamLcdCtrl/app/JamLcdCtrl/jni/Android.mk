LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := jamlcdctrl
### Add all source file names to be included in lib separated by a whitespace
LOCAL_SRC_FILES := com_blogspot_jagfukuoka_JamLcdCtrl_MainActivity.c

LOCAL_LDLIBS    := -llog

include $(BUILD_SHARED_LIBRARY)
