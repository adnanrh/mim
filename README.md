# mim
This is a console application that takes an input string as a runtime argument, then continuously reads and executes
user input as vim commands. Input is read after a newline character is entered. Exits when the user enters the exit character ("q").

<b>Requires:</b>

* Java SE 8 or above (https://www.java.com/en/download/)

<b>Run Instructions:</b>

Using gradle:
1. Clone or download the repository
2. Navigate to the repository directory in a terminal or command prompt
3. Run ```./gradlew run --args='"<input string>"' -q --console=plain``` (Linux) or ```gradlew run --args='"[input string]"' -q --console=plain``` (Windows) to build and run

Using JDK directly (faster console interaction):
1. Clone or download the repository
2. Navigate to the src/main/java directory in the repository folder in a terminal or command prompt
3. Run ```javac *.java``` to compile
4. Run ```java Mim "<input string>"``` to run

<b>Test Instructions:</b>

Using gradle:
1. Clone or download the repository
2. Navigate to the repository directory in a terminal or command prompt
3. Run ```./gradlew clean test``` (Linux) or ```gradlew clean test``` (Windows) to run unit tests

<b>Run Example:</b>
```
C:\Users\Adnan\IdeaProjects\Mim\src\main\java>java Mim "Hello World"
[H]ello World
e
Hell[o] World
e
Hello Worl[d]
0
[H]ello World
$
Hello Worl[d]
0
[H]ello World
q

C:\Users\Adnan\IdeaProjects\Mim\src\main\java>
```
