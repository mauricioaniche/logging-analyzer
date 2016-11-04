######### CONFIG

project <- "1all"
directory <- "/Users/mauricioaniche/Desktop/log-study/apache"

######### 

changes <- read.csv(paste(directory, project, "-log-level-changes.csv", sep=""), header=T)
table(changes$direction) # q7
