RELEASEVERSION=1.1.1
ZIPRELEASEDIR="sudokuki-"$RELEASEVERSION"-binary"

make && make dist-zip

mkdir -p $ZIPRELEASEDIR/

cp NEWS README COPYING HOWTO_RUN_SUDOKUKI.txt $ZIPRELEASEDIR/
cp libs/* $ZIPRELEASEDIR/
cp sudokuki-$RELEASEVERSION.jar $ZIPRELEASEDIR/
echo "java -Djava.library.path=. -jar sudokuki-"$RELEASEVERSION".jar -ui Swing" > $ZIPRELEASEDIR/sudokuki.sh
chmod +x $ZIPRELEASEDIR"/sudokuki.sh"
cp "sudokuki-"$RELEASEVERSION".zip" $ZIPRELEASEDIR/"sudokuki-"$RELEASEVERSION"-src.zip"

zip -r $ZIPRELEASEDIR".zip" $ZIPRELEASEDIR

