######### CONFIG

project <- "spring-framework"
directory <- "/Users/mauricioaniche/Desktop/log-study/"

######### 

churn <- read.csv(paste(directory, project, "-log-churn.csv", sep=""), header=T)

qtyNoChanges <- nrow(churn[churn$adds == 0 & churn$deletions == 0 & churn$updates == 0,])
qtyNoChanges / nrow(churn) # qty of commits with log changes

qtyChanges <- nrow(churn[churn$adds > 0 | churn$deletions > 0 | churn$updates > 0,])
qtyChanges / nrow(churn)

qtyUpdates <- nrow(churn[churn$updates > 0,])
qtyUpdates / nrow(churn)

qtyAdds <- nrow(churn[churn$adds > 0,])
qtyAdds / nrow(churn)

qtyDeletes <- nrow(churn[churn$deletions > 0,])
qtyDeletes / nrow(churn)
