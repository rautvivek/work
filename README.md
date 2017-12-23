## Overview
- Added functionalities for transfer amount,deposit amount,withdraw amount.
## Unit Test
- Junit test case are added in AccountsServiceTest.Test name is transferAmount()

## Class Details
- In package  com.db.awmd.challenge.domain, base class Transaction is added. DepositTransaction,TransferTransaction,WithdrawTransaction classess extends Transaction class.
- In package  com.db.awmd.challenge.domain, enum TransactionType is added.
- In package com.db.awmd.challenge.exception, InsufficientBalanceException is added.
- For AccountsRepository interface, methods transferMoney(..),depositMoney(..),withdrawMoney(..) are added.
- Above methods implemention is added in AccountsRepositoryInMemory class.
- In  AccountsRepositoryInMemory class, new methods along with above methods are added- beginTransaction(..),beginFundTransferTransaction(..),beginDepositTransaction(..),beginWithdrawTransaction(..),IsAmountWithdrawnableFromAccout(..),depositAmount(..),withdrawAmount(..).
- Methods used for notification are depositAmount(..),withdrawAmount(..) of class AccountsRepositoryInMemory
- Added deposit() and withdraw() methods to class Account.