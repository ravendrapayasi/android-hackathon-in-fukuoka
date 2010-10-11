2010/10/09 Android Hackathon in Fukuoka vol.2

組込み班？の成果物。

■動作
BeagleBoard(限定ではないけど)のUSBシリアルからデータを送信するだけ。
/dev/ttyUSB0 固定にしている。
送信先は、JuJuさんボードの予定。
名称がわからなかったので、「JuJuさんのArduino Mega」の頭文字をとった。
まあ、Arduinoではないのだが、ピンが互換ということだったので。


■ビルド方法
Android SDK環境と、Android NDK環境が必要。
JNIがあるので、ビルド時には先にndk-buildした方がよい。

antも使えるようにしている。
「ant install」とすると、ターゲットへのアップロードもしてくれるので、さらに便利。


■注意点
/dev/ttyUSB0はアクセス権が0600なので、
	chmod 0666 /dev/ttyUSB0
などとしなくては動作しない。
しかも、挿し直すたびに、電源を起動するたびにそうなる。
これは将来的に何とかしたいところだが、Platformに手を入れる必要がある。

