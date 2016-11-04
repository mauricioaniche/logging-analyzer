library(vioplot)

######### CONFIG

project <- "1all"
directory <- "/Users/mauricioaniche/Desktop/log-study/apache/"

######### 

javaTypes <- read.csv(paste(directory, project, "-java-types.csv", sep=""), header=T)
logs <- read.csv(paste(directory, project, "-logs.csv", sep=""), header=T)

logsOfClasses <- merge(logs, javaTypes, by="file") # filtering interfaces and enums

table(logsOfClasses$level) # Q1
table(logsOfClasses$position) #Q2
table(logsOfClasses$variables) # Q3
table(logsOfClasses$has_exception) #Q4

exceptions<-table(logsOfClasses[logsOfClasses$has_exception == 'true',]$exception_type) #Q5
exceptions[order(exceptions)]
  
vioplot(logsOfClasses$strings_length)
summary(logsOfClasses$strings_length)
