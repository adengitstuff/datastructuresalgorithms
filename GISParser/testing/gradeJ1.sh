#! /usr/bin/bash
#
#  Invocation:  gradeJ1.sh [ -index | -search | -all ] [ -repeat ]  <your zip file> <test code zip file>
#               gradeJ1.sh -clean   (deletes files created in earlier test)
#
#     Switches:
#        -index   test only the indexing feature
#        -search  test only the search feature
#        -all     test both indexing and search
#        -repeat  reuse the test seed from the latest execution
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
########################################################################
# Configuration variables:
indexingLog="indexLog.txt"
searchLog="searchLog.txt"
GISfile="testDB.txt"
CMDfile="testCmds.txt"
REFoffsetsfile="RefOffsets.txt"
stuOffsetsFile="stuOffsets.txt"
REFresultsfile="RefResults.txt"
stuSearchFile="searchResults.txt"
SEEDfile="seed.txt"
Separator="######################################################################"

# Control variables:
stuZipFile="unset"
toolsZipFile="unset"
testIndexing="no"
testSearch="no"
repeatOn="no"

############################################################## functions
#  Remove files created during test; handy if you are repeating a
#  test:
clean() {
   rm -Rf $LOGfile
   rm -Rf $GISfile
   rm -Rf $CMDfile
   rm -Rf $REFfile
   rm -Rf $STUfile
   rm -Rf *.txt *.class *.java *.jar

   for tfile in ./*
   do
      if [[ -d $tfile ]]; then
         rm -Rf $tfile
      fi
   done
   exit 0
}

############################################# fn to check for zip/jar file
#                 param1:  name of file to be checked
isZip() {

   mimeType=`file -b --mime-type $1`
   [[ $mimeType == "application/zip" ]]
}

##################################### fn to extract token from file name
#                 param1: (possibly fully-qualified) name of file
#  Note:  in production use, the first part of the file name will be the
#         student's PID
#
getPID() { 

   fname=$1
   # strip off any leading path info
   fname=${fname##*/}
   # extract first token of file name
   sPID=${fname%%.*}
}

############################################# echo invocation instructions
#
showInstructions() {
   
   echo "Error:  incorrect command-line parameters."
   echo "   gradeJ1.sh [ -index | -search | -all ] [ -repeat ]  <your zip file> <test code zip file>"
   echo "     or"
   echo "   gradeJ1.sh -clean"
   echo "Read the header comment for more information."
}

########################################################################
#
#  Check for valid command-line parameters to the script:
   
   if [[ $# -eq 1 ]]; then
      if [ $1 == "-clean" ]; then
         clean
      else
         echo "Unrecognized option: $1"
         showInstructions
         exit -1
      fi
   fi
   
   if [[ $# -eq 2 ]]; then
      stuZipFile="$1"
      toolsZipFile="$2"
   elif [[ $# -eq 3 ]]; then
      if [[ $1 == "-repeat" ]]; then
         repeatOn="yes"
      elif [[ $1 == "-index" ]]; then
         testIndexing="yes"
      elif [[ $1 == "-search"  ]]; then
         testSearch="yes"
      elif [[ $1 == "-all"  ]]; then
         testIndexing="yes"
         testSearch="yes"
      else 
         echo "Unrecognized switch: $1"
         exit -2
      fi
      stuZipFile="$2"
      toolsZipFile="$3"
   elif [[ $# -eq 4 ]]; then
      if [[ $1 == "-index" ]]; then
         testIndexing="yes"
      elif [[ $1 == "-search"  ]]; then
         testSearch="yes"
      elif [[ $1 == "-all"  ]]; then
         testIndexing="yes"
         testSearch="yes"
      else 
         echo "Unrecognized switch: $1"
         exit -2
      fi
      if [[ $2 == "-repeat" ]]; then
         repeatOn="yes"
      else 
         echo "Unrecognized switch: $2"
         exit -2
      fi
      stuZipFile="$3"
      toolsZipFile="$4"
   else
      echo "Invalid number of parameters"
      showInstructions
      exit -2
   fi

   
   if [[ ! -e $stuZipFile ]]; then
      echo "Error:  $stuZipFile is missing."
      exit -3
   else
      isZip $stuZipFile
      if [[ $? -ne 0 ]]; then
         echo "Error:  $stuZipFile is not a zip file."
         exit -3
      fi
   fi
   
   if [[ ! -e $toolsZipFile ]]; then
      echo "Error:  $toolsZipFile is missing."
      exit -4
   else
      isZip $toolsZipFile
      if [[ $? -ne 0 ]]; then
         echo "Error:  $toolsZipFile is not a zip file."
         exit -4
      fi
   fi

########################################################### write header for log file
   
   # Extract first token of zip file name (student PID when we run this)
   getPID $stuZipFile
   
   # Initiate header for grading log
   headerLog="header.txt"
   echo "Grading:  $stuZipFile" > $headerLog
   echo -n "Time:     " >> $headerLog
   echo `date` >> $headerLog
   echo >> $headerLog

########################################################### prepare for build

   buildLog="buildLog.txt"
#  Unpack student zip file:
   echo "Unpacking your zip file..." > $buildLog
   unzip -o $stuZipFile >> $buildLog
   
   if [[ $? -gt 2 ]]; then
      echo "Error:  unzip did not exit cleanly."
      exit -4
   fi

#  Attempt a build; this should compile everything:
   echo "Compiling your submission..." >> $buildLog
   javac *.java

#  See if the main class file was created:
   if [[ ! -e GISParser.class ]]; then
      echo "Error:  the build failed." >> $buildLog
      exit -5
   fi

   genLog="genLog.txt"
#  Unpack the grading code:
   echo "Unpacking the test code zip file..." > $genLog
   unzip -o $toolsZipFile >> $genLog
   
   if [[ $? -gt 2 ]]; then
      echo "Error:  unzip did not exit cleanly." >> $genLog
      exit -6
   fi

#  Generate a test case:
   if [[ $repeatOn == "no" ]]; then
      echo "Generating new test data..." >> $genLog
      java -jar GISGenerator.jar $GISfile $CMDfile
   else
      echo "Will use existing test data files... "  >> $genLog
   fi
   
   filesOK="yes"
   #  Check existence of test files:
   echo "Checking existence of test data..." >> $genLog
   if [[ ! -e $GISfile ]]; then
      echo "Error:  $GISfile does not exist." >> $genLog
      filesOK="no"
   fi
   if [[ ! -e $CMDfile ]]; then
      echo "Error:  $CMDfile does not exist." >> $genLog
      filesOK="no"
   fi
   if [[ ! -e $REFoffsetsfile ]]; then
      echo "Error:  $REFoffsetsfile does not exist." >> $genLog
      filesOK="no"
   fi
   if [[ ! -e $REFresultsfile ]]; then
      echo "Error:  $REFresultsfile does not exist." >> $genLog
      filesOK="no"
   fi
   if [[ $filesOK != "yes" ]]; then
      if [[ $repeatOn == "yes" ]]; then
         echo "Error:  -repeat requires that old test data files are present..." >> $genLog
      fi
      exit -8
   fi

#  Test offset generation, if requested
#
#  Run student soln to generate offsets file:
   if [[ $testIndexing == "yes" ]]; then
      echo "Running your solution for indexing..." > $indexingLog
      java GISParser -index $GISfile $stuOffsetsFile

   #  Check existence of student results file:
      if [[ ! -e $stuOffsetsFile ]]; then
         echo "Error:  $stuOffsetsFile was not created." >> $indexingLog
      else
         echo "Checking your offsets..." >> $indexingLog
         java -jar LogComparator.jar 1 $REFoffsetsfile $stuOffsetsFile >> $indexingLog
      fi
      echo >> $indexingLog
   fi
   
#  Test command processing, if requested
#
#  Run student soln to execute commands:
   if [[ $testSearch == "yes" ]]; then
      echo "Running your solution to perform searches..." >> $searchLog
      java GISParser -search $GISfile $CMDfile $stuSearchFile

   #  Check existence of student results file:
      if [[ ! -e $stuSearchFile ]]; then
         echo "Error:  $stuSearchFile was not created." >> $searchLog
      else
         echo "Checking your offsets..." >> $searchLog
         java -jar LogComparator.jar 2 $REFresultsfile $stuSearchFile >> $searchLog
      fi
      echo >> $searchLog
   fi

#  Write final summary
   # Import header
   reportLog="$sPID.txt"
   cat $headerLog > $reportLog
   echo >> $reportLog
   
   # Import scores from the test logs
   echo "Scores from testing:" >> $reportLog
   if [[ $testIndexing == "yes" ]]; then
      echo -n "Indexing:   " >> $reportLog
      grep -m1 "1 >> Score" $indexingLog >> $reportLog
   fi
   if [[ $testSearch == "yes" ]]; then
      echo -n "Searching:  " >> $reportLog
      grep -m1 "2 >> Score" $searchLog >> $reportLog
   fi
   echo >> $reportLog
   echo $Separator >> $reportLog
   
   # Import full log for each test
   if [[ $testIndexing == "yes" ]]; then
      echo "Detailed results from testing indexing:" >> $reportLog
      echo >> $reportLog
      cat $indexingLog >> $reportLog
      echo $Separator >> $reportLog
   fi
   if [[ $testSearch == "yes" ]]; then
      echo "Detailed results from testing search:" >> $reportLog
      echo >> $reportLog
         cat $searchLog >> $reportLog
   echo $Separator >> $reportLog
   fi
   
   # Import the compilation and data generation phases
   echo "Output from compilation of your code:" >> $reportLog
   echo >> $reportLog
   cat $buildLog >> $reportLog
   echo $Separator >> $reportLog
   echo "Output from test data generation:" >> $reportLog
   echo >> $reportLog
   cat $genLog >> $reportLog
   
   exit 0
