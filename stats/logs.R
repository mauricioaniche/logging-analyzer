library(vioplot)

######### CONFIG

project <- "spring-framework"
directory <- "/Users/mauricioaniche/Desktop/log-study/"

######### 

javaTypes <- read.csv(paste(directory, project, "-java-types.csv", sep=""), header=T)
logs <- read.csv(paste(directory, project, "-logs.csv", sep=""), header=T)

logsOfClasses <- merge(logs, javaTypes, by="file") # filtering interfaces and enums

table(logsOfClasses$level)
table(logsOfClasses$position)
table(logsOfClasses$variables)
table(logsOfClasses$has_exception)
table(logsOfClasses$exception_type)
vioplot(logsOfClasses$strings_length)
summary(logsOfClasses$strings_length)
