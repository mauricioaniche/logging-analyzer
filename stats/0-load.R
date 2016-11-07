library(vioplot)
library(sqldf)

######### CONFIG

project <- "2all"
directory <- "/Users/mauricioaniche/Desktop/log-study/apache/"

######### 

javaTypes <- read.csv(paste(directory, project, "-java-types.csv", sep=""), header=T)


logs <- read.csv(paste(directory, project, "-logs.csv", sep=""), header=T)
logsOfClasses <- merge(logs, javaTypes, by="file") # filtering interfaces and enums


metrics <- read.csv(paste(directory, project, "-log-product-metrics.csv", sep=""), header=T)
metrics <- metrics[metrics$project %in% unique(logs$project),]
metricsOfClasses <- merge(metrics, javaTypes, by=c("project", "file")) # filtering interfaces and enums
metricsOfClasses <- metricsOfClasses[metricsOfClasses$type =='class',]
