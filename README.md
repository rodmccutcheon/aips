# AIPS

* Uses Java 19

* Naive implementation to select: "The top 3 half hours with most cars, in the same format as the input file".
  * Current implementation is O(n*log(n)) due to the sort
  * This could be optimised further using something similar to "quicksearch" algorithm developed by Tony Hoare
  * The quicksearch algorithm would be: 
    * best-case performance O(n)
    * worst-case performance: O(n^2)
    * average-case performance: O(n)