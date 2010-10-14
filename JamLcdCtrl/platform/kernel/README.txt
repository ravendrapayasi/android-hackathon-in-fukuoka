uImage
	Century社のUSB-LCDモニタが使えるはず

boot.scr
	BeagleBoardに挿したSDカードの第3パーティションから起動するスクリプト

boot.cmd
cmd2scr.sh
	ubootのmkimageでboot.scrを作るスクリプト(linux)


スクリプトから起動するときの命令
	mmc init; fatload mmc 0 0x80200000 boot.scr; source 0x80200000
