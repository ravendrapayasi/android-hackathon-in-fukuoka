#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include <android/log.h>

#include <termios.h>
#include <unistd.h>
#include <fcntl.h>


#define  LOG_TAG    "JNI"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#define HEADER_SIZE	(5)

static uint8_t sHeader[] = {
		0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x80, 0x00,
};

static const uint8_t PATTERN0[] = {
		0x0f, 0xff, 0x0f, 0xff, 0x0f, 0xff, 0x0f, 0xff, 0x0f, 0xff, 0x0f, 0xff, 0x0f, 0xff, 0x0f, 0xff,
};
static const uint8_t PATTERN1[] = {
		0x08, 0xf8, 0x0f, 0x8f, 0x0f, 0xff, 0x08, 0xf8, 0x0f, 0x8f, 0x0f, 0xff, 0x08, 0xf8, 0x0f, 0x8f,
};

static const struct {
	uint8_t size;
	const uint8_t *pData;
} SENDDATA[] = {
	{
		sizeof(PATTERN0),
		PATTERN0,
	},
	{
		sizeof(PATTERN1),
		PATTERN1,
	},
};

jint Java_com_blogspot_jagfukuoka_JamLcdCtrl_MainActivity_sendData ( JNIEnv* env, jobject thiz, jint ctrl )
{
	LOGI("sendData");

	// シリアル送信
	int fd = open("/dev/ttyUSB0", O_WRONLY);
	if(fd == -1) {
		LOGE("fd == -1");
		return -1;
	}

	struct termios ter, terold;
	tcgetattr(fd, &terold);
	memset(&ter, 0, sizeof(ter));

	ter.c_cflag = CLOCAL | CS8 | B38400;
	ter.c_iflag = IGNPAR;
	ter.c_oflag = 0;
	ter.c_lflag = 0;

	tcsetattr(fd, TCSANOW, &ter);

#if 0
	{
		char buf[20];
		sprintf(buf, "ctrl:%d /size:%d", ctrl, SENDDATA[ctrl].size);
		LOGI(buf);
	}
#endif

	sHeader[HEADER_SIZE] = SENDDATA[ctrl].size + 2;	//+2はデータヘッダサイズ
	write(fd, sHeader, sizeof(sHeader));
	int sz = write(fd, SENDDATA[ctrl].pData, SENDDATA[ctrl].size);

	//戻すのが早すぎ
	//tcsetattr(fd, TCSANOW, &terold);

	close(fd);

	return (sz == SENDDATA[ctrl].size) ? 0 : -2;
}
