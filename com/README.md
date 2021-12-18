As for the coding challenge, I I put one together I’m hoping you can look at.

The challenge is to create a simple Java bank aggregation application that pulls information from multiple different banks and displays it. At the link, you can download some skeleton code I put together to base the solution on.

https://drive.google.com/file/d/1jd8eTFRdXuLsxQcV78OMlD-18x05A8ip/view?usp=sharing

As you will see, there are two packages, com.bank1 and com.bank2 which represent proprietary API integration points towards these two hypothetical banks. They only return hardcoded dummy values and ignores input parameters, but I want you to imagine that they represent wrappers for external API calls. They both expose functionality to fetch account balances and transactions, but in slightly different ways. Your solution may not alter these classes.

You will also see that there is a class com.pyyne.challenge.bank.BankController which is the entry-point for you to start.

The challenge here is that I don’t want the BankController to ever directly use any classes in the com.bank1 and com.bank2 packages. Instead, you need to create an abstraction layer (using, for instance, an Adaptor pattern) that isolates and “hides” the bank1 and bank2 classes behind a common interface and data structures. Your solution should demonstrate appropriate separation of responsibilities and code isolation. A small hint, as there are different versions of the Adaptor pattern - the point is to create an abstraction that makes bank1 and bank2 look the same, not to create an adaptor that makes, for instance, bank1 look like bank2.

I would also like you to include relevant unit tests for the code you develop.

Bonus challenge - Employ a dependency injection pattern by, for instance, using the Spring framework. If you are completely unfamiliar with this, don’t worry. In that case, I would like you to spend a little time to read up on it and be prepared to have a discussion on how that pattern and functionality could be employed and what it would mean for this solution.