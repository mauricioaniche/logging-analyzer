######### CONFIG

project <- "spring-framework"
directory <- "/Users/mauricioaniche/Desktop/log-study/"

######### 

metrics <- read.csv(paste(directory, project, "-log-product-metrics.csv", sep=""), header=T)
javaTypes <- read.csv(paste(directory, project, "-java-types.csv", sep=""), header=T)

metricsOfClasses <- merge(metrics, javaTypes, by="file") # filtering interfaces and enums
metricsOfClasses <- metricsOfClasses[metricsOfClasses$type =='class',]

sum(metricsOfClasses$warn) == nrow(logs[logs$level == 'WARN',]) # sanity check
sum(metricsOfClasses$total_logs) == nrow(logs) # sanity check
sum(table(logsOfClasses$exception_type)) == nrow(logs) # sanity check

boxplot(metricsOfClasses$total_logs)
boxplot(metricsOfClasses$log_density)
boxplot(metricsOfClasses$avg_logging_level)
