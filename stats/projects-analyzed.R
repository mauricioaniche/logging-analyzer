outersect = function(x, y) {
  sort(c(setdiff(x, y),
         setdiff(y, x)))
}

######### CONFIG

logs <- read.csv("/Users/mauricioaniche/Desktop/log-study/apache/0all-logs.csv", header=T)
projects <- read.csv("apache-projects.csv", header=T)

nrow(projects) # total of projects in apacht
length(unique(logs$project)) # total of analyzed projects

######### 

