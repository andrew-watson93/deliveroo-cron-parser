# Building the application
If you have gradle installed, build the application by running
> gradle clean build

Or alternatively use the wrapper:
> ./gradlew clean build

This will compile the application and run the tests

# Running the application
To run the application, run the following command for gradle:
> gradle run --args="CRON_STATEMENT"

or 
> ./gradlew rum --args="CRON_STATMENT"

replacing CRON_STATEMENT with your cron command.