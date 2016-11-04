######### CONFIG

project <- "spring-framework"
directory <- "/Users/mauricioaniche/Desktop/log-study/"

######### 
dirFile <- paste(directory, project, ".dir", sep="")
dir <- readChar(dirFile, file.info(dirFile)$size-1)
metrics <- read.csv(paste(directory, project, "-log-process-metrics.csv", sep=""), header=T)
metrics$file <- paste(dir, metrics$file, sep="")

javaTypes <- read.csv(paste(directory, project, "-java-types.csv", sep=""), header=T)

metricsOfClasses <- merge(metrics, javaTypes, by="file") # filtering interfaces and enums
metricsOfClasses <- metricsOfClasses[metricsOfClasses$type =='class',]


boxplot(metricsOfClasses$logadd)
boxplot(metricsOfClasses$logdel)
