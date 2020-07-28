#### What assumptions did you make?
1. Phrase matches will be used to match a keyword by default. An alternative could be to use an exact matcher. I have provided example implementaions of both.
2. An intermediate score is returned when an error occurs while processing one of the prefixes. One of the reasons for this is to preserve the SLA

#### How does your algorithm work?
The algorithm works with the following steps:
1. Fetch suggestions from Amazon API for every prefix of input. Also initialize the weight of each prefix based on its length. The weight range of any keyword is between the 1 and keywordLength. The weight of a prefix is inversely proportional to the length of the prefix.
```text
Keyword: test
Weights: [t:4, te:3, tes:2, test:1]
```
2. Compute two intermediate scores for prefix from the retrieved suggestions.
    - `keywordScore`: Obtained by multiplying the prefix weight by the number of matches found in intermediate suggestion list.
    - `maxPossibleScore`:  Obtained by multiplying the prefix weight by the total number of items in the suggestion list. (not all queries will return 10 items)
3. Sum all intermediate keywordScores and intermediate maxPossibleScores and normalize it to 100.

All of the above steps are performed in parallel using a `cachedThreadPool`. `AtomicIntegers` are also used to ensure that the overall result is updated during concurrent computation of the intermediate results. 

#### Do you think the (*hint) that we gave you earlier is correct and if so - why?
It was useful to not give a weight score based on the position of the keyword in the return list from the API.

#### How precise do you think your outcome is and why?
I think it's a good starting point given the time constraints for this assignment. There are other factors to consider such as autocorrection and misspelled words. 

## Project description
- Run from terminal using `./mvnw spring-boot:run`
- Swagger UI is also provided for convenience at http://localhost:8080/swagger-ui.html
