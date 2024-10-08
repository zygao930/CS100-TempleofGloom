The provided code base should be present in this repository. The difference between this and previous worksheets/coursework is that we have only given you the class files (don't try to reverse engineer the source code, as that will ruin the point of the exercise and won't gain you anything anyway). You should see four packages: 

+ `game`, 
+ `gui`, 
+ `student`, and 
+ `main`.

The`student` package is where you will be writing your code.

The program can be run from two classes within the `main` package. Running the `main` method from the class `TXTmain` executes the program in headless mode (without a GUI); running it from `GUImain` runs it with an accompanying display, which may help debug. Each of these runs a single map on a random seed by default. If you run the program before any solution code is written, you should see the explorer stand still, and an error message tells you that you returned from`explore()` without having found the Orb. It would be best if you started by trying this to check that the code compiles correctly.

Two optional flags can be used to run the program in different ways:

+ `-n <count>`: runs the program multiple times. This option is available only in headless mode and is ignored if run with the GUI. The output will still be written to the console for each map so you know how well you did, and an average score will be provided at the end. This helps run your solution many times and compare different solutions on many maps.
+ `-s <seed>`: runs the program with a predefined seed. This allows you to test your solutions on particular maps that can be challenging or that you might be failing on and thus is quite helpful for debugging. This can be used both with the GUI and in headless mode.

For instance, to run the program 100 times in headless mode, write:

```java
    java main.TXTmain -n 100
```

To run the program once with a seed of 1050, write:

```java
    java main.GUImain -s 1050   
```

You may combine these flags, but if you run the program more than once and provide a seed, it will run the program on the same map (the map generated by that seed) every time.