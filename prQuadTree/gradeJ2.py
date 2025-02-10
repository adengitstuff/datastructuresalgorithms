#  To run on CentOS, add the following as the first line above:
#
#         #! /usr/bin/python3
#
#  You may need to edit the line above to correct the path to the python
#  interpreter binary.  You may find it useful to run the following 
#  command:  ls -l /usr/bin/py*
#
#  Invocation:  python gradeJ2.py <#data pts>       on Windows
#               gradeJ2.py <#data pts>                on Linux 
#
#  Prerequisites:
#    - the Oracle JDK must be installed, and its bin directory
#      must be in your path
#    - you must have implemented your solution in ./cs3114/J2/DS/prQuadTree.java
#    - you must be working in the directory where you unpacked
#      the given zip archive, one level above the .\src directory
#    - you must have perfomed a successful build, so that the file
#      testDriver.class is in the .\src directory
#
#  This script will first run the test generator, then run your solution
#  with the same seed file, then run the comparison tool on the results.
#  It's still up to you to check the resulting comparison files.
#
import sys, shutil, os

if ( len(sys.argv) < 1 ):
	printf("Must specify number of data points!");
	sys.exit();
	
# Edit if you need to specify the path to your Java bin directory:
runCmd = "java testDriver " + sys.argv[1] + " -repeat"

# Uncomment correct setting for pure command-line or Eclipse:
binDir = "./src/"      # for pure command-line
#binDir = "./bin/"       # for Eclipse, depending on project settings

# Generate the reference results:
os.system("java -jar prQuadGen.jar " + sys.argv[1])

# Move the seed file into the src directory and move there:
seedFile = 'seed.txt'
destination = binDir + seedFile
shutil.copy(seedFile, destination)
os.chdir(binDir)

# Check for existence of .class file for test class file
testClassFile = './cs3114/J2/DS/Lewis.java'
if ( not os.path.isfile(testClassFile) ):
   print("%s not found; please check the directories" %(testClassFile))
   sys.exit()

# Check for test driver and PR quadtree .class files after build:
reqdClassFiles = ['testDriver.class',
                  './cs3114/J2/DS/Direction.class',
                  './cs3114/J2/DS/Compare2D.class',
                  './cs3114/J2/DS/prQuadTree.class',
                  './cs3114/J2/DS/prQuadTree$prQuadLeaf.class',
                  './cs3114/J2/DS/prQuadTree$prQuadInternal.class',
                  './cs3114/J2/DS/prQuadTree$prQuadNode.class'
                 ]
for cFile in reqdClassFiles:
   if ( not os.path.isfile(cFile) ):
      print("%s not found; please compile your code first" %(cFile))
      sys.exit()

# Run the student solution:
os.system(runCmd)
os.chdir("..");

# Perform comparisons:
stuFiles = ['TestTreeInitialization.txt',
            'TestInsertion.txt',
            'TestRegionSearch.txt'
           ]

fileCount = 1
baseCmd = "java -jar LogComparator.jar "
for stuF in stuFiles:
   refF = "ref" + stuF
   qualStuF = binDir + stuF                      # use for pure command-line

   if ( not os.path.isfile(qualStuF) ):
      print("Could not find: %s" %(qualStuF))
   elif ( not os.path.isfile(refF) ):
      print("Could not find: %s" %(refF))
   else:
      cStr = str(fileCount)
      destF = "comp" + stuF
      newCmd = baseCmd + cStr + " " + refF + " " + qualStuF + " > " + destF
      print(newCmd)
      os.system(newCmd);
      fileCount += 1

# Exit:
sys.exit()
