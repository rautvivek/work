## Overview
- Added functionalities for transfer amount,minimal functionalities of deposit amount and withdraw amount also added.
## Unit Test
- Junit test case are added in AccountsServiceTest.Test name is transferAmount().
- Junit test case transferAmount() also added in AccountsControllerTest. 

## Class Details
- Transaction functionality classes: In package  com.db.awmd.challenge.domain, base class Transaction is added. DepositTransaction,TransferTransaction,WithdrawTransaction classes also added which extends Transaction class.
- Transaction type Enum: In package  com.db.awmd.challenge.domain, enum TransactionType is added.
- Exceptions are added. In package com.db.awmd.challenge.exception: InsufficientBalanceException,AccountLockException are added.
- For AccountsRepository interface, methods transferMoney(..),depositMoney(..),withdrawMoney(..) are added.
- Above methods implemention is added in AccountsRepositoryInMemory class.
- In  AccountsRepositoryInMemory class, new methods along with above methods are added- beginTransaction(..),beginFundTransferTransaction(..),beginDepositTransaction(..),beginWithdrawTransaction(..),IsAmountWithdrawnableFromAccout(..),depositAmount(..),withdrawAmount(..).
- Methods used for notification are depositAmount(..),withdrawAmount(..) of class AccountsRepositoryInMemory
- Added deposit() and withdraw() methods to class Account.
- Added TransferAmount() in AccountsController class.