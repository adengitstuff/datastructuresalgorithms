#  To run on CentOS, add the following as the first line above:
#
#         #! /usr/bin/python3
#
#  You may need to edit the line above to correct the path to the python
#  interpreter binary.  You may find it useful to run the following 
#  command:  ls -l /usr/bin/py*
#
#  Invocation:  gradeJ1.py [ -index | -search | -all ] <your zip file> <test code zip file>
#
#     Switches:
#        -index   test only the indexing feature
#        -search  test only the search feature
#        -all     test both indexing and search
#
#  Prerequisites:  the Oracle JDK must be installed, and its bin directory
#                  must be in your path
#
#  To use this script (on Linux):
#    - create a testing directory
#    - copy the zip file containing your solution to the test directory
#    - copy the posted grading code to the test directory
#    - put this script in that directory
#    - if necessary, run chmod a+x to make the script executable
#    - execute the command given above
#
#  If you get an error message about the build failing, then your zip
#  file is defective.
#
#  If you get any other error messages, you need to fix the problem.
#  
#  Check the results, as appropriate:
#     indexLog.txt     for test of indexing
#     searchLog.txt    for test of search
#
import sys, os, zipfile

# Check for valid number of arguments
nargs = len(sys.argv) - 1
if (nargs != 3):
	print("Invocation: gradej1.py [-index | -search | -all] <soln zip file> <tools zip file>")
	sys.exit()

# Check option
testIndexing = False
testSearch = False
if ( sys.argv[1] == "-index" or sys.argv[1] == "-all"):
	testIndexing = True
if ( sys.argv[1] == "-search" or sys.argv[1] == "-all"):
	testSearch = True
	
# Extract the student zip file
if ( not os.path.isfile(sys.argv[2]) ):
	print("Student zip file %s does not exist." %(sys.argv[2]))
	sys.exit();
with zipfile.ZipFile(sys.argv[2], 'r') as zip_ref:
    zip_ref.extractall("./")

# Attempt to compile student code
os.system("javac GISParser.java")
if ( not os.path.isfile("GISParser.class") ):
	print("Compilation failed; GISParser.class does not exist")
	sys.exit();

# Extract the tools
if ( not os.path.isfile(sys.argv[3]) ):
	print("Tools zip file %s does not exist." %(sys.argv[3]))
	sys.exit();
with zipfile.ZipFile(sys.argv[3], 'r') as zip_ref:
    zip_ref.extractall("./")

# Generate test data
os.system("java -jar GISGenerator.jar GISdata.txt GIScmds.txt")

# Run indexing test, if requested
if ( testIndexing ):
   os.system("java GISParser -index GISdata.txt stuIndex.txt")
   # Check results of indexing test
   os.system("java -jar LogComparator.jar 1 RefOffsets.txt stuIndex.txt > indexLog.txt")

# Run search test, if requested
if (testSearch ):
   os.system("java GISParser -search GISdata.txt GIScmds.txt stuSearches.txt")
   # Check results of search test
   os.system("java -jar LogComparator.jar 2 RefResults.txt stuSearches.txt > searchLog.txt")

sys.exit()
