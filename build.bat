javac -d build -cp src src\modding\Loader.java src\com\samjo\ExampleMod.java
cd build
7z a -tzip ExampleMod.zip com ..\src\com\samjo\mod.properties
rm -rf com
cd ..