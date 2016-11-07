# have you loaded 0-load.R?

####### Q1
nrow(logsOfClasses)
table(logsOfClasses$level)

# chart that counts the percentage of each level per project, 
# i.e. for one project, 10% of INFO, 20% of DEBUG, etc...
# then, generate the boxplot with all projects.
groupedLogs <- sqldf("select [project.x] project, count(*) total from logsOfClasses group by project")
groupedLogsPerLevel <- sqldf("select [project.x] project, level, count(*) total from logsOfClasses group by project, level")
groupedLogsPct <- sqldf("select project, level, total, (select total from groupedLogs gl where gl.project = glpl.project) totalProject from groupedLogsPerLevel glpl group by project, level")
groupedLogsPct$pct <- groupedLogsPct$total / groupedLogsPct$totalProject * 100

boxplot(
  groupedLogsPct[groupedLogsPct$level == 'TRACE',]$pct,
  groupedLogsPct[groupedLogsPct$level == 'DEBUG',]$pct,
  groupedLogsPct[groupedLogsPct$level == 'INFO',]$pct,
  groupedLogsPct[groupedLogsPct$level == 'WARN',]$pct,
  groupedLogsPct[groupedLogsPct$level == 'ERROR',]$pct,
  groupedLogsPct[groupedLogsPct$level == 'FATAL',]$pct,
  names=c("TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"),
  las=2
)

# ??? QTY OF LOG LINES / TOTAL LINES
locPerProject <- sqldf("select [project.x] project, sum(loc) from metricsOfClasses group by project")

####### Q2
table(logsOfClasses$position) #Q2
table(logsOfClasses$variables) # Q3
table(logsOfClasses$has_exception) #Q4

exceptions<-table(logsOfClasses[logsOfClasses$has_exception == 'true',]$exception_type) #Q5
exceptions[order(exceptions)]
  
vioplot(logsOfClasses$strings_length)
summary(logsOfClasses$strings_length)
