RELEASEVERSION=1.1.Beta5
ZIPRELEASEDIR="sudokuki-"$RELEASEVERSION"-binary"

make && make dist

mkdir -p $ZIPRELEASEDIR/libs

cp NEWS README COPYING HOWTO_RUN_SUDOKUKI.txt $ZIPRELEASEDIR/
cp libs/* $ZIPRELEASEDIR/libs/
cp sudokuki-$RELEASEVERSION.jar $ZIPRELEASEDIR/
echo "java -Djava.library.path=./libs -jar sudokuki-"$RELEASEVERSION".jar -ui Swing" > $ZIPRELEASEDIR/sudokuki.sh
chmod +x $ZIPRELEASEDIR"/sudokuki.sh"
cp "sudokuki-"$RELEASEVERSION".tar.gz" $ZIPRELEASEDIR/

zip -r $ZIPRELEASEDIR".zip" $ZIPRELEASEDIR

