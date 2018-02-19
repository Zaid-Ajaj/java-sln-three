rm -rf -r out
javac test/*.java src/*.java -d out
cd out && java Runner
cd ..