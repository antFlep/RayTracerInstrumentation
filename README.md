# RayTracerInstrumentation

The Simplest way to run this is with gradle, if you have gradle installed, just type `gradle run`, press enter and it should be running.

This project is for measuring how many times methods from the class **SDRaytracer** are called and how long their average execution time is.

### First Solution: Runtime Measuring
There are two ways available to get the values, the first and default one is to collect start and end point times and calculate the values at a certain points in time. This is the default solution, because it is far faster than the second one and has the least impact on the performance of SDRaytracer. Calculations are done `onMethodExit` of `renderImage` because afterwards we wait for user input, so it should not be a major problem. The only method which is hit the most by this approach is the `main` method of **SDRaytracer** because it is only run once and that single pass-through is only finished after renderImage. 

### Second Solution: Logging
The Second solution uses the **TimeLogger** in order to write start and end time points to a file, which can later be analysed with the **LogParser**, in order to calculate the values. This approach however has some issues. Most importantly it has a significantly higher impact on the performance than the first approach, so it is only an added extra. If we do not use autoflush with this solution, there is a possibility that on exiting the **SDRaytracer** application, not everything will be written to the log file. However if we do use autoflush the performance hit is even bigger, which is undesirable, a compromise that has been used used here is to Flush manually at certain points (`onMethodExit` of `renderImage` in our case), so we know that everything up till that point will be in the log file. 

In order to enable the second solution we need to uncomment Code between **LOG SOLUTION START** and **LOG SOLUTION END** and to comment the code between **RUNTIME SOLUTION START**  and **RUNTIME SOLUTION END** in **MyMethodVisitor** . Also depending on where the you're running the LogParser from you may need to change the log-file Location in **LogParser**, if run it from this directory there should not be any changes required
