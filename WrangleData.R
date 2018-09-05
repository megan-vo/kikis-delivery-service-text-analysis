# install.packages("dplyr")  # once per machine
library("dplyr")

# Loads kiki data set
part <- read.csv("data/part8.csv", stringsAsFactors = F)

speakers.stats <- part %>% 
  group_by(Speaker) %>% 
  summarize(
    WordCount = sum(WordCount)
  ) %>% 
  mutate(Percentage = WordCount / sum(WordCount))

write.csv(speakers.stats, file = "data/part8.csv", row.names=FALSE)