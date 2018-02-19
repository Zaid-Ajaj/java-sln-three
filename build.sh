# clean/delete the output folder if it exists 
rm -rf -r out
# Call the Java compiler 
# option "--source-path" is where the source .java files are located, in this case in src
# option "-d out" is the output directory
javac src/*.java -d out