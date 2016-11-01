######### CONFIG

project <- "spring-framework"
directory <- "/Users/mauricioaniche/Desktop/log-study/"

######### 

changes <- read.csv(paste(directory, project, "-log-level-changes.csv", sep=""), header=T)
table(changes$direction)